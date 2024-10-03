package io.clean.special_lecture.domain.special_lecture

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SpecialLectureTest {

    @Test
    fun `수강신청시 시작전이라면 IllegalStateException 발생`() {
        // given
        val now = DEFAULT_START_DATE_TIME.minusNanos(1)
        val specialLecture = createLecture()

        // when // then
        assertThatIllegalStateException().isThrownBy {
            specialLecture.enroll(1, now)
        }.withMessageContaining("수강신청이 시작되지 않았습니다.")
    }

    @Test
    fun `수강신청시 종료했다면 IllegalStateException 발생`() {
        // given
        val now = DEFAULT_END_DATE_TIME.plusNanos(1)
        val specialLecture = createLecture()

        // when // then
        assertThatIllegalStateException().isThrownBy {
            specialLecture.enroll(1, now)
        }.withMessageContaining("수강신청이 종료되었습니다.")
    }

    @Test
    fun `수강신청시 인원이 다 차면 IllegalStateException 발생`() {
        // given
        val now = DEFAULT_START_DATE_TIME.plusNanos(1)
        val specialLecture = createLecture(capacity = 0)

        // when // then
        assertThatIllegalStateException().isThrownBy {
            specialLecture.enroll(1, now)
        }.withMessageContaining("수강인원이 다 찼습니다.")
    }

    @Test
    fun `수강신청을 할 수 있다`() {
        // given
        val now = DEFAULT_START_DATE_TIME.plusNanos(1)
        val specialLecture = createLecture()

        // when
        specialLecture.enroll(1, now)

        // then
        assertThat(specialLecture.students).hasSize(1)
    }

    @Test
    fun `강연자를 추가할 수 있다`() {
        // given
        val title = "제목"
        val startAt = LocalDateTime.of(1995, 3, 26, 10, 0)
        val endAt = startAt.plusDays(1)
        val specialLecture = SpecialLecture(title, startAt, endAt)
        // when
        specialLecture.addLecturer( 1)

        // then
        assertThat(specialLecture.lecturers).hasSize(1)
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
