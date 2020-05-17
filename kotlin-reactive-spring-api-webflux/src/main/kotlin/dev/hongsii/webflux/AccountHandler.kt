package dev.hongsii.webflux

import dev.hongsii.common.extensions.logger
import dev.hongsii.repository.CustomizedAccountRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class AccountHandler(
        private val customizedAccountRepository: CustomizedAccountRepository
) {

    fun getAccounts(request: ServerRequest) =
            Mono.just(customizedAccountRepository.findAllByQueryDsl())
                    .flatMap { ServerResponse.ok().bodyValue(it) }
                    .doOnNext { log.info("next log") }
                    .publishOn(Schedulers.elastic())
                    .doOnSuccess { log.info("success log") }

    fun getAccountsWithIO(request: ServerRequest) =
            Mono.fromCallable { customizedAccountRepository.findAllByQueryDsl() }
                    .doOnNext { log.info("next log") }
                    .subscribeOn(schedulers)
                    .flatMap { ServerResponse.ok().bodyValue(it) }
                    .doOnSuccess { log.info("success log") }

    companion object {
        val schedulers = Schedulers.newElastic("db-schedulers")
        val log = logger()
    }
}
