package com.example.travelapp.data.data


interface PostRepository {

    fun load(): List<Post>

    fun save(items: List<Post>)
}