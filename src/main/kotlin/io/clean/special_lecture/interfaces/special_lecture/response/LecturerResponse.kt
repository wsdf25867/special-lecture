package io.clean.special_lecture.interfaces.special_lecture.response

import io.clean.special_lecture.application.special_lecture.response.LecturerServiceResponse

data class LecturerResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(serviceResponse: LecturerServiceResponse): LecturerResponse {
            return LecturerResponse(
                id = serviceResponse.id,
                name = serviceResponse.name,
            )
        }
    }
}
