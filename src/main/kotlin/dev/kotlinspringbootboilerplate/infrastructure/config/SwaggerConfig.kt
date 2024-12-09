package dev.kotlinspringbootboilerplate.infrastructure.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SecurityScheme(
	name = "bearerAuth",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer"
)
@Configuration
class SwaggerConfig {

	@Bean
	fun openAPI(): OpenAPI = OpenAPI()
		.components(Components())
		.addSecurityItem(SecurityRequirement().addList("bearerAuth"))
		.info(apiInfo())

	private fun apiInfo() = Info()
		.title("API")
		.description("")
		.version("1.0.0")
}