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
}
