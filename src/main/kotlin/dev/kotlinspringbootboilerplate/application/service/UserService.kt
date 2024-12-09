package dev.kotlinspringbootboilerplate.application.service

import dev.kotlinspringbootboilerplate.domain.model.User
import dev.kotlinspringbootboilerplate.domain.repository.UserRepository
import dev.kotlinspringbootboilerplate.presentation.dto.UpdateUserDto
import dev.kotlinspringbootboilerplate.presentation.dto.UserDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
	private val userRepository: UserRepository,
)  {

	@Transactional(readOnly = true)
	 fun getUser(user: User): UserDto {
		val findUser = userRepository.findUserById(user.id) ?: throw Exception("User not found")

		return UserDto.fromDomain(findUser)
	}

	@Transactional
	 fun updateUser(user: User, updateUserDto: UpdateUserDto): UserDto {
		updateUserDto.nickname?.let {
			validDuplicateNickname(it)
		}

		val updatedUser = user.copy(
			nickname = updateUserDto.nickname ?: user.nickname,
			email = updateUserDto.email ?: user.email
		)
		val result = userRepository.updateUser(updatedUser)
		return UserDto.fromDomain(result)
	}


	@Transactional
	 fun deleteUser(user: User) {
		userRepository.deleteUser(user.id)
	}

	private fun validDuplicateNickname(nickname: String) {
		userRepository.findUserByNickname(nickname)?.let {
			throw Exception("Nickname already exists")
		}
	}
}