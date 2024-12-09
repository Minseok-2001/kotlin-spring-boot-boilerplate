package dev.kotlinspringbootboilerplate.domain.repository

import dev.kotlinspringbootboilerplate.domain.model.User

interface UserRepository {
	fun saveUser(user: User): User
	fun findUserById(id: Long): User?
	fun findUserByNickname(nickname: String): User?
	fun findUserByEmail(email: String): User?
	fun findUserByProviderSub(providerSub: String): User?
	fun updateUser(user: User): User
	fun deleteUser(id: Long)
}