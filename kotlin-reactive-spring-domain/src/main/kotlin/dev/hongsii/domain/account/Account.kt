package dev.hongsii.domain.account

import dev.hongsii.domain.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
data class Account(
        @Column
        val email: String
) : BaseEntity()
