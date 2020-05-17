package dev.hongsii.filter

import org.reactivestreams.Subscription
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.CoreSubscriber
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono
import reactor.core.publisher.Operators
import reactor.util.context.Context
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class MdcLoggingFilter : WebFilter {

    companion object {
        private val MDC_CONTEXT_REACTOR_KEY: String = MdcLoggingFilter::class.java.name

        const val REQUEST_ID = "txid"
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return chain
                .filter(exchange)
                .subscriberContext { it.put(REQUEST_ID, UUID.randomUUID().toString().replace("-", "")) }
    }

    @PostConstruct
    fun init() {
        Hooks.onEachOperator(MDC_CONTEXT_REACTOR_KEY, Operators.lift { _, subscriber -> MdcContextLifter(subscriber) })
    }

    @PreDestroy
    fun cleanupHook() {
        Hooks.resetOnEachOperator(MDC_CONTEXT_REACTOR_KEY)
    }

    /**
     * Helper that copies the state of Reactor [Context] to MDC on the #onNext function.
     */
    inner class MdcContextLifter<T>(private val coreSubscriber: CoreSubscriber<T>) : CoreSubscriber<T> {

        override fun onNext(t: T) {
            coreSubscriber.apply {
                currentContext().copyToMdc()
                onNext(t)
            }
        }

        override fun onSubscribe(subscription: Subscription) {
            coreSubscriber.onSubscribe(subscription)
        }

        override fun onComplete() {
            coreSubscriber.onComplete()
        }

        override fun onError(throwable: Throwable?) {
            coreSubscriber.onError(throwable)
        }

        override fun currentContext(): Context = coreSubscriber.currentContext()

        /**
         * Extension function for the Reactor [Context]. Copies the current context to the MDC, if context is empty clears the MDC.
         * State of the MDC after calling this method should be same as Reactor [Context] state.
         * One thread-local access only.
         */
        private fun Context.copyToMdc() {
            if (!this.isEmpty) {
                val map: Map<String, String> = this.stream()
                        .collect(Collectors.toMap(
                                { it.key.toString() },
                                { it.value.toString() }
                        ))
                MDC.setContextMap(map)
            } else {
                MDC.clear()
            }
        }
    }
}
