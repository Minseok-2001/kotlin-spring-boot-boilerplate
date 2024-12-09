package dev.kotlinspringbootboilerplate.application.service

import dev.kotlinspringbootboilerplate.domain.model.AppleOAuthUserInfo
import dev.kotlinspringbootboilerplate.domain.model.GoogleOAuthUserInfo
import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import dev.kotlinspringbootboilerplate.domain.model.OAuthUserInfo


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
