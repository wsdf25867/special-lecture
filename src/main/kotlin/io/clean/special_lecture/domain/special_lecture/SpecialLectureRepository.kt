package io.clean.special_lecture.domain.special_lecture

import java.time.LocalDateTime

interface SpecialLectureRepository {
    fun findById(id: Long): SpecialLecture?

    fun save(lecture: SpecialLecture): SpecialLecture

    fun findLecturesByDateBetween(date: LocalDateTime): List<SpecialLecture>
}
