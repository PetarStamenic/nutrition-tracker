package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal

import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.response.ResponseCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import java.lang.Integer.max
import java.util.Locale

data class MealWithCalories(
    var meal: MyMeal,
    var calories: ResponseCalories
){
    fun calculateCaloriesByMesurements() : Double{
        var kcal = 0.0
        meal.ingredients.forEachIndexed{ i: Int, s: String ->
            run {
                var cal = calculateCaloriesPerIngredient(i)
                if(cal != -1.0)
                    kcal += cal
            }
        }
        return kcal
    }

    fun calculateCaloriesPerIngredient(ingredient: Int): Double {
        if(Repository.getInstance().customCalories.isNotEmpty())
            Repository.getInstance().customCalories.forEach{
                if(it.mealId == meal.id)
                    if(it.ingredientIndex == ingredient)
                        return it.calories
            }
        if (meal.ingredients.size < ingredient || ingredient < 0)
            return 0.0
        var mesure = 0
        val mesurement = meal.mesurements.get(ingredient)
        mesurement.replace("sliced","")
        mesurement.replace("mashed","")
        mesurement.replace("minced","")

        try {
            if(mesurement.lowercase().contains("pound") || mesurement.lowercase().endsWith("lbs")|| mesurement.lowercase().endsWith("lb"))
            {
                if(mesurement.contains("-") || mesurement.contains(".")|| mesurement.contains(",")|| mesurement.contains("/"))
                {
                    var max = 0
                    mesurement.forEach {
                        if(it.isDigit()){
                            var num : String
                            num = it.toString()
                            max = max(max,num.toInt())
                        }
                    }
                    mesure = max*454
                }else{
                    var num = mesurement.filter { it.isDigit() }
                    mesure = num.toInt()*454
                }
            }else if (mesurement.lowercase(Locale.getDefault()).contains("grams")) {
                val mes: Array<String> =
                    mesurement.replace("(", " ").replace(")", " ").split(" ".toRegex())
                        .dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                for (i in mes.indices) {
                    if (mes[i].lowercase(Locale.getDefault()).contains("grams")) {
                        if (i - 1 >= 0) {
                            val s = mes[i - 1]
                            mesure = s.toInt()
                        } else if (i + 1 < mes.size) {
                            mesure = mes[i + 1].toInt()
                        }
                    }
                }
            } else if (mesurement.lowercase(Locale.getDefault()).endsWith("kg")) {
                mesure = (1000 * mesurement.split("kg".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray().get(0).trim { it <= ' ' }
                    .toDouble()).toInt()
            } else if (mesurement.lowercase(Locale.getDefault()).endsWith("g") && !(mesurement.lowercase().contains("sprig"))) {
                mesure =
                    mesurement.split("g".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        .get(0).trim { it <= ' ' }
                        .toInt()
            } else if (mesurement.lowercase(Locale.getDefault()).endsWith("ml")) {
                mesure =
                    mesurement.split("ml".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        .get(0).trim { it <= ' ' }
                        .toInt()
            } else if (mesurement.lowercase(Locale.getDefault()).endsWith("dl")) {
                mesure = 10 * mesurement.split("dl".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray().get(0).trim { it <= ' ' }
                    .toInt()
            } else if (mesurement.lowercase(Locale.getDefault()).endsWith("cl")) {
                mesure = 100 * mesurement.split("cl".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray().get(0).trim { it <= ' ' }
                    .toInt()
            } else if (mesurement.lowercase(Locale.getDefault()).endsWith("l")) {
                mesure = 1000 * mesurement.split("l".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray().get(0).trim { it <= ' ' }
                    .toInt()
            } else if (mesurement.lowercase(Locale.getDefault())
                    .contains("tbsp") || mesurement.lowercase(Locale.getDefault())
                    .contains("tbls") || mesurement.lowercase(Locale.getDefault())
                    .contains("tbs") || mesurement.lowercase(Locale.getDefault())
                    .contains("tblsp") || mesurement.lowercase(
                    Locale.getDefault()
                ).contains("tablespoon")
            ) {
                val tbsp = 15
                mesurement.replace("\\", "")
                if (mesurement.contains("1/2")) {
                    mesure = 7
                    if (mesurement.contains("1 1/2")) {
                        mesure = 22
                    }
                    if (mesurement.contains("2 1/2")) {
                        mesure = 37
                    }
                    if (mesurement.contains("3")) {
                        mesure = mesure + 3 * tbsp
                    }
                    if (mesurement.contains("4")) {
                        mesure = mesure + 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = mesure + 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = mesure + 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = mesure + 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = mesure + 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = mesure + 9 * tbsp
                    }
                } else {
                    if (mesurement.contains("1")) {
                        mesure = 1 * tbsp
                    }
                    if (mesurement.contains("2")) {
                        mesure = 2 * tbsp
                    }
                    if (mesurement.contains("3")) {
                        mesure = 3 * tbsp
                    }
                    if (mesurement.contains("4")) {
                        mesure = 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = 9 * tbsp
                    }
                }
            } else if (mesurement.lowercase(Locale.getDefault())
                    .contains("tsp") || mesurement.lowercase(Locale.getDefault())
                    .contains("teaspoon")
            ) {
                val tbsp = 6
                mesurement.replace("\\", "")
                if (mesurement.contains("1/2".toString())) {
                    mesure = 3
                    if (mesurement.contains("1 1/2".toString())) {
                        mesure = 3 + 1 * tbsp
                    }
                    if (mesurement.contains("2 1/2".toString())) {
                        mesure = 3 + 2 * tbsp
                    }
                    if (mesurement.contains("3")) {
                        mesure = mesure + 3 * tbsp
                    }
                    if (mesurement.contains("4")) {
                        mesure = mesure + 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = mesure + 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = mesure + 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = mesure + 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = mesure + 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = mesure + 9 * tbsp
                    }
                } else {
                    if (mesurement.contains("1")) {
                        mesure = 1 * tbsp
                    }
                    if (mesurement.contains("2")) {
                        mesure = 2 * tbsp
                    }
                    if (mesurement.contains("3")) {
                        mesure = 3 * tbsp
                    }
                    if (mesurement.contains("4")) {
                        mesure = 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = 9 * tbsp
                    }
                }
            } else if (mesurement.lowercase(Locale.getDefault()).contains("cup")) {
                val tbsp = 240
                mesurement.replace("\\", "")
                if (mesurement.contains("1/2".toString())) {
                    mesure = 120
                    if (mesurement.contains("1 1/2".toString())) {
                        mesure = 120 + 1 * tbsp
                    }
                    if (mesurement.contains("2 1/2".toString())) {
                        mesure = 120 + 2 * tbsp
                    }
                    if (mesurement.contains("3")) {
                        mesure = mesure + 3 * tbsp
                    }
                    if (mesurement.contains("4")) {
                        mesure = mesure + 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = mesure + 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = mesure + 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = mesure + 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = mesure + 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = mesure + 9 * tbsp
                    }
                } else if (mesurement.contains("1/3".toString())) {
                    mesure = 80
                    if (mesurement.contains("1 1/3".toString())) {
                        mesure = 80 + 1 * tbsp
                    }
                    if (mesurement.contains("2 1/3".toString())) {
                        mesure = 80 + 2 * tbsp
                    }
                    if (mesurement.contains("3 1/3")) {
                        mesure = mesure + 3 * tbsp
                    }
                    if (mesurement.contains("4")) {
                        mesure = mesure + 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = mesure + 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = mesure + 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = mesure + 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = mesure + 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = mesure + 9 * tbsp
                    }
                } else if (mesurement.contains("1/4".toString())) {
                    mesure = 60
                    if (mesurement.contains("1 1/4".toString())) {
                        mesure = 60 + 1 * tbsp
                    }
                    if (mesurement.contains("2 1/4".toString())) {
                        mesure = 60 + 2 * tbsp
                    }
                    if (mesurement.contains("3")) {
                        mesure = mesure + 3 * tbsp
                    }
                    if (mesurement.contains("4 1/4")) {
                        mesure = mesure + 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = mesure + 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = mesure + 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = mesure + 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = mesure + 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = mesure + 9 * tbsp
                    }
                } else {
                    if (mesurement.contains("1")) {
                        mesure = 1 * tbsp
                    }
                    if (mesurement.contains("2")) {
                        mesure = 2 * tbsp
                    }
                    if (mesurement.contains("3")) {
                        mesure = 3 * tbsp
                    }
                    if (mesurement.contains("4")) {
                        mesure = 4 * tbsp
                    }
                    if (mesurement.contains("5")) {
                        mesure = 5 * tbsp
                    }
                    if (mesurement.contains("6")) {
                        mesure = 6 * tbsp
                    }
                    if (mesurement.contains("7")) {
                        mesure = 7 * tbsp
                    }
                    if (mesurement.contains("8")) {
                        mesure = 8 * tbsp
                    }
                    if (mesurement.contains("9")) {
                        mesure = 9 * tbsp
                    }
                }
            } else if (mesurement.lowercase(Locale.getDefault()).contains("3rd")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 3
            } else if (mesurement.lowercase(Locale.getDefault()).contains("4th")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 4
            } else if (mesurement.lowercase(Locale.getDefault()).contains("5th")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 5
            } else if (mesurement.lowercase(Locale.getDefault()).contains("6th")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 6
            } else if (mesurement.lowercase(Locale.getDefault()).contains("7th")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 7
            } else if (mesurement.lowercase(Locale.getDefault()).contains("8th")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 9
            } else if (mesurement.lowercase(Locale.getDefault()).contains("9th")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 9
            } else if (mesurement.lowercase(Locale.getDefault()).contains("10th")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 10
            } else if (mesurement.contains("half")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) / 2
            } else if (mesurement.contains("few")) {
                mesure =
                    merica(meal.ingredients.get(ingredient)) * 5
            } else if (meal.ingredients.get(ingredient).contains("egg")) {
                if (mesurement.contains("extra")) {
                    mesure = 65 * mesurement.split("extra".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray().get(0).trim { it <= ' ' }
                        .toInt()
                } else if (mesurement.contains("large")) {
                    mesure = 58 * mesurement.split("large".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray().get(0).trim { it <= ' ' }
                        .toInt()
                } else if (mesurement.contains("medium")) {
                    mesure =
                        50 * mesurement.split("medium".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray().get(0).trim { it <= ' ' }
                            .toInt()
                } else if (mesurement.contains("small")) {
                    mesure = 45 * mesurement.split("ml".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray().get(0).trim { it <= ' ' }
                        .toInt()
                } else {
                    mesure = 50 * mesurement.trim { it <= ' ' }.toInt()
                }
            } else if (mesurement.contains("1")) {
                mesure = 1 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("2")) {
                mesure = 2 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("3")) {
                mesure = 3 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("4")) {
                mesure = 4 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("5")) {
                mesure = 5 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("6")) {
                mesure = 6 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("7")) {
                mesure = 7 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("8")) {
                mesure = 8 * merica(meal.ingredients.get(ingredient))
            } else if (mesurement.contains("9")) {
                mesure = 9 * merica(meal.ingredients.get(ingredient))
            }

        }catch (e:Exception){
            return -1.0
        }
        calories.forEach {
            if (meal.ingredients.get(ingredient).lowercase().contains(it.name.lowercase())) {
                var calori = it.calories
                if (mesure == 0)
                    mesure = it.serving_size_g
                return it.calories * mesure / 100.0
            }
        }
        return -1.0
    }
        private fun merica(name: String): Int {
            when (name.lowercase(Locale.getDefault())) {
                "apple" -> return 195
                "apricot" -> return 35
                "avocado" -> return 170
                "banana" -> return 120
                "blackberry" -> return 3
                "blueberry" -> return 1
                "cherries" -> return 5
                "coconut" -> return 680
                "cranberry" -> return 1
                "fig" -> return 50
                "grapefruit" -> return 246
                "grapes" -> return 5
                "guava" -> return 200
                "jackfruit" -> return 6800
                "kiwi" -> return 75
                "lemon" -> return 100
                "lime" -> return 50
                "mango" -> return 200
                "melon" -> return 1500
                "nectarine" -> return 150
                "olives" -> return 5
                "orange" -> return 130
                "papaya" -> return 450
                "peach" -> return 150
                "pear" -> return 180
                "pineapple" -> return 1590
                "plum" -> return 65
                "pomegranate" -> return 255
                "pumpkin" -> return 4500
                "raspberry" -> return 5
                "strawberry" -> return 12
                "watermelon" -> return 9000
                "artichoke" -> return 368
                "asparagus" -> return 22
                "garlic" -> return 5
                "greenbeans" -> return 5
                "beets" -> return 113
                "bellpepper" -> return 170
                "broccoli" -> return 225
                "brusselsprouts" -> return 14
                "cabbage" -> return 9070
                "carrots" -> return 60
                "cauliflower" -> return 500
                "celery" -> return 450
                "corn" -> return 180
                "cucumber" -> return 250
                "kale" -> return 198
                "lettuce" -> return 800
                "mushrooms" -> return 15
                "onion" -> return 160
                "parsnip" -> return 115
                "pea" -> return 1
                "potato" -> return 184
                "snowpea" -> return 2
                "spinach" -> return 30
                "squash" -> return 1100
                "sweetpotato" -> return 113
                "tomato" -> return 170
                "zucchini" -> return 200
            }
            return 0
        }
}
