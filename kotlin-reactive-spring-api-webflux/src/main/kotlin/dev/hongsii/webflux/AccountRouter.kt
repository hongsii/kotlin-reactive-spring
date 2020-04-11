package dev.hongsii.webflux

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class AccountRouter {

    @Bean
    fun route(handler: AccountHandler) = router {
        ("/api/reactor" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/accounts", handler::getAccounts)
            GET("/io/accounts", handler::getAccountsWithIO)
        }
    }
}