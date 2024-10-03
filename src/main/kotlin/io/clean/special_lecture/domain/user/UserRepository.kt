package io.clean.special_lecture.domain.user

interface UserRepository {
    fun findById(id: Long): User?

    fun save(user: User): User
}
