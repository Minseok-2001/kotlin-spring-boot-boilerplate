package dev.kotlinspringbootboilerplate.infrastructure.security

import dev.kotlinspringbootboilerplate.presentation.dto.TokenPair
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtTokenService {
	@Value("\${jwt.secret}")
	private lateinit var jwtSecret: String

	@Value("\${jwt.accessTokenExpiration}")
	private lateinit var accessTokenExpiration: String

	@Value("\${jwt.refreshTokenExpiration}")
	private lateinit var refreshTokenExpiration: String

	private val secretKey: SecretKey by lazy {
		Keys.hmacShaKeyFor(jwtSecret.toByteArray())
	}

	fun createTokenPair(userId: Long): TokenPair {
		val accessToken = createToken(userId, accessTokenExpiration.toLong())
		val refreshToken = createToken(userId, refreshTokenExpiration.toLong())
		return TokenPair(accessToken, refreshToken)
	}

	fun validateToken(token: String): Boolean {
		return try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
			true
		} catch (e: Exception) {
			false
		}
	}

	fun getUserIdFromToken(token: String): String? {
		return try {
			val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
			claims.subject
		} catch (e: Exception) {
			null
		}
	}

	fun refreshAccessToken(refreshToken: String): String? {
		if (!validateToken(refreshToken)) return null
		val userId = getUserIdFromToken(refreshToken)?.toLongOrNull() ?: return null
		return createToken(userId, accessTokenExpiration.toLong())
	}

	private fun createToken(userId: Long, expirationMs: Long): String {
		val now = Date()
		val expiration = Date(now.time + expirationMs)

		return Jwts.builder().setSubject(userId.toString()).setIssuedAt(now).setExpiration(expiration)
			.signWith(secretKey, SignatureAlgorithm.HS256).compact()
	}
}