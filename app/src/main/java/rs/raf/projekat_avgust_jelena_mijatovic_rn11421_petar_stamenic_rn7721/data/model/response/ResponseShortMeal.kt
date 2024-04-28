package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.shoort.ShortMeal

@JsonClass(generateAdapter = true)
data class ResponseShortMeal(
    @field:Json(name = "meals")
    var meals: List<ShortMeal>
)
