package dev.kotlinspringbootboilerplate.infrastructure.config

import dev.kotlinspringbootboilerplate.infrastructure.security.CurrentUserMethodArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(val currentUserMethodArgumentResolver: CurrentUserMethodArgumentResolver) :
	WebMvcConfigurer {

	override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
		resolvers.add(currentUserMethodArgumentResolver)
	}

	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
			.allowedHeaders("*")
			.allowCredentials(true)
	}
}