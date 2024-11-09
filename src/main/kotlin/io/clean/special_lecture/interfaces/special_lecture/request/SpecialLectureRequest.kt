package io.clean.special_lecture.interfaces.special_lecture.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class SpecialLectureRequest(
    val userId: Long,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val enrollDateTime: LocalDateTime
)
