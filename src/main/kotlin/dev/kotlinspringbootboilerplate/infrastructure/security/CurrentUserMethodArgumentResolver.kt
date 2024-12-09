package dev.kotlinspringbootboilerplate.infrastructure.security

import dev.kotlinspringbootboilerplate.domain.model.User
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CurrentUserMethodArgumentResolver : HandlerMethodArgumentResolver {

	override fun supportsParameter(parameter: MethodParameter): Boolean {
		return parameter.getParameterAnnotation(CurrentUser::class.java) != null
				&& parameter.parameterType == User::class.java
	}

	override fun resolveArgument(
		parameter: MethodParameter,
		mavContainer: ModelAndViewContainer?,
		webRequest: NativeWebRequest,
		binderFactory: WebDataBinderFactory?
	): User? {
		val authentication = SecurityContextHolder.getContext().authentication

		try {
			if (authentication != null && authentication.principal != null) {
				return authentication.principal as User
			}
		} catch (e: ClassCastException) {
			throw UsernameNotFoundException("User not found or not authenticated")
		}

		return null
	}
}
