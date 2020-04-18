package dev.hongsii.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import dev.hongsii.domain.account.Account
import dev.hongsii.domain.account.QAccount
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QueryDslConfig(
    @PersistenceContext
    private val em: EntityManager
) {

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(em)
    }
}

class CustomizedAccountRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : AccountRepositoryCustom {

    override fun findAllByQueryDsl(): List<Account> = jpaQueryFactory.selectFrom(QAccount.account).fetch()
}