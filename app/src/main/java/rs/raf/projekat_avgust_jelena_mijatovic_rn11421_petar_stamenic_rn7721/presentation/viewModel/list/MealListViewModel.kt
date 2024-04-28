package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.list

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import retrofit2.HttpException
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.api.CaloriesAPI
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.api.FreeMealAPI
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MyMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.shoort.ShortMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseShortMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.list.MealListFragment
import java.io.IOException

class MealListViewModel(
    var mealApi:FreeMealAPI,
    var caloriesAPI: CaloriesAPI
):ViewModel() {
    lateinit var fragment: MealListFragment

    fun loadData(){
        Repository.getInstance().meals.clear()
        fragment.lifecycleScope.launchWhenCreated {
            var progressBar = fragment.activity?.findViewById<ProgressBar>(R.id.progress_bar)
            progressBar?.isVisible = true
            val response = try {
                if (Repository.getInstance().category != null) {
                    mealApi.getShortMealsByCategoryName(Repository.getInstance().category!!)
                } else if(Repository.getInstance().area !=null){
                    mealApi.getShortMealsByAreaName(Repository.getInstance().area!!)
                }else if(Repository.getInstance().ingredient !=null){
                    mealApi.getShortMealsByIngredientName(Repository.getInstance().ingredient!!)
                }else if(Repository.getInstance().search !=null){
                    mealApi.getFullMealsByNamePart(Repository.getInstance().search!!)
                }else{
                    mealApi.getFullMealsByFirstLetter("a")
                }
            } catch (e: IOException) {
                println("IOException")
                e.printStackTrace()
                progressBar?.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                println("HTTPEXception")
                e.printStackTrace()
                progressBar?.isVisible = false
                return@launchWhenCreated
            }
            var shortMeals :MutableList<ShortMeal> = mutableListOf()
            if (response.isSuccessful && response.body() != null) {
                if(response.body()!! is ResponseShortMeal) {
                    var responseShortMeal = response.body()!! as ResponseShortMeal
                    shortMeals = responseShortMeal.meals.toMutableList()
                    shortMeals.forEach{
                        fragment.lifecycleScope.launchWhenCreated {
                            progressBar?.isVisible = true
                            var responseLong = try{
                                mealApi.getFullMealByMealId(it.idMeal)
                            } catch (e: IOException) {
                                println("IOException")
                                e.printStackTrace()
                                progressBar?.isVisible = false
                                return@launchWhenCreated
                            } catch (e: HttpException) {
                                println("HTTPEXception")
                                e.printStackTrace()
                                progressBar?.isVisible = false
                                return@launchWhenCreated
                            }
                            if(responseLong.isSuccessful && responseLong.body()!= null && responseLong.body()!!.meals.isNotEmpty()){
                                var myMeal = MyMeal(responseLong.body()!!.meals.get(0))
                                fragment.lifecycleScope.launchWhenCreated {
                                    progressBar?.isVisible = true
                                    var kcalresponse = try{
                                        caloriesAPI.getCaloriesForIngredient100g(myMeal.ingredients.joinToString(" and "))
                                    }  catch (e: IOException) {
                                        println("IOException")
                                        e.printStackTrace()
                                        progressBar?.isVisible = false
                                        return@launchWhenCreated
                                    } catch (e: HttpException) {
                                        println("HTTPEXception")
                                        e.printStackTrace()
                                        progressBar?.isVisible = false
                                        return@launchWhenCreated
                                    }
                                    if(kcalresponse.isSuccessful && kcalresponse.body() != null){
                                        var mealWithCalories = MealWithCalories(myMeal, kcalresponse.body()!!)
                                        if(Repository.getInstance().filterKcalMin<=mealWithCalories.calculateCaloriesByMesurements() && mealWithCalories.calculateCaloriesByMesurements()<Repository.getInstance().filterKcalMax) {
                                            Repository.getInstance().meals.add(mealWithCalories)
                                            Repository.getInstance().mealList.value =
                                                Repository.getInstance().meals
                                        }
                                        /*
                                        if(Repository.getInstance().mealList.value == null){
                                            Repository.getInstance().mealList.value = mutableListOf(mealWithCalories)
                                        }else{
                                            Repository.getInstance().mealList.value = Repository.getInstance().mealList.value
                                        }

                                         */
                                    }

                                    progressBar?.isVisible = false
                                }
                            }
                        }
                    }



                }
                if(response.body()!! is ResponseMeal){
                    var responseMeals = response.body()!! as ResponseMeal
                    responseMeals.meals.forEach{
                        var myMeal = MyMeal(it)
                        fragment.lifecycleScope.launchWhenCreated {
                            progressBar?.isVisible = true
                            var kcalresponse = try{
                                caloriesAPI.getCaloriesForIngredient100g(myMeal.ingredients.joinToString(" and "))
                            }  catch (e: IOException) {
                                println("IOException")
                                e.printStackTrace()
                                progressBar?.isVisible = false
                                return@launchWhenCreated
                            } catch (e: HttpException) {
                                println("HTTPEXception")
                                e.printStackTrace()
                                progressBar?.isVisible = false
                                return@launchWhenCreated
                            }
                            if(kcalresponse.isSuccessful && kcalresponse.body() != null){
                                var mealWithCalories = MealWithCalories(myMeal, kcalresponse.body()!!)

                                Repository.getInstance().meals.add(mealWithCalories)
                                Repository.getInstance().mealList.value = Repository.getInstance().meals
                                /*
                                if(Repository.getInstance().mealList.value == null){
                                    Repository.getInstance().mealList.value = mutableListOf(mealWithCalories)
                                }else{
                                    Repository.getInstance().mealList.value?.add(mealWithCalories)
                                }

                                 */
                            }

                            progressBar?.isVisible = false
                        }
                    }
                }
            } else {
                println("Bad format")
            }
            progressBar?.isVisible = false

        }

        }
}