package com.example.gamelog.data.api

import com.example.gamelog.data.api.response.GameResponse
import com.example.gamelog.data.model.game.GameDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("search") name: String,
        @Query("page") page: Int? = null
    ): GameResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int
    ): GameDetail

}