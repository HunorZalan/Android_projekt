package com.example.projekt.models

import android.os.Parcelable
import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val address: String,
    val email: String,
    val phone: String,
    val img: String
) : Parcelable
