package dev.kotlinspringbootboilerplate.domain.model

data class User(
	val id: Long,
	val name: String?,
	val nickname: String,
	val email: String,
	val provider: OAuthProvider,
	val providerSub: String
)