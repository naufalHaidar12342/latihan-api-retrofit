package xyz.heydarrn.latihanretrofit

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET ("detail/{id}")
    fun getRestaurant(@Path("id") id: String) : retrofit2.Call<RestaurantResponse>
}