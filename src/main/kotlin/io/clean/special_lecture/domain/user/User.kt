package io.clean.special_lecture.domain.user

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
)
