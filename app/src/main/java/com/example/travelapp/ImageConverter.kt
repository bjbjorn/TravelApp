package com.example.travelapp

import androidx.room.TypeConverter
import kotlin.collections.ArrayList

class ImageConverter {
    @TypeConverter
    fun toImages(value: String?): Images {
        if (value == null || value.isEmpty()) {
            return Images()
        }

        val list: List<String> = value.split(",")
        val longList = ArrayList<String>()
        for (item in list) {
            if (!item.isEmpty()) {
                longList.add(item)
            }
        }
        val imageClass = Images()
        imageClass.images = longList
        return imageClass
    }

    @TypeConverter
    fun toString(images: Images?): String {

        var string = ""

        if (images == null) {
            return string
        }

        images.images?.forEach {
            string += "$it,"
        }
        return string
    }
}