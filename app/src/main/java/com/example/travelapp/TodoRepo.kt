package com.example.travelapp


interface TodoRepo {
    fun save(items: List<Todo>)

    fun load(): List<Todo>
}
