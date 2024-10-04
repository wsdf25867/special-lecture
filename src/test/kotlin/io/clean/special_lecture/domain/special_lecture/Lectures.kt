package io.clean.special_lecture.domain.special_lecture

import java.time.LocalDateTime

private const val DEFAULT_TITLE = "title"
private const val DEFAULT_CAPACITY = 30
internal val DEFAULT_START_DATE_TIME = LocalDateTime.of(1995, 3, 26, 10, 0)
internal val DEFAULT_END_DATE_TIME = DEFAULT_START_DATE_TIME.plusDays(1)

fun createLecture(
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



