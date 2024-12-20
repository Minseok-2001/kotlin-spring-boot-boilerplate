package dev.kotlinspringbootboilerplate.infrastructure.security.model

import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import dev.kotlinspringbootboilerplate.domain.model.OAuthUserInfo

data class GoogleOAuthUserInfo(
	override val id: String,
	override val name: String,
	override val email: String
) : OAuthUserInfo {
	override val provider: OAuthProvider = OAuthProvider.GOOGLE
	override val providerSub: String = id

	companion object {
		fun from(attributes: Map<String, Any>): GoogleOAuthUserInfo {
			return GoogleOAuthUserInfo(
				id = attributes["sub"] as String,
				name = attributes["name"] as String,
				email = attributes["email"] as String
			)
		}
	}
}