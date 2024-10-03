package io.clean.special_lecture.application.special_lecture

import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.application.special_lecture.response.SpecialLectureServiceResponse
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import io.clean.special_lecture.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class SpecialLectureService(
    private val specialLectureRepository: SpecialLectureRepository,
    private val userRepository: UserRepository,
) {

    fun enroll(request: SpecialLectureServiceEnrollRequest) {
        val specialLecture =
            requireNotNull(specialLectureRepository.findById(request.specialLectureId)) { "special lecture not found" }
        val user = requireNotNull(userRepository.findById(request.userId)) { "user not found" }

        specialLecture.enroll(user.id, request.enrollDateTime)
    }

    @Transactional(readOnly = true)
    fun findAllAbleToEnroll(date: LocalDateTime): List<SpecialLectureServiceResponse> {
        return specialLectureRepository.findAllAbleToEnrollByDate(date).map {
            SpecialLectureServiceResponse.from(it)
        }
    }

    @Transactional(readOnly = true)
    fun findAllEnrolledByUserId(userId: Long): List<SpecialLectureServiceResponse> {
        return specialLectureRepository.findAllEnrolledByUserId(userId).map {
            SpecialLectureServiceResponse.from(it)
        }
    }

}
