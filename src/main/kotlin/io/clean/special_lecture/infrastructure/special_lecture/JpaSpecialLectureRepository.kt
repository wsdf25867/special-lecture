package io.clean.special_lecture.infrastructure.special_lecture

import io.clean.special_lecture.domain.special_lecture.SpecialLecture
import io.clean.special_lecture.domain.special_lecture.SpecialLectureRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaSpecialLectureRepository : SpecialLectureRepository, JpaRepository<SpecialLecture, Long>
