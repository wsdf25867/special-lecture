package io.clean.special_lecture.domain.user

import io.clean.special_lecture.domain.user.fake.FakeUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserRepositoryTest {

    private val repository = FakeUserRepository()

    @Test
    fun `유저를 저장할 수 있다`() {
        // given
        val user = User("name")

        // when
        val saved = repository.save(user)

        // then
        assertThat(saved.id).isEqualTo(1)
    }

    @Test
    fun `없은 userId로 조회시 Null을 반환한다`() {
        // given
        val user = User("name")

        // when
        val found = repository.findById(1)

        // then
        assertThat(found).isNull()
    }

    @Test
    fun `유저를 조회할 수 있다`() {
        // given
        val user = User("name")
        val saved = repository.save(user)

        // when
        val found = repository.findById(1)

        // then
        assertThat(found!!.name).isEqualTo("name")
    }
}
