package com.example.travelapp.data.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Post::class), version = 1)
abstract class PostDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
}