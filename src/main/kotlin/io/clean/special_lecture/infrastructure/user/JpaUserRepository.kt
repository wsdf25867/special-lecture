package io.clean.special_lecture.infrastructure.user

import io.clean.special_lecture.domain.user.User
import io.clean.special_lecture.domain.user.UserRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository : UserRepository, JpaRepository<User, Long> {
}
