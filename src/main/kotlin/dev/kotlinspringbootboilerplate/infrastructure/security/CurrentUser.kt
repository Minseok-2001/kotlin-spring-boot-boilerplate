package dev.kotlinspringbootboilerplate.infrastructure.security

import io.swagger.v3.oas.annotations.Parameter

@Parameter(hidden = true)
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class CurrentUser
