package com.cookpad.hiring.android.data.room

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object Converters {

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromListToString(data: List<String>): String {
        return moshi.adapter(List::class.java).toJson(data)
    }

    @TypeConverter
    fun fromStringToList(json: String): List<String>? {
        val listMyData = Types.newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter = moshi.adapter<List<String>>(listMyData)
        return jsonAdapter.fromJson(json)
    }
}
