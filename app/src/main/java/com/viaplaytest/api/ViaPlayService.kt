package com.viaplaytest.api

import retrofit2.Response
import retrofit2.http.GET

interface ViaPlayService {

    @GET("androiddash-se")
    suspend fun getTopNewsList(): Response<RootJson>

    companion object {
        const val ENDPOINT = "https://content.viaplay.se/"
    }
}
