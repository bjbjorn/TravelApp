package com.example.travelapp.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo


@kotlinx.serialization.Serializable
@Entity
data class User (
    @ColumnInfo(name = "Username") var username : String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
): java.io.Serializable {

}