package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseShortMeal

interface CaloriesAPI {

    @Headers("X-Api-Key: nEdGyyXRvKhCvZ8gLNYwRw==gXpQWzANNOLRuqWS")
    @GET("v1/nutrition?query")
    suspend fun getCaloriesForIngredient100g(@Query("query")query:String): Response<ResponseCalories>


}