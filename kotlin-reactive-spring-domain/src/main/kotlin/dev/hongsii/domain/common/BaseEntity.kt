package dev.hongsii.domain.common

import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
) {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(nullable = false, updatable = true)
    var updatedAt: LocalDateTime? = null
}
