package io.clean.special_lecture.application.special_lecture

import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import io.clean.special_lecture.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SpecialLectureService(
    private val specialLectureRepository: SpecialLectureRepository,
    private val userRepository: UserRepository,
) {

    fun enroll(request: SpecialLectureServiceEnrollRequest) {
        val specialLecture = requireNotNull(specialLectureRepository.findById(request.specialLectureId)) { "special lecture not found" }
        val user = requireNotNull(userRepository.findById(request.userId)) { "user not found" }

        specialLecture.enroll(user.id, request.enrollDateTime)
    }

}
