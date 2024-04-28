package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal

import java.time.LocalDate

class PlanItem(
    var uid:Int,
    var mealWithCalories : MealWithCalories,
    var date: LocalDate,
    var mealType: String
)