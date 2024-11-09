package io.clean.special_lecture.domain.user.fake

import io.clean.special_lecture.domain.user.User
import io.clean.special_lecture.domain.user.UserRepository

class FakeUserRepository : UserRepository {
    private val users: MutableMap<Long, User> = mutableMapOf()
    private var sequence: Long = 1
    override fun findById(id: Long): User? {
        return users[id]
    }

    override fun save(user: User): User {
        return User(user.name, sequence)
            .apply { users[sequence++] = this }
    }

}
