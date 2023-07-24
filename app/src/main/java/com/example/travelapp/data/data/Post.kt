package com.example.travelapp.data.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.serialization.Serializable
@Entity
data class Post(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "posted_by") val Name: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean,
    @ColumnInfo(name = "text") var text: String?,
    @ColumnInfo(name = "images") var images: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
): java.io.Serializable