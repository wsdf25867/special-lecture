package io.clean.special_lecture.domain.special_lecture

import java.time.LocalDateTime

interface SpecialLectureRepository {
    fun findById(id: Long): SpecialLecture?

    fun save(lecture: SpecialLecture): SpecialLecture

    fun findAllAbleToEnrollByDate(date: LocalDateTime): List<SpecialLecture>

    fun findAllEnrolledByUserId(userId: Long): List<SpecialLecture>

    fun findByIdWithLock(id: Long): SpecialLecture?
}
