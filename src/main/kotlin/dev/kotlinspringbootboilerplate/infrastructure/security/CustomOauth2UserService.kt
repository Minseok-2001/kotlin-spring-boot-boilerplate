package dev.kotlinspringbootboilerplate.infrastructure.security

import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import dev.kotlinspringbootboilerplate.domain.model.OAuthUserInfo
import dev.kotlinspringbootboilerplate.domain.model.User
import dev.kotlinspringbootboilerplate.domain.repository.UserRepository
import dev.kotlinspringbootboilerplate.infrastructure.service.RandomNicknameGenerator
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Transactional
@Service
class CustomOAuth2UserService(
	private val userRepository: UserRepository,
	private val randomNicknameGenerator: RandomNicknameGenerator

) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
		val delegate = DefaultOAuth2UserService()
		val oAuth2User = delegate.loadUser(userRequest)

		val provider = OAuthProvider.valueOf(userRequest.clientRegistration.registrationId.uppercase())
		val oauthUserInfo = OAuthUserInfoFactory.create(oAuth2User.attributes, provider)
		getOrInsertUser(oauthUserInfo)

		return oAuth2User

	}

	fun getOrInsertUser(oauthUserInfo: OAuthUserInfo): User {
		val user = userRepository.findUserByProviderSub(oauthUserInfo.providerSub)

		if (user != null) {
			return user
		}
		val randomUsername = randomNicknameGenerator.generateNickname()

		return userRepository.saveUser(
			User(
				id = 0,
				name = oauthUserInfo.name,
				email = oauthUserInfo.email,
				provider = oauthUserInfo.provider,
				nickname = randomUsername,
				providerSub = oauthUserInfo.providerSub
			)
		)
	}
}