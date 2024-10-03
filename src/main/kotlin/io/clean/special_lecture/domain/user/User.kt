package io.clean.special_lecture.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    val name: String,
    @Id
    @GeneratedValue
    val id: Long = 0,
)
