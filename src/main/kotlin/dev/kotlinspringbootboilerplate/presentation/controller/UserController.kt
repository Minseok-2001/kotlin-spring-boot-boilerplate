package dev.kotlinspringbootboilerplate.presentation.controller

import dev.kotlinspringbootboilerplate.application.service.UserService
import dev.kotlinspringbootboilerplate.domain.model.User
import dev.kotlinspringbootboilerplate.infrastructure.security.CurrentUser
import dev.kotlinspringbootboilerplate.presentation.dto.UpdateUserDto
import dev.kotlinspringbootboilerplate.presentation.dto.UserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User related operations")
@SecurityRequirement(name = "bearerAuth")
class UserController(
	private val userService: UserService
) {


	@Operation(
		summary = "Get current user information",
		description = "Retrieves the information of the currently authenticated user"
	)
	@GetMapping
	fun getUser(@CurrentUser user: User): ResponseEntity<UserDto> {
		val user = userService.getUser(user)
		return ResponseEntity.ok(user)
	}

	@PutMapping
	fun updateUser(@CurrentUser user: User, updateUserDto: UpdateUserDto): ResponseEntity<UserDto> {
		val user = userService.updateUser(user, updateUserDto)
		return ResponseEntity.ok(user)
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun deleteUser(@CurrentUser user: User) {
		userService.deleteUser(user)
	}
}