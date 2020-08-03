package com.example.jetpacktest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(var name: String, var page: Int) {

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
}