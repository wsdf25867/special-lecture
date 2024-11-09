package io.clean.special_lecture.application.special_lecture.response

import io.clean.special_lecture.domain.special_lecture.SpecialLecture
import java.time.LocalDateTime

data class SpecialLectureServiceResponse(
    val title: String,
    val enrollStartDateTime: LocalDateTime,
    val enrollEndDateTime: LocalDateTime,
    val capacity: Int,
    val id: Long,
    val lecturers: List<LecturerServiceResponse>
) {
    companion object {
        fun from(lecture: SpecialLecture): SpecialLectureServiceResponse {
            return SpecialLectureServiceResponse(
                title = lecture.title,
                enrollStartDateTime = lecture.enrollStartDateTime,
                enrollEndDateTime = lecture.enrollEndDateTime,
                capacity = lecture.capacity,
                id = lecture.id,
                lecturers = lecture.lecturers.map {
                    LecturerServiceResponse.from(it)
                }
            )
        }
    }

}
