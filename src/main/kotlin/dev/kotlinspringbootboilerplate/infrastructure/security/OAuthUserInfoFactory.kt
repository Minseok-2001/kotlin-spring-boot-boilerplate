package dev.kotlinspringbootboilerplate.infrastructure.security

import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import dev.kotlinspringbootboilerplate.domain.model.OAuthUserInfo
import dev.kotlinspringbootboilerplate.infrastructure.security.model.AppleOAuthUserInfo
import dev.kotlinspringbootboilerplate.infrastructure.security.model.GoogleOAuthUserInfo


object OAuthUserInfoFactory {
	fun create(attributes: Map<String, Any>, provider: OAuthProvider): OAuthUserInfo {
		return when (provider) {
			OAuthProvider.GOOGLE -> GoogleOAuthUserInfo.from(attributes)
			OAuthProvider.APPLE -> AppleOAuthUserInfo.from(attributes)
			OAuthProvider.KAKAO -> TODO()
			OAuthProvider.NAVER -> TODO()
		}
	}
}
