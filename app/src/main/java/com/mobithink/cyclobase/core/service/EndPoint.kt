package com.mobithink.cyclobase.core.service

import com.mobithink.cyclobase.database.model.TripDTO
import com.mobithink.cyclobase.starter.model.SignInPayload
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EndPoint {

    companion object {
        const val API = "/api"
        const val V1 = "/v1"

        const val TRIPS = "/trips"
        const val TECHNICAL = "/technical"
        const val USERS = "/users"

    }

    @POST(API + V1 + TECHNICAL + "/wakeup")
    fun checkStatus(): Call<Void?>?

    @POST(API + V1 + TRIPS)
    fun registerTrip(@Body tripDTO: TripDTO?): Call<Void?>?

    @POST(API + V1 + USERS + "/auth")
    fun signIn(@Body tripDTO: SignInPayload?): Call<Void?>?


}