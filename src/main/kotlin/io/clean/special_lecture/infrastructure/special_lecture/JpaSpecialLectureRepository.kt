package io.clean.special_lecture.infrastructure.special_lecture

import io.clean.special_lecture.domain.special_lecture.SpecialLecture
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface JpaSpecialLectureRepository : SpecialLectureRepository, JpaRepository<SpecialLecture, Long> {

    @Query("select s from SpecialLecture s " +
            "join fetch s._lecturers l " +
            "join fetch l.user " +
            "where s.enrollStartDateTime <= :date " +
            "and s.enrollEndDateTime >= :date")
    override fun findAllAbleToEnrollByDate(@Param("date") date: LocalDateTime): List<SpecialLecture>

    @Query("select s " +
            "from SpecialLecture s " +
            "join fetch s._students ss " +
            "join fetch s._lecturers l " +
            "join fetch l.user " +
            "where ss.userId = :userId")
    override fun findAllEnrolledByUserId(userId: Long): List<SpecialLecture>
}
