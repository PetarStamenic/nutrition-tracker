package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.Ingredient
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.UniqueCaloriesForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite.Favorites
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.PlanItem
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.shoort.ShortArea
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.shoort.ShortCategory

class Repository private constructor(){
    companion object{
        @Volatile
        private var instance: Repository? = null
        fun getInstance(): Repository {
            if(instance == null){
                synchronized(this){
                    if(instance == null){
                        instance = Repository()
                    }
                }
            }
            return instance!!
        }
    }
    val planList: MutableLiveData<MutableList<PlanItem>> by lazy {
        MutableLiveData<MutableList<PlanItem>>()
    }
    val plan: MutableList<PlanItem> = mutableListOf()


    val favoritesList: MutableLiveData<MutableList<Favorites>> by lazy {
        MutableLiveData<MutableList<Favorites>>()
    }
    val favorites: MutableList<Favorites> = mutableListOf()

    val historyList: MutableLiveData<MutableList<PlanItem>> by lazy {
        MutableLiveData<MutableList<PlanItem>>()
    }
    val history: MutableList<PlanItem> = mutableListOf()

    val mealList: MutableLiveData<MutableList<MealWithCalories>> by lazy {
        MutableLiveData<MutableList<MealWithCalories>>()
    }
    val meals: MutableList<MealWithCalories> = mutableListOf()


    val currMeal: MutableLiveData<MealWithCalories> by lazy {
        MutableLiveData<MealWithCalories>()
    }
    var mealWithCalories : MealWithCalories? = null


    val shortCategoriesList: MutableLiveData<MutableList<ShortCategory>> by lazy {
        MutableLiveData<MutableList<ShortCategory>>()
    }
    var shortCategories: MutableList<ShortCategory> = mutableListOf()


    val shortAreaList: MutableLiveData<MutableList<ShortArea>> by lazy {
        MutableLiveData<MutableList<ShortArea>>()
    }
    var shortAreas: MutableList<ShortArea> = mutableListOf()



    val statisticsList: MutableLiveData<MutableList<PlanItem>> by lazy {
        MutableLiveData<MutableList<PlanItem>>()
    }
    val statistics: MutableList<PlanItem> = mutableListOf()

    val ingredientList: MutableLiveData<MutableList<Ingredient>> by lazy {
        MutableLiveData<MutableList<Ingredient>>()
    }
    var ingredients: MutableList<Ingredient> = mutableListOf()

    var customCalories: MutableList<UniqueCaloriesForDatabase> = mutableListOf()

    var filter: Int = 0
    var area: String? = null
    var ingredient: String? = null
    var search: String? = null
    var category: String? = null
    var isPlan: Boolean = true
    var curMealImage: Bitmap? = null
    var filterSearch: String? = null
    var filterKcalMin = 0
    var filterKcalMax = Int.MAX_VALUE
    var sortBy = 0
    var orederBy = 0


    var filterFavorites: Int = 0
    var areaFavorites: String? = null
    var ingredientFavorites: String? = null
    var searchFavorites: String? = null
    var categoryFavorites: String? = null
    var favoritesSpinner = 0



}