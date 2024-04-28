package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model

import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.Meal

data class Day(
    var calories: Int =0,
    var meals :MutableList<Meal> = listOf<Meal>().toMutableList(),
    var date: Long = 0
    ){
    fun addMeal(meal: Meal){
        meals.add(meal)
    }
}
