package com.example.maplocationswithlaravelapi

import com.example.maplocationswithlaravelapi.Config.Companion.BASE_URL
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 *  Moshi builder
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 *  Retrofit with Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface APIService {

    @FormUrlEncoded
    @POST("api/map-location")
    suspend fun createMapLocation(@FieldMap params: HashMap<String?, String?>): Response<ResponseBody>

    @GET("api/map-location")
    suspend fun getMapLocations(): List<MapData>

}

object DataApi {
    val retrofitService : APIService by lazy {
        retrofit.create(APIService::class.java) }
}