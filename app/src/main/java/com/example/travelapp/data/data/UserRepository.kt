package com.example.travelapp.data.data

interface UserRepository {
    fun load() : List<User>

    fun save(items: List<User>)
}