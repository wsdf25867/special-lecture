package io.clean.special_lecture.support.time

import java.time.LocalDateTime

fun LocalDateTime.isAfterOrEqual(other: LocalDateTime): Boolean =
        this.isAfter(other) || this.isEqual(other)

fun LocalDateTime.isBeforeOrEqual(other: LocalDateTime): Boolean =
        this.isBefore(other) || this.isEqual(other)
