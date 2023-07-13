package com.example.travelapp.data.data

import android.content.Context
import androidx.room.Room

class UserRoomRepository(appContext: Context): UserRepository {
    private val db: UserDatabase
    private val dao: UserDao

    init {
        db = Room.databaseBuilder(appContext, UserDatabase::class.java, "user-db")
            .allowMainThreadQueries()
            .build()
        dao = db.userDao()
    }

    override fun load(): List<User> = dao.query()

    override fun save(items: List<User>) {
        db.runInTransaction {
            dao.deleteAll()
            dao.insert(items)
        }
    }
}