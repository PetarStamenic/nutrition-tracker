package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.AppDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite.FavoriteArea
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite.FavoriteCategory
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite.FavoriteMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite.Favorites
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.FavoritesFragment

class FavoritesViewModel : ViewModel() {

    lateinit var fragment: FavoritesFragment
    var data: MutableList<Favorites> = mutableListOf()

    fun loadData(){
        Repository.getInstance().favorites.clear()
        ReadFromDatabase().execute()

    }

    private inner class ReadFromDatabase(): AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val planItemDao = AppDatabase.getInstance().planItemDao()
            val temp = planItemDao.getAll()
            var old : FavoriteMeal?
            var new : FavoriteMeal?
            var oldCat : FavoriteCategory?
            var newCat : FavoriteCategory?
            var oldArea : FavoriteArea?
            var newArea : FavoriteArea?
            var area = Repository.getInstance().areaFavorites
            var category = Repository.getInstance().categoryFavorites
            var ingredient = Repository.getInstance().ingredientFavorites
            data.clear()
            when(Repository.getInstance().favoritesSpinner){
                0->{
                    if(area != null){
                        temp.forEach { dao ->
                            run {
                                if(dao.toPlanItem().mealWithCalories.meal.area==area) {
                                    println(dao.mealId)
                                    old = null
                                    new = null
                                    var oldTitle = ""
                                    var oldelemNum = 0
                                    data.forEach {
                                        if(it is FavoriteMeal) {
                                            if (it.mealWithCalories.meal.id == dao.mealId) {
                                                old = it
                                                oldTitle = it.getTitle()!!
                                                var num = it.numberOfMeals+1
                                                new = FavoriteMeal(num, old!!.mealWithCalories,(1.0*(num))/temp.size)
                                                println("incresing")
                                            }
                                        }
                                    }
                                    if (old == null) {
                                        data.add(FavoriteMeal(1, dao.toPlanItem().mealWithCalories,1.0/temp.size))
                                        println("newitem")
                                    } else {
                                        data.forEachIndexed{index,elem->
                                            run {
                                                if(elem.getTitle() == oldTitle)
                                                    oldelemNum = index
                                            }

                                        }
                                        data.removeAt(oldelemNum)
                                        data.add(new!!)
                                    }
                                }
                            }
                        }
                    } else if(category != null){
                        temp.forEach { dao ->
                            run {
                                if(dao.toPlanItem().mealWithCalories.meal.category == category) {
                                    println(dao.mealId)
                                    old = null
                                    new = null
                                    var oldTitle = ""
                                    var oldelemNum = 0
                                    data.forEach {
                                        if(it is FavoriteMeal) {
                                            if (it.mealWithCalories.meal.id == dao.mealId) {
                                                old = it
                                                oldTitle = it.getTitle()!!
                                                var num = it.numberOfMeals+1
                                                new = FavoriteMeal(num, old!!.mealWithCalories,(1.0*(num))/temp.size)

                                                println("incresing")
                                            }
                                        }
                                    }
                                    if (old == null) {
                                        data.add(FavoriteMeal(1, dao.toPlanItem().mealWithCalories,1.0/temp.size))
                                        println("newitem")
                                    } else {
                                        data.forEachIndexed{index,elem->
                                            run {
                                                if(elem.getTitle() == oldTitle)
                                                    oldelemNum = index
                                            }

                                        }
                                        data.removeAt(oldelemNum)
                                        data.add(new!!)
                                    }
                                }
                            }
                        }

                    } else if(ingredient != null){
                        temp.forEach { dao ->
                            run {
                                if(dao.toPlanItem().mealWithCalories.meal.ingredients.contains(ingredient)) {
                                    println(dao.mealId)
                                    old = null
                                    new = null
                                    var oldTitle = ""
                                    var oldelemNum = 0
                                    data.forEach {
                                        if(it is FavoriteMeal) {
                                            if (it.mealWithCalories.meal.id == dao.mealId) {
                                                old = it
                                                oldTitle = it.getTitle()!!
                                                var num = it.numberOfMeals+1
                                                new = FavoriteMeal(num, old!!.mealWithCalories,(1.0*(num))/temp.size)

                                                println("incresing")
                                            }
                                        }
                                    }
                                    if (old == null) {
                                        data.add(FavoriteMeal(1, dao.toPlanItem().mealWithCalories,1.0/temp.size))
                                        println("newitem")
                                    } else {

                                        data.forEachIndexed{index,elem->
                                            run {
                                                if(elem.getTitle() == oldTitle)
                                                    oldelemNum = index
                                            }

                                        }
                                        data.removeAt(oldelemNum)
                                        data.add(new!!)
                                    }
                                }
                            }
                        }
                    }else {
                        temp.forEach { dao ->
                            run {
                                println(dao.mealId)
                                old = null
                                new = null
                                var oldTitle = ""
                                var oldelemNum = 0
                                data.forEach {

                                    if(it is FavoriteMeal) {
                                        if (it.mealWithCalories.meal.id == dao.mealId) {
                                            old = it
                                            oldTitle = it.getTitle()!!
                                            var num = it.numberOfMeals+1
                                            new = FavoriteMeal(num, old!!.mealWithCalories,(1.0*(num))/temp.size)

                                            println("incresing")
                                        }
                                    }
                                }
                                if (old == null) {
                                    data.add(FavoriteMeal(1, dao.toPlanItem().mealWithCalories,1.0/temp.size))
                                    println("newitem")
                                } else {
                                    data.forEachIndexed{index,elem->
                                        run {
                                            if(elem.getTitle() == oldTitle)
                                                oldelemNum = index
                                        }

                                    }
                                    data.removeAt(oldelemNum)
                                    data.add(new!!)
                                }
                            }
                        }
                    }
                }
                1->{
                    temp.forEach { dao ->
                        run {
                                println(dao.mealId)
                                oldCat = null
                                newCat = null
                                var oldTitle = ""
                                var oldelemNum = 0
                                data.forEach {
                                    if(it is FavoriteCategory) {
                                        if (it.getTitle() == dao.toPlanItem().mealWithCalories.meal.category) {
                                            oldCat = it
                                            oldTitle = it.getTitle()!!
                                            var num = it.numberOfMeals+1
                                            newCat = FavoriteCategory(num, oldCat!!.getTitle()!!,(1.0*(num))/temp.size)
                                            println("incresing")
                                        }
                                    }
                                }
                                if (oldCat == null) {
                                    data.add(FavoriteCategory(1, dao.toPlanItem().mealWithCalories.meal.category,1.0/temp.size))
                                    println("newitem")
                                } else {
                                    data.forEachIndexed{index,elem->
                                        run {
                                            if(elem.getTitle() == oldTitle)
                                                oldelemNum = index
                                        }

                                    }
                                    data.removeAt(oldelemNum)
                                    data.add(newCat!!)
                                }

                        }
                    }
                }
                2->{
                    temp.forEach { dao ->
                        run {
                                println(dao.mealId)
                                oldArea = null
                                newArea = null
                                var oldTitle = ""
                                var oldelemNum = 0
                                data.forEach {
                                    if(it is FavoriteArea) {
                                        if (it.getTitle() == dao.toPlanItem().mealWithCalories.meal.area) {
                                            oldArea = it
                                            oldTitle = it.getTitle()!!
                                            var num = it.numberOfMeals+1
                                            newArea = FavoriteArea(num, oldTitle,(1.0*(num))/temp.size)
                                            println("incresing")
                                        }
                                    }
                                }
                                if (oldArea == null) {
                                    data.add(FavoriteArea(1, dao.toPlanItem().mealWithCalories.meal.area,1.0/temp.size))
                                    println("newitem")
                                } else {
                                    data.forEachIndexed{index,elem->
                                        run {
                                            if(elem.getTitle() == oldTitle)
                                                oldelemNum = index
                                        }

                                    }
                                    data.removeAt(oldelemNum)
                                    data.add(newArea!!)
                                }

                        }
                    }
                }
                3->{
                    temp.forEach { dao ->
                        run {
                                println(dao.mealId)
                                old = null
                                new = null
                                var oldTitle = ""
                                var oldelemNum = 0
                                data.forEach {
                                    if(it is FavoriteMeal) {
                                        if (it.getTitle() == dao.toPlanItem().mealWithCalories.meal.name) {
                                            old = it
                                            oldTitle = it.getTitle()!!
                                            var num = it.numberOfMeals+1
                                            new = FavoriteMeal(num, old!!.mealWithCalories,(1.0*(num))/temp.size)
                                            println("incresing")
                                        }
                                    }
                                }
                                if (old == null) {
                                    data.add(FavoriteMeal(1, dao.toPlanItem().mealWithCalories,1.0/temp.size))
                                    println("newitem")
                                } else {
                                    data.forEachIndexed{index,elem->
                                        run {
                                            if(elem.getTitle() == oldTitle)
                                                oldelemNum = index
                                        }

                                    }
                                    data.removeAt(oldelemNum)
                                    data.add(new!!)
                                }

                        }
                    }
                }
            }

            println(Repository.getInstance().favoritesSpinner)
            data.forEach{
                println(it)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            data?.forEach {
                Repository.getInstance().favorites.add(it)
                Repository.getInstance().favoritesList.value = Repository.getInstance().favorites
            }
        }
    }
}