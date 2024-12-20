package dev.kotlinspringbootboilerplate.presentation.controller

import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController() {

	@GetMapping("/login/{provider}")
	fun login(@PathVariable provider: OAuthProvider): String {
		return "redirect:/oauth2/authorization/${provider.name.lowercase()}"
	}
}
