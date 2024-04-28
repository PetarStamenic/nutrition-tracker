package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.singleMeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import okio.IOException
import retrofit2.HttpException
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.api.CaloriesAPI
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.api.FreeMealAPI
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MyMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal.MealSingleFragment

class MealSingleViewModel(
    var mealApi: FreeMealAPI,
    var caloriesAPI: CaloriesAPI
): ViewModel() {
    lateinit var fragment: MealSingleFragment

    fun getMeal(){
        if(Repository.getInstance().mealWithCalories == null){
            fragment.lifecycleScope.launchWhenCreated {
                val response =  try {
                    mealApi.getFullMealAtRandom()
                } catch (e:IOException) {
                    return@launchWhenCreated
                } catch (e:HttpException){
                    return@launchWhenCreated
                }
                if(response.isSuccessful && response.body() != null){
                    var myMeal = MyMeal(response.body()!!.meals[0])
                    fragment.lifecycleScope.launchWhenCreated {
                        var kcalResponse = try {
                            caloriesAPI.getCaloriesForIngredient100g(myMeal.ingredients.joinToString(" and "))
                        } catch (e:IOException) {
                            return@launchWhenCreated
                        } catch (e:HttpException){
                            return@launchWhenCreated
                        }
                        if(kcalResponse.isSuccessful && kcalResponse.body() != null){
                            var  mealWithCalories = MealWithCalories(myMeal,kcalResponse.body()!!)
                            Repository.getInstance().mealWithCalories = mealWithCalories
                            Repository.getInstance().currMeal.value = Repository.getInstance().mealWithCalories!!
                        }
                    }
                }
            }
        } else {
            Repository.getInstance().currMeal.value = Repository.getInstance().mealWithCalories!!
        }
    }
}