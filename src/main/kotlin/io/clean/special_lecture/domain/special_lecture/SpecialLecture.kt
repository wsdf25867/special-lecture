package io.clean.special_lecture.domain.special_lecture

import io.clean.special_lecture.support.time.isAfterOrEqual
import io.clean.special_lecture.support.time.isBeforeOrEqual
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class SpecialLecture(
    val title: String,
    val enrollStartDateTime: LocalDateTime,
    val enrollEndDateTime: LocalDateTime,
    val capacity: Int = 30,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], mappedBy = "specialLecture")
    private val _students: MutableSet<Student> = mutableSetOf()
    val students: Set<Student>
        get() = _students.toSet()

    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], mappedBy = "specialLecture")
    private val _lecturers: MutableSet<Lecturer> = mutableSetOf()
    val lecturers: Set<Lecturer>
        get() = _lecturers.toSet()

    fun enroll(userId: Long, enrollDateTime: LocalDateTime) {
        check(enrollDateTime.isAfterOrEqual(enrollStartDateTime)) { "수강신청이 시작되지 않았습니다." }
        check(enrollDateTime.isBeforeOrEqual(enrollEndDateTime)) { "수강신청이 종료되었습니다." }
        check(students.size < capacity) { "수강인원이 다 찼습니다." }
        _students.add(Student(userId, this))
    }

    fun addLecturer(userId: Long) {
        _lecturers.add(Lecturer(userId, this))
    }
}
