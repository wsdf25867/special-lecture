package io.clean.special_lecture.application.special_lecture

import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.domain.special_lecture.SpecialLecture
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import io.clean.special_lecture.domain.user.User
import io.clean.special_lecture.domain.user.UserRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyLong
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime

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

    private fun createLecture(
        title: String = DEFAULT_TITLE,
        enrollStartDateTime: LocalDateTime = DEFAULT_START_DATE_TIME,
        enrollEndDateTime: LocalDateTime = DEFAULT_END_DATE_TIME,
        capacity: Int = DEFAULT_CAPACITY,
    ): SpecialLecture = SpecialLecture(
        title = title,
        enrollStartDateTime = enrollStartDateTime,
        enrollEndDateTime = enrollEndDateTime,
        capacity = capacity,
    )


    companion object {
        private const val DEFAULT_TITLE = "title"
        private const val DEFAULT_CAPACITY = 30
        private val DEFAULT_START_DATE_TIME = LocalDateTime.of(1995, 3, 26, 10, 0)
        private val DEFAULT_END_DATE_TIME = DEFAULT_START_DATE_TIME.plusDays(1)
    }
}
