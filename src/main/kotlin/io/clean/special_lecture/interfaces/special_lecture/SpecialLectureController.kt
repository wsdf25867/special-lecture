package io.clean.special_lecture.interfaces.special_lecture

import io.clean.special_lecture.application.special_lecture.SpecialLectureService
import io.clean.special_lecture.interfaces.special_lecture.response.SpecialLectureResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
}
