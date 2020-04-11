package dev.hongsii.coroutine

import dev.hongsii.common.extensions.logger
import dev.hongsii.domain.account.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AccountCoHandler(
        private val accountRepository: AccountRepository
) {

    suspend fun getAccounts(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait(accountRepository.findAll())
    }

    suspend fun getAccountsWithIOContext(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait(io {
            accountRepository.findAll()
        })
    }

    companion object {
        val log = logger()
    }
}

suspend fun <T> io(block: suspend CoroutineScope.() -> T): T = withContext(Dispatchers.IO, block)
