package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.shoort

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class ShortMeal(
    @field:Json(name = "idMeal")
    var idMeal: Long,
    @field:Json(name = "strmeal")
    var strMeal: String,
    @field:Json(name = "strMealThumb")
    var strMealThumb: String
)
