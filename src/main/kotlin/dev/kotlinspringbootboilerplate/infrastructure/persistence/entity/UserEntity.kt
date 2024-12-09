package dev.kotlinspringbootboilerplate.infrastructure.persistence.entity

import dev.kotlinspringbootboilerplate.domain.model.BaseEntity
import dev.kotlinspringbootboilerplate.domain.model.OAuthProvider
import dev.kotlinspringbootboilerplate.domain.model.User
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "user_account")
@SQLDelete(sql = "UPDATE user_account SET deleted = true WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class UserEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,

	@Column(nullable = false, unique = true)
	var email: String,

	@Column(nullable = false)
	var nickname: String,

	@Column(name = "provider")
	val provider: String,

	@Column(name = "name")
	val name: String? = null,

	@Column(name = "provider_sub")
	val providerSub: String? = null,

	@Column(name = "deleted_at")
	var deletedAt: LocalDateTime? = null
) : BaseEntity() {
	fun toDomain() = User(
		id = id!!,
		email = email,
		nickname = nickname,
		provider = OAuthProvider.valueOf(provider),
		name = name,
		providerSub = providerSub!!
	)

	companion object {
		fun from(user: User) = UserEntity(
			id = user.id,
			email = user.email,
			nickname = user.nickname,
			provider = user.provider.name,
			name = user.name,
			providerSub = user.providerSub
		)
	}
}
