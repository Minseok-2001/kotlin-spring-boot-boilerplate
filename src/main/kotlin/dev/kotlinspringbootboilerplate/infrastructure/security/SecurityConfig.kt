package dev.kotlinspringbootboilerplate.infrastructure.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
	@Autowired
	private val customOAuth2UserService: CustomOAuth2UserService,

	@Autowired
	private val jwtAuthenticationFilter: JwtAuthenticationFilter,

	@Autowired
	private val socialLoginService: SocialLoginService
) {

	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		http
			.cors { }
			.csrf { it.disable() }
			.httpBasic { it.disable() }
			.formLogin { it.disable() }
			.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
			.authorizeHttpRequests { authorize ->
				authorize
					.requestMatchers(
						"/auth/**",
						"/swagger",
						"/swagger-ui.html",
						"/swagger-ui/**",
						"/api-docs",
						"/api-docs/**",
						"/api",
						"/api/**",
						"/v3/api-docs/**",
						"/error"
					).permitAll()
					.anyRequest().authenticated()
			}
			.oauth2Login { oauth2 ->
				oauth2
					.userInfoEndpoint { it.userService(customOAuth2UserService) }
					.successHandler(oAuth2AuthenticationSuccessHandler())
			}
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

		return http.build()
	}

	@Bean
	fun oAuth2AuthenticationSuccessHandler(): AuthenticationSuccessHandler {
		return AuthenticationSuccessHandler { request, response, authentication ->

			val oAuth2User = authentication.principal as OAuth2User

			val tokenPair = socialLoginService.loginWithOAuth(oAuth2User)

			response.contentType = "application/json"
			response.characterEncoding = "UTF-8"
			response.writer.write(ObjectMapper().writeValueAsString(tokenPair))
		}
	}
}
