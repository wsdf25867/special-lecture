package io.clean.special_lecture.domain.special_lecture

import io.clean.special_lecture.domain.special_lecture.fake.FakeSpecialLectureRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SpecialLectureRepositoryTest {

    private val specialLectureRepository = FakeSpecialLectureRepository()

    @Test
    fun `특강을 저장할 수 있다`() {
        // given
        val specialLecture = SpecialLecture("title", LocalDateTime.now(), LocalDateTime.now())

        // when
        val saved = specialLectureRepository.save(specialLecture)

        // then
        assertThat(saved.id).isEqualTo(1)
    }

    @Test
    fun `특강을 조회할 수 있다`() {
        // given
        val specialLecture = SpecialLecture("title", LocalDateTime.now(), LocalDateTime.now())
        val saved = specialLectureRepository.save(specialLecture)

        // when
        val found = specialLectureRepository.findById(saved.id)

        // then
        assertThat(found!!.id).isEqualTo(1)
        assertThat(found.title).isEqualTo("title")
    }

    @Test
    fun `날짜에 따라 수강 가능한 특강을 조회시 없다면 빈 리스트반환`() {
        // given
        // when
        val found = specialLectureRepository.findAllAbleToEnrollByDate(LocalDateTime.now())

        // then
        assertThat(found).isEmpty()
    }


    @Test
    fun `날짜에 따라 수강 가능한 특강을 조회할 수 있다`() {
        // given
        val specialLecture = createLecture()
        specialLectureRepository.save(specialLecture)

        // when
        val found = specialLectureRepository.findAllAbleToEnrollByDate(DEFAULT_START_DATE_TIME)

        // then
        assertThat(found).hasSize(1)
    }

    @Test
    fun `유저 id에 따라 신청한 특강목록을 조회시 없다면 빈리스트`() {
        // given
        val specialLecture = createLecture()
        specialLectureRepository.save(specialLecture)

        // when
        val found = specialLectureRepository.findAllEnrolledByUserId(1)

        // then
        assertThat(found).isEmpty()
    }

    @Test
    fun `유저 id에 따라 신청한 특강목록을 조회할 수 있다`() {
        // given
        val specialLecture = createLecture()
        specialLecture.enroll(1, DEFAULT_START_DATE_TIME.plusNanos(1))
        specialLectureRepository.save(specialLecture)

        // when
        val found = specialLectureRepository.findAllEnrolledByUserId(1)

        // then
        assertThat(found).hasSize(1)
    }
}
