package io.clean.special_lecture.domain.special_lecture.fake

import io.clean.special_lecture.domain.special_lecture.SpecialLecture
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import io.clean.special_lecture.support.time.isAfterOrEqual
import io.clean.special_lecture.support.time.isBeforeOrEqual
import java.time.LocalDateTime

class FakeSpecialLectureRepository : SpecialLectureRepository {
    private val lectures: MutableMap<Long, SpecialLecture> = mutableMapOf()
    private var sequence: Long = 1

    override fun findById(id: Long): SpecialLecture? = lectures[id]

    override fun save(lecture: SpecialLecture): SpecialLecture {
        return SpecialLecture(
            title = lecture.title,
            enrollStartDateTime = lecture.enrollStartDateTime,
            enrollEndDateTime = lecture.enrollEndDateTime,
            capacity = lecture.capacity,
            id = sequence,
        ).apply {
            lecture.lecturers.forEach {
                this.addLecturer(it.user)
            }
            lecture.students.forEach {
                this.enroll(it.userId, LocalDateTime.now())
            }
        }.apply {
            lectures[sequence++] = this
        }
    }

    override fun findLecturesByDateBetween(date: LocalDateTime): List<SpecialLecture> =
        lectures.values.filter {
            date.isAfterOrEqual(it.enrollStartDateTime) &&
                    date.isBeforeOrEqual(it.enrollEndDateTime)
        }
}
