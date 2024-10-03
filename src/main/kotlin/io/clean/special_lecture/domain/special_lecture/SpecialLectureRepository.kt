package io.clean.special_lecture.domain.special_lecture

interface SpecialLectureRepository {
    fun findById(id: Long): SpecialLecture?

    fun save(lecture: SpecialLecture): SpecialLecture
}
