package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import retrofit2.HttpException
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.api.FreeMealAPI
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseIngredient
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseShortArea
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseShortCategory
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.FavoritesFilterFragment
import java.io.IOException

class FavoriteFilterViewModel(
    val mealAPI: FreeMealAPI
):ViewModel() {
    lateinit var fragment: FavoritesFilterFragment

    fun loadData() {
        fragment.lifecycleScope.launchWhenCreated {
            var progressBar = fragment.activity?.findViewById<ProgressBar>(R.id.progress_bar)
            progressBar?.isVisible = true

            val response = try {
                when (Repository.getInstance().filter) {
                    1 -> {
                        mealAPI.getShortAreas()
                    }

                    2 -> {
                        mealAPI.getAllIngredients()
                    }

                    else -> {
                        mealAPI.getShortCategories()
                    }
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
            if (response.isSuccessful && response.body() != null) {
                if (response.body()!! is ResponseShortCategory) {
                    var responseShortCategory = response.body()!! as ResponseShortCategory
                    Repository.getInstance().shortCategories = responseShortCategory.meals.toMutableList()
                    Repository.getInstance().shortCategoriesList.value = Repository.getInstance().shortCategories
                }
                if (response.body()!! is ResponseShortArea) {
                    var responseShortArea = response.body()!! as ResponseShortArea
                    Repository.getInstance().shortAreas = responseShortArea.meals.toMutableList()
                    Repository.getInstance().shortAreaList.value = Repository.getInstance().shortAreas
                }
                if (response.body()!! is ResponseIngredient) {
                    var responseIngredient = response.body()!! as ResponseIngredient
                    Repository.getInstance().ingredients = responseIngredient.meals.toMutableList()
                    Repository.getInstance().ingredientList.value = Repository.getInstance().ingredients
                }
            } else {
                println("BAD FORMAT")
            }
            progressBar?.isVisible = false
        }
    }
        fun filter(string: String?){
            if(string == null) {
                when (Repository.getInstance().filter) {
                    0 -> fragment.filterAdapter.filterList(arrayListOf(fragment.filterAdapter.originalCategories))
                    1 -> fragment.filterAdapter.filterList(arrayListOf(fragment.filterAdapter.originalAreas))
                    2 -> fragment.filterAdapter.filterList(arrayListOf(fragment.filterAdapter.originalIngredients))
                }
                return
            }
            val filteredList: ArrayList<Any> = ArrayList()
            when(Repository.getInstance().filter){
                0->{
                    for(item in fragment.filterAdapter.originalCategories){
                        if(item.strCategory.lowercase().contains(string!!.lowercase()))
                            filteredList.add(item)
                    }
                }
                1->{
                    for(item in fragment.filterAdapter.originalAreas){
                        if(item.strArea.lowercase().contains(string!!.lowercase()))
                            filteredList.add(item)
                    }
                }
                2->{
                    for(item in fragment.filterAdapter.originalIngredients){
                        if(item.strIngredient.lowercase().contains(string!!.lowercase()))
                            filteredList.add(item)
                    }
                }
                else -> return
            }
            fragment.filterAdapter.filterList(filteredList)
        }


}