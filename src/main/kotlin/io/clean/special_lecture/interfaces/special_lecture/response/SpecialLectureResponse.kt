package io.clean.special_lecture.interfaces.special_lecture.response

import io.clean.special_lecture.application.special_lecture.response.SpecialLectureServiceResponse
import java.time.LocalDateTime

data class SpecialLectureResponse(
    val title: String,
    val enrollStartDateTime: LocalDateTime,
    val enrollEndDateTime: LocalDateTime,
    val capacity: Int,
    val id: Long,
    val lecturers: List<LecturerResponse>
) {
    companion object {
        fun from(serviceResponse: SpecialLectureServiceResponse): SpecialLectureResponse {
            return SpecialLectureResponse(
                title = serviceResponse.title,
                enrollStartDateTime = serviceResponse.enrollStartDateTime,
                enrollEndDateTime = serviceResponse.enrollEndDateTime,
                capacity = serviceResponse.capacity,
                id = serviceResponse.id,
                lecturers = serviceResponse.lecturers.map {
                    LecturerResponse.from(it)
                }
            )
        }
    }
}
