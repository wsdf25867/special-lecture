package io.clean.special_lecture.infrastructure.special_lecture

import io.clean.special_lecture.domain.special_lecture.SpecialLecture
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface JpaSpecialLectureRepository : SpecialLectureRepository, JpaRepository<SpecialLecture, Long> {

    @Query("SELECT s FROM SpecialLecture s WHERE :date BETWEEN s.enrollStartDateTime AND s.enrollEndDateTime")
    override fun findLecturesByDateBetween(@Param("date") date: LocalDateTime): List<SpecialLecture>
}
