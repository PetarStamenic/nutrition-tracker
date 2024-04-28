package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite

import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories

class FavoriteMeal(
    var numberOfMeals : Int = 1,
    var mealWithCalories: MealWithCalories,
    var percentage: Double
): Favorites {
    override fun getPercentage(): String? {
        return ((percentage*10000).toInt()/100.0).toString()
    }

    override fun getTitle(): String? {
        return mealWithCalories.meal.name
    }

    override fun getImage(): String? {
        return mealWithCalories.meal.thumbnail
    }

    override fun getTimes(): String? {
        return numberOfMeals.toString()
    }

    override fun getCalories(): String? {
        return ((mealWithCalories.calculateCaloriesByMesurements()*100).toInt()/100.0).toString()
    }

}