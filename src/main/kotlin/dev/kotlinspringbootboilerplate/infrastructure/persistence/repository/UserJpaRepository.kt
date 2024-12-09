package dev.kotlinspringbootboilerplate.infrastructure.persistence.repository

import dev.kotlinspringbootboilerplate.infrastructure.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
	fun findByNickname(nickname: String): UserEntity?
	fun findByEmail(email: String): UserEntity?
	fun findByProviderSub(providerSub: String): UserEntity?
}