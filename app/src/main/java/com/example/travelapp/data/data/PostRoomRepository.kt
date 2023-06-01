package com.example.travelapp.data.data

import android.content.Context
import androidx.room.Room

class PostRoomRepository(appContext: Context) : PostRepository {

    private val db: PostDatabase
    private val dao: PostDao

    init {
        db = Room.databaseBuilder(appContext, PostDatabase::class.java, "post-db")
            .allowMainThreadQueries()
            .build()
        dao = db.postDao()
    }

    override fun load(): List<Post> = dao.query()

    override fun save(items: List<Post>) {
        db.runInTransaction {
            dao.deleteAll()
            dao.insert(items)
        }
    }

}