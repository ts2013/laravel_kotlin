package com.example.maplocationswithlaravelapi

import com.squareup.moshi.Json

class MapData (
    @Json(name = "id") val id: Int,
    @Json(name = "address")  val address: String,
    @Json(name = "zipcode")  val zipcode: String,
    @Json(name = "latitude") val addLocLat: String,
    @Json(name = "longitude") val addLocLon: String,
    @Json(name = "description") val description: String
)