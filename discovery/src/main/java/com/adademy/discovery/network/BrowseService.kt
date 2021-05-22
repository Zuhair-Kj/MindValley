package com.adademy.discovery.network

import com.adademy.discovery.model.CategoriesResponse
import com.adademy.discovery.model.ChannelsResponse
import com.adademy.discovery.model.LatestEpisodesRespone
import retrofit2.http.GET

interface BrowseService {
    @GET("/raw/z5AExTtw")
    suspend fun getLatestEpisodes(): LatestEpisodesRespone

    @GET("/raw/Xt12uVhM")
    suspend fun getChannels(): ChannelsResponse

    @GET("/raw/A0CgArX3")
    suspend fun getCategories(): CategoriesResponse
}