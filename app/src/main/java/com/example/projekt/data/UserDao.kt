package com.example.projekt.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projekt.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE user_table SET username = :name, address = :add, email = :em, phone = :ph, img = :image WHERE id = :userid")
    suspend fun updateOneUser(name : String, add : String, em : String, ph : String, image : String, userid : Int)

    @Query("SELECT id FROM user_table ORDER BY id DESC")
    fun getUser() : LiveData<Int>

    @Query("SELECT * FROM user_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

}