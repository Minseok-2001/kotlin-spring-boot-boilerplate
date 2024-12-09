package dev.kotlinspringbootboilerplate.infrastructure.security

import dev.kotlinspringbootboilerplate.domain.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
	private val jwtTokenService: JwtTokenService,
	private val userRepository: UserRepository
) : OncePerRequestFilter() {

	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		val token = getTokenFromRequest(request)
		if (token != null && jwtTokenService.validateToken(token)) {
			val userId = jwtTokenService.getUserIdFromToken(token)
			if (userId != null) {
				val user = userRepository.findUserById(userId.toLong())
				val authentication = UsernamePasswordAuthenticationToken(user, null, emptyList())
				SecurityContextHolder.getContext().authentication = authentication
			}
		}
		filterChain.doFilter(request, response)
	}

	private fun getTokenFromRequest(request: HttpServletRequest): String? {
		val bearerToken = request.getHeader("Authorization")
		return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			bearerToken.substring(7)
		} else null
	}
}