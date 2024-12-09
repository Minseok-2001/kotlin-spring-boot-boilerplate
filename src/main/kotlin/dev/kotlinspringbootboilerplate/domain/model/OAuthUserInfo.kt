package dev.kotlinspringbootboilerplate.domain.model

interface OAuthUserInfo {
	val id: String
	val name: String
	val email: String
	val provider: OAuthProvider
	val providerSub: String
}
