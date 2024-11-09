package io.clean.special_lecture.domain.special_lecture

import io.clean.special_lecture.domain.user.User
import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY

@Entity
class Lecturer(
    @ManyToOne(fetch = LAZY)
    val user: User,
    @ManyToOne(fetch = LAZY)
    val specialLecture: SpecialLecture,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
)
