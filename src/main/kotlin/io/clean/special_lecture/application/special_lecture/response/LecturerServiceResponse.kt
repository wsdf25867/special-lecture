package io.clean.special_lecture.application.special_lecture.response

import io.clean.special_lecture.domain.special_lecture.Lecturer

data class LecturerServiceResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(lecturer: Lecturer): LecturerServiceResponse {
            return LecturerServiceResponse(
                id = lecturer.id,
                name = lecturer.user.name,
            )
        }
    }

}
