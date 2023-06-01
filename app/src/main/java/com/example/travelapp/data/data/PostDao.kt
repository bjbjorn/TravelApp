package com.example.travelapp.data.data

import androidx.room.*

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun query(): List<Post>

    @Update
    fun update(items: List<Post>)

    @Query("DELETE FROM Post")
    fun deleteAll()

    @Insert
    fun insert(items: List<Post>)

}