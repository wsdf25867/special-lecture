package io.clean.special_lecture.application.special_lecture.concurrency

import io.clean.special_lecture.application.special_lecture.SpecialLectureService
import io.clean.special_lecture.application.special_lecture.request.SpecialLectureServiceEnrollRequest
import io.clean.special_lecture.domain.special_lecture.SpecialLecture
import io.clean.special_lecture.domain.user.User
import io.clean.special_lecture.infrastructure.special_lecture.JpaSpecialLectureRepository
import io.clean.special_lecture.infrastructure.user.JpaUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.test.Test

@SpringBootTest
class SpecialLectureServiceConcurrencyTest {

    @Autowired
    private lateinit var service: SpecialLectureService

    @Autowired
    private lateinit var repository: JpaSpecialLectureRepository

    @Autowired
    private lateinit var userRepository: JpaUserRepository

    @Test
    fun `동시에 40명이 특강 신청, 30명만 성공`() {
        // given
        val specialLecture = SpecialLecture(
            title = "Concurrency Lecture",
            enrollStartDateTime = LocalDateTime.now().minusDays(1),
            enrollEndDateTime = LocalDateTime.now().plusDays(1),
            capacity = 30
        )
        repository.save(specialLecture)

        val totalApplicants = 40
        val threadPool = Executors.newFixedThreadPool(totalApplicants)
        val latch = CountDownLatch(totalApplicants)
        val results = mutableListOf<Boolean>()

        repeat(totalApplicants) { i ->
            userRepository.save(User("name$i"))
        }

        // when
        for (i in 1..totalApplicants) {
            threadPool.execute {
                try {
                    service.enroll(SpecialLectureServiceEnrollRequest(1, i.toLong(), LocalDateTime.now()))
                    synchronized(results) { results.add(true) } // 신청 성공
                } catch (e: Exception) {
                    println("error!!: $e.message")
                    synchronized(results) { results.add(false) } // 신청 실패
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await() // 모든 스레드가 끝날 때까지 대기

        // then
        assertThat(30).isEqualTo(results.count { it }) // 30명만 성공했는지 확인
        assertThat(10).isEqualTo(results.count { !it }) // 10명은 실패했는지 확인
    }

    @Test
    fun `동일한 유저가 5번 신청, 중복 방지하여 1번만 성공`() {
        // given
        val specialLecture = SpecialLecture(
            title = "Duplicate User Test Lecture",
            enrollStartDateTime = LocalDateTime.now().minusDays(1),
            enrollEndDateTime = LocalDateTime.now().plusDays(1),
            capacity = 30
        )
        val saved = repository.save(specialLecture)

        val userId = 1L
        val totalAttempts = 5
        val threadPool = Executors.newFixedThreadPool(totalAttempts)
        val latch = CountDownLatch(totalAttempts)
        val results = mutableListOf<Boolean>()

        // when
        for (i in 1..totalAttempts) {
            threadPool.execute {
                try {
                    service.enroll(SpecialLectureServiceEnrollRequest(saved.id, userId, LocalDateTime.now()))
                    results.add(true) // 신청 성공
                } catch (e: Exception) {
                    results.add(false) // 신청 실패
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await() // 모든 스레드가 끝날 때까지 대기

        // then
        assertThat(1).isEqualTo(results.count { it }) // 1번만 성공했는지 확인
        assertThat(4).isEqualTo(results.count { !it }) // 4번은 실패했는지 확인
    }
}
