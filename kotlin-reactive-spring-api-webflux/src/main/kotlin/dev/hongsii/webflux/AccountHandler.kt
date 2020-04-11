package dev.hongsii.webflux

import dev.hongsii.common.extensions.logger
import dev.hongsii.domain.account.AccountRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class AccountHandler(
        private val accountRepository: AccountRepository
) {

    fun getAccounts(request: ServerRequest) =
            Mono.just(accountRepository.findAll())
                    .flatMap { ServerResponse.ok().bodyValue(it) }

    fun getAccountsWithIO(request: ServerRequest) =
            Mono.fromCallable { accountRepository.findAll() }
                    .subscribeOn(schedulers)
                    .flatMap { ServerResponse.ok().bodyValue(it) }

    companion object {
        val schedulers = Schedulers.newElastic("db-schedulers")
        val log = logger()
    }
}
