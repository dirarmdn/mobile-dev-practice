package com.example.opendatajabar.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(user: UserEntity) {
        userDao.insert(user)
    }

    suspend fun getUserById(userId: Int): UserEntity? {
        return userDao.getById(userId)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.update(user)
    }

}