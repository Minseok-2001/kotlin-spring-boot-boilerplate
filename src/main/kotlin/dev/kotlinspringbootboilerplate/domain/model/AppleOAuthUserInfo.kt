package dev.kotlinspringbootboilerplate.domain.model

data class AppleOAuthUserInfo(
	override val id: String,
	override val name: String,
	override val email: String
) : OAuthUserInfo {
	override val provider: OAuthProvider = OAuthProvider.APPLE
	override val providerSub: String = id

	companion object {
		fun from(attributes: Map<String, Any>): AppleOAuthUserInfo {
			return AppleOAuthUserInfo(
				id = attributes["sub"] as String,
				name = attributes["name"] as? String ?: "",
				email = attributes["email"] as String
			)
		}
	}
}
