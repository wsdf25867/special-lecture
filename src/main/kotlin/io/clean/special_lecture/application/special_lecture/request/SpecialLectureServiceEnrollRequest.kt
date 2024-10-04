package io.clean.special_lecture.application.special_lecture.request

import java.time.LocalDateTime

data class SpecialLectureServiceEnrollRequest(
    val specialLectureId: Long,
    val userId: Long,
    val enrollDateTime: LocalDateTime = LocalDateTime.now(),
)
