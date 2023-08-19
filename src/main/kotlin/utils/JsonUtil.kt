package utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object JsonUtil {
    val gson: Gson = GsonBuilder().create()
}