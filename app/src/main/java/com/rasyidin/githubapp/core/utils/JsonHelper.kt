package com.rasyidin.githubapp.core.utils

import android.content.Context
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.data.source.remote.response.Users
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(): String? {
        val jsonString: String
        try {
            jsonString =
                context.resources.openRawResource(R.raw.user).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getUsers(): List<Users> {
        val list = ArrayList<Users>()
        val responseObject = JSONObject(parsingFileToString().toString())
        val listArray = responseObject.getJSONArray("users")

        for (i in 0 until listArray.length()) {
            val user = listArray.getJSONObject(i)

            val username = user.getString("username")
            val name = user.getString("name")
            val avatar = user.getString("avatar")
            val company = user.getString("company")
            val location = user.getString("location")
            val repository = user.getInt("repository")
            val follower = user.getInt("follower")
            val following = user.getInt("following")

            val userResponse = Users(
                username = username,
                name = name,
                avatar = avatar,
                company = company,
                location = location,
                repository = repository,
                follower = follower,
                following = following
            )
            list.add(userResponse)
        }

        return list
    }
}