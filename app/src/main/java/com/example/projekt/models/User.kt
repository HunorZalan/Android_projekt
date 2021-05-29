package com.example.projekt.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val address: String,
    val email: String,
    val phone: String,
    val img: String
)
