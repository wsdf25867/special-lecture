package io.clean.special_lecture.domain.special_lecture

import jakarta.persistence.*

@Entity
class Student(
    val userId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    val specialLecture: SpecialLecture,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
)
