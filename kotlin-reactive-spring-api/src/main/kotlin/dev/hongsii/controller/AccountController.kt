package dev.hongsii.controller

import dev.hongsii.domain.account.Account
import dev.hongsii.domain.account.AccountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountController(
        private val accountRepository: AccountRepository
) {

    @GetMapping
    fun getAccounts(): MutableList<Account> = accountRepository.findAll()
}