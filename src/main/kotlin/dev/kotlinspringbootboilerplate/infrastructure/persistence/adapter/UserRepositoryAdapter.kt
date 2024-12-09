package dev.kotlinspringbootboilerplate.infrastructure.persistence.adapter

import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import dev.kotlinspringbootboilerplate.domain.model.User
import dev.kotlinspringbootboilerplate.domain.repository.UserRepository
import dev.kotlinspringbootboilerplate.infrastructure.persistence.entity.UserEntity
import dev.kotlinspringbootboilerplate.infrastructure.persistence.repository.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
	private val userJpaRepository: UserJpaRepository
) : UserRepository {
	override fun saveUser(user: User): User {
		val userEntity = UserEntity(
			nickname = user.nickname,
			email = user.email,
			provider = user.provider.toString(),
			name = user.name,
			providerSub = user.providerSub
		)

		val savedEntity = userJpaRepository.save(userEntity)

		return User(
			id = savedEntity.id ?: throw IllegalStateException("User entity ID cannot be null"),
			nickname = savedEntity.nickname,
			email = savedEntity.email,
			provider = OAuthProvider.valueOf(savedEntity.provider),
			name = savedEntity.name ?: "",
			providerSub = savedEntity.providerSub ?: ""
		)
	}

	override fun findUserById(id: Long): User? {
		return userJpaRepository.findById(id).orElse(null)?.toDomain()
	}

	override fun findUserByNickname(nickname: String): User? {
		return userJpaRepository.findByNickname(nickname)?.toDomain()
	}

	override fun findUserByEmail(email: String): User? {
		return userJpaRepository.findByEmail(email)?.toDomain()
	}

	override fun findUserByProviderSub(providerSub: String): User? {
		return userJpaRepository.findByProviderSub(providerSub)?.toDomain()
	}

	override fun updateUser(user: User): User {
		val existingEntity = userJpaRepository.getReferenceById(user.id)
		existingEntity.apply {
			nickname = user.nickname
			email = user.email
		}
		return existingEntity.toDomain()
	}

	override fun deleteUser(id: Long) {
		return userJpaRepository.deleteById(id)
	}
}