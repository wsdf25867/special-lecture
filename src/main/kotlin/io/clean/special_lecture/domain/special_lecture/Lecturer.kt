package io.clean.special_lecture.domain.special_lecture

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY

@Entity
class Lecturer(
    val userId: Long,
    @ManyToOne(fetch = LAZY)
    val specialLecture: SpecialLecture,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
)
