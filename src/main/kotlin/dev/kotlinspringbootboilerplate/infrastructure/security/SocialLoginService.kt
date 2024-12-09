package dev.kotlinspringbootboilerplate.infrastructure.security

import dev.kotlinspringbootboilerplate.domain.repository.UserRepository
import dev.kotlinspringbootboilerplate.presentation.dto.TokenPair
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class SocialLoginService(
	private val userRepository: UserRepository,
	private val jwtTokenService: JwtTokenService
) {
	fun loginWithOAuth(oAuth2User: OAuth2User): TokenPair {
		val providerSub = oAuth2User.getAttribute<String>("sub") ?: throw Exception("Provider sub not found")
		val user = userRepository.findUserByProviderSub(providerSub) ?: throw Exception("User not found")
		val userId = user.id ?: throw Exception("User id not found")

		return jwtTokenService.createTokenPair(userId)
	}
}
