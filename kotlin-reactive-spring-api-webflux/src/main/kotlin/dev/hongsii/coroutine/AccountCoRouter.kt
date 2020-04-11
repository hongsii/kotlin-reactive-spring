package dev.hongsii.coroutine

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AccountCoRouter {

    @Bean
    fun coRoute(handler: AccountCoHandler) = coRouter {
        ("/api/coroutine" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/accounts", handler::getAccounts)
            GET("/io/accounts", handler::getAccountsWithIOContext)
        }
    }
}