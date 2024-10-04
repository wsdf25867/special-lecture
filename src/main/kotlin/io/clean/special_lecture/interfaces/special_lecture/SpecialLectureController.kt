package io.clean.special_lecture.interfaces.special_lecture

import io.clean.special_lecture.application.special_lecture.SpecialLectureService
import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.interfaces.special_lecture.request.SpecialLectureRequest
import io.clean.special_lecture.interfaces.special_lecture.response.SpecialLectureResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/special-lectures")
class SpecialLectureController(
    private val specialLectureService: SpecialLectureService
) {

    @GetMapping("/able-to-enroll")
    fun findAllAbleToEnroll(
        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") date: LocalDateTime,
    ): List<SpecialLectureResponse> {
        return specialLectureService.findAllAbleToEnroll(date).map {
            SpecialLectureResponse.from(it)
        }
    }

    @GetMapping
    fun findAll(@RequestParam userId: Long): List<SpecialLectureResponse> {
        return specialLectureService.findAllEnrolledByUserId(userId).map {
            SpecialLectureResponse.from(it)
        }
    }

    @PostMapping("{special-lecture-id}/enroll")
    fun enroll(
        @PathVariable(value = "special-lecture-id") specialLectureId: Long,
        @RequestBody request: SpecialLectureRequest
    ) = specialLectureService.enroll(
        SpecialLectureServiceEnrollRequest(
            specialLectureId,
            request.userId,
            request.enrollDateTime
        )
    )
}
