package dev.hongsii.repository

import dev.hongsii.domain.account.Account

interface AccountRepositoryCustom {

    fun findAllByQueryDsl(): List<Account>
}