package dev.kotlinspringbootboilerplate

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<KotlinSpringBootBoilerplateApplication>().with(TestcontainersConfiguration::class).run(*args)
}
