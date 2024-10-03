package io.clean.special_lecture.application.special_lecture

import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.application.special_lecture.response.SpecialLectureServiceResponse
import io.clean.special_lecture.domain.special_lecture.DEFAULT_START_DATE_TIME
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import io.clean.special_lecture.domain.special_lecture.createLecture
import io.clean.special_lecture.domain.special_lecture.fake.FakeSpecialLectureRepository
import io.clean.special_lecture.domain.user.User
import io.clean.special_lecture.domain.user.UserRepository
import io.clean.special_lecture.domain.user.fake.FakeUserRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class SpecialLectureServiceTest{
    private val userRepository: UserRepository = FakeUserRepository()
    private val specialLectureRepository: SpecialLectureRepository = FakeSpecialLectureRepository()
    private val sut: SpecialLectureService = SpecialLectureService(specialLectureRepository, userRepository)

    @Test
    fun `특강 신청시 없는 specialLectureId 라면 IllegalArgumentException이 발생한다`() {
        // given
        val request = SpecialLectureServiceEnrollRequest(1, 1)

        // when // then
        assertThatIllegalArgumentException().isThrownBy {
            sut.enroll(request)
        }
    }

    @Test
    fun `특강 신청시 없는 userId 라면 IllegalArgumentException이 발생한다`() {
        // given
        val request = SpecialLectureServiceEnrollRequest(1, 1)
        specialLectureRepository.save(createLecture())

        // when // then
        assertThatIllegalArgumentException().isThrownBy {
            sut.enroll(request)
        }
    }

    @Test
    fun `특강 신청을 할 수 있다`() {
        //given
        val request = SpecialLectureServiceEnrollRequest(1, 1, DEFAULT_START_DATE_TIME)
        specialLectureRepository.save(createLecture())
        userRepository.save(User("강지우"))

        //when //then
        assertThatNoException().isThrownBy {
            sut.enroll(request)
        }
    }

    @Test
    fun `신청가능한 특강 목록을 조회할 수 있다`() {
        // given
        val lecture = createLecture()
        specialLectureRepository.save(lecture)

        // when
        val actual = sut.findAllAbleToEnroll(DEFAULT_START_DATE_TIME)

        // then
        assertThat(actual).isNotEmpty
        assertThat(actual[0]).isInstanceOf(SpecialLectureServiceResponse::class.java)
    }

    @Test
    fun `신청한 특강 목록을 조회할 수 있다`() {
        // given
        val lecture = createLecture()
        lecture.enroll(1, DEFAULT_START_DATE_TIME)
        specialLectureRepository.save(lecture)

        // when
        val actual = sut.findAllEnrolledByUserId(1)

        // then
        assertThat(actual).isNotEmpty
        assertThat(actual[0]).isInstanceOf(SpecialLectureServiceResponse::class.java)
    }
}
