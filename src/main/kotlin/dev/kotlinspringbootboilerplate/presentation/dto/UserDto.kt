package dev.kotlinspringbootboilerplate.presentation.dto

import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import dev.kotlinspringbootboilerplate.domain.model.User


data class UserDto(
	val id: Long,
	val nickname: String,
	val email: String
) {
	companion object {
		fun fromDomain(user: User) = UserDto(
			id = user.id,
			nickname = user.nickname,
			email = user.email
		)
	}
}

data class UpdateUserDto(
	val nickname: String?,
	val email: String?,
)

data class UserInputDto(
	val nickname: String,
	val email: String,
	val provider: String,
	val name: String,
	val providerSub: String
) {
	fun toDomain(id: Long) = User(
		id = id,
		nickname = this.nickname,
		email = this.email,
		provider = OAuthProvider.valueOf(this.provider),
		name = this.name,
		providerSub = this.providerSub
	)
}

