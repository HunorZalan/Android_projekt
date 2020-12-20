package com.example.projekt.data

import androidx.lifecycle.LiveData
import com.example.projekt.models.User

class UserRepository(private val userDao : UserDao) {

    val readAllData : LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun updateOneUser(name : String, add : String, em : String, ph : String, image : String, userid : Int){
        userDao.updateOneUser(name, add, em, ph, image, userid)
    }

    fun getUser() : LiveData<Int>{
        return userDao.getUser()
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

}