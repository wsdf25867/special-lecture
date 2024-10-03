package io.clean.special_lecture.application.special_lecture

import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.domain.special_lecture.DEFAULT_START_DATE_TIME
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import io.clean.special_lecture.domain.special_lecture.createLecture
import io.clean.special_lecture.domain.user.User
import io.clean.special_lecture.domain.user.UserRepository
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class SpecialLectureServiceTest {

    private val specialLectureRepository = Mockito.mock(SpecialLectureRepository::class.java)
    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val sut = SpecialLectureService(specialLectureRepository, userRepository)

    @Test
    fun `특강 신청시 없는 specialLectureId 라면 IllegalArgumentException이 발생한다`() {
        // given
        val request = SpecialLectureServiceEnrollRequest(1, 1)
        given(specialLectureRepository.findById(anyLong()))
            .willReturn(null)

        // when // then
        assertThatIllegalArgumentException().isThrownBy {
            sut.enroll(request)
        }
    }

    @Test
    fun `특강 신청시 없는 userId 라면 IllegalArgumentException이 발생한다`() {
        // given
        val request = SpecialLectureServiceEnrollRequest(1, 1)
        given(specialLectureRepository.findById(anyLong()))
            .willReturn(createLecture())
        given(userRepository.findById(anyLong()))
            .willReturn(null)

        // when // then
        assertThatIllegalArgumentException().isThrownBy {
            sut.enroll(request)
        }
    }

    @Test
    fun `특강 신청을 할 수 있다`() {
        //given
        val request = SpecialLectureServiceEnrollRequest(1, 1, DEFAULT_START_DATE_TIME)
        given(specialLectureRepository.findById(anyLong()))
            .willReturn(createLecture())
        given(userRepository.findById(anyLong()))
            .willReturn(User("강지우"))

        //when //then
        assertThatNoException().isThrownBy {
            sut.enroll(request)
        }
    }
}
