package com.kotlinkurs.http_and_recyclerview

import retrofit2.Call
import retrofit2.http.GET

interface MyInterface {
    @GET("posts")

    fun getData(): Call<List<Post>>

}
