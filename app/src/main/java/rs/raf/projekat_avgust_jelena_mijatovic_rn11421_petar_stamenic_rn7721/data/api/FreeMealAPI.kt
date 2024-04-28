package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.*

interface FreeMealAPI {
    @GET("api/json/v1/1/filter.php?")
    suspend fun getShortMealsByCategoryName(@Query("c")c:String):Response<ResponseShortMeal>
    @GET("api/json/v1/1/filter.php?")
    suspend fun getShortMealsByAreaName(@Query("a")a:String):Response<ResponseShortMeal>
    @GET("api/json/v1/1/filter.php?")
    suspend fun getShortMealsByIngredientName(@Query("i")i:String):Response<ResponseShortMeal>
    @GET("api/json/v1/1/search.php?")
    suspend fun getFullMealsByNamePart(@Query("s")s:String):Response<ResponseMeal>
    @GET("api/json/v1/1/search.php?")
    suspend fun getFullMealsByFirstLetter(@Query("f")f:String):Response<ResponseMeal>
    @GET("api/json/v1/1/lookup.php?")
    suspend fun getFullMealByMealId(@Query("i")i:Long):Response<ResponseMeal>
    @GET("api/json/v1/1/random.php")
    suspend fun getFullMealAtRandom():Response<ResponseMeal>
    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getShortCategories():Response<ResponseShortCategory>
    @GET("api/json/v1/1/list.php?a=list")
    suspend fun getShortAreas():Response<ResponseShortArea>
    @GET("api/json/v1/1/list.php?i=list")
    suspend fun getAllIngredients():Response<ResponseIngredient>

}