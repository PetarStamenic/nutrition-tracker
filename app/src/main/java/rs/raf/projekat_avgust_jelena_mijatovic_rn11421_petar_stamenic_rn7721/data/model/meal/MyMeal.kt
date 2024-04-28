package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal

import java.util.*

data class MyMeal(
    var meal: Meal
){
    var id: Long = if(meal.idMeal != null) meal.idMeal!! else -1
    var name: String = if(meal.strMeal != null) meal.strMeal!! else "name not found"
    var drink: String = if(meal.strDrinkAlternate != null) meal.strDrinkAlternate!! else "drink not found"
    var category: String  = if(meal.strCategory != null) meal.strCategory!! else "category not found"
    var area: String = if(meal.strArea != null) meal.strArea!! else "area not found"
    var instructions: String = if(meal.strInstructions != null) meal.strInstructions!! else "instructions not clear"
    var thumbnail: String = if(meal.strMealThumb != null) meal.strMealThumb!! else "immage not found"
    var tags: MutableList<String> = if(meal.strTags != null) (meal.strTags!!).split(",").toMutableList() else listOf("Sorry no tags found").toMutableList()
    var strYoutube: String = if(meal.strYoutube != null) meal.strYoutube!! else "youtube link not found"
    var count: Int = if(meal.strIngredient1 == null || meal.strIngredient1!!.trim(){it <= ' '}.equals("")) 0 else
        if(meal.strIngredient2 == null || meal.strIngredient2!!.trim(){it <= ' '}.equals("")) 1 else
            if(meal.strIngredient3 == null || meal.strIngredient3!!.trim(){it <= ' '}.equals("")) 2 else
                if(meal.strIngredient4 == null || meal.strIngredient4!!.trim(){it <= ' '}.equals("")) 3 else
                    if(meal.strIngredient5 == null || meal.strIngredient5!!.trim(){it <= ' '}.equals("")) 4 else
                        if(meal.strIngredient6 == null || meal.strIngredient6!!.trim(){it <= ' '}.equals("")) 5 else
                            if(meal.strIngredient7 == null || meal.strIngredient7!!.trim(){it <= ' '}.equals("")) 6 else
                                if(meal.strIngredient8 == null || meal.strIngredient8!!.trim(){it <= ' '}.equals("")) 7 else
                                    if(meal.strIngredient9 == null || meal.strIngredient9!!.trim(){it <= ' '}.equals("")) 8 else
                                        if(meal.strIngredient10 == null || meal.strIngredient10!!.trim(){it <= ' '}.equals("")) 9 else
                                            if(meal.strIngredient11 == null || meal.strIngredient11!!.trim(){it <= ' '}.equals("")) 10 else
                                                if(meal.strIngredient12 == null || meal.strIngredient12!!.trim(){it <= ' '}.equals("")) 11 else
                                                    if(meal.strIngredient13 == null || meal.strIngredient13!!.trim(){it <= ' '}.equals("")) 12 else
                                                        if(meal.strIngredient14 == null || meal.strIngredient14!!.trim(){it <= ' '}.equals("")) 13 else
                                                            if(meal.strIngredient15 == null || meal.strIngredient15!!.trim(){it <= ' '}.equals("")) 14 else
                                                                if(meal.strIngredient16 == null || meal.strIngredient16!!.trim(){it <= ' '}.equals("")) 15 else
                                                                    if(meal.strIngredient17 == null || meal.strIngredient17!!.trim(){it <= ' '}.equals("")) 16 else
                                                                        if(meal.strIngredient18 == null || meal.strIngredient18!!.trim(){it <= ' '}.equals("")) 17 else
                                                                            if(meal.strIngredient19 == null || meal.strIngredient19!!.trim(){it <= ' '}.equals("")) 18 else 19
    var ingredients: MutableList<String> = mutableListOf( if (!(meal.strIngredient1 == null || meal.strIngredient1!!.trim { it <= ' ' }.equals(""))) meal.strIngredient1!! else "removeLater",
        if (!(meal.strIngredient2 == null || meal.strIngredient2!!.trim { it <= ' ' }.equals(""))) meal.strIngredient2!! else "removeLater",
        if (!(meal.strIngredient3 == null || meal.strIngredient3!!.trim { it <= ' ' }.equals(""))) meal.strIngredient3!! else "removeLater",
        if (!(meal.strIngredient4 == null || meal.strIngredient4!!.trim { it <= ' ' }.equals(""))) meal.strIngredient4!! else "removeLater",
        if (!(meal.strIngredient5 == null || meal.strIngredient5!!.trim { it <= ' ' }.equals(""))) meal.strIngredient5!! else "removeLater",
        if (!(meal.strIngredient6 == null || meal.strIngredient6!!.trim { it <= ' ' }.equals(""))) meal.strIngredient6!! else "removeLater",
        if (!(meal.strIngredient7 == null || meal.strIngredient7!!.trim { it <= ' ' }.equals(""))) meal.strIngredient7!! else "removeLater",
        if (!(meal.strIngredient8 == null || meal.strIngredient8!!.trim { it <= ' ' }.equals(""))) meal.strIngredient8!! else "removeLater",
        if (!(meal.strIngredient9 == null || meal.strIngredient9!!.trim { it <= ' ' }.equals(""))) meal.strIngredient9!! else "removeLater",
        if (!(meal.strIngredient10 == null || meal.strIngredient10!!.trim { it <= ' ' }.equals(""))) meal.strIngredient10!! else "removeLater",
        if (!(meal.strIngredient11 == null || meal.strIngredient11!!.trim { it <= ' ' }.equals(""))) meal.strIngredient11!! else "removeLater",
        if (!(meal.strIngredient12 == null || meal.strIngredient12!!.trim { it <= ' ' }.equals(""))) meal.strIngredient12!! else "removeLater",
        if (!(meal.strIngredient13 == null || meal.strIngredient13!!.trim { it <= ' ' }.equals(""))) meal.strIngredient13!! else "removeLater",
        if (!(meal.strIngredient14 == null || meal.strIngredient14!!.trim { it <= ' ' }.equals(""))) meal.strIngredient14!! else "removeLater",
        if (!(meal.strIngredient15 == null || meal.strIngredient15!!.trim { it <= ' ' }.equals(""))) meal.strIngredient15!! else "removeLater",
        if (!(meal.strIngredient16 == null || meal.strIngredient16!!.trim { it <= ' ' }.equals(""))) meal.strIngredient16!! else "removeLater",
        if (!(meal.strIngredient17 == null || meal.strIngredient17!!.trim { it <= ' ' }.equals(""))) meal.strIngredient17!! else "removeLater",
        if (!(meal.strIngredient18 == null || meal.strIngredient18!!.trim { it <= ' ' }.equals(""))) meal.strIngredient18!! else "removeLater",
        if (!(meal.strIngredient19 == null || meal.strIngredient19!!.trim { it <= ' ' }.equals(""))) meal.strIngredient19!! else "removeLater",
        if (!(meal.strIngredient20 == null || meal.strIngredient20!!.trim { it <= ' ' }.equals(""))) meal.strIngredient20!! else "removeLater").subList(0,count)
    var mesurements: MutableList<String> = mutableListOf( if (!(meal.strMeasure1 == null || meal.strMeasure1!!.trim { it <= ' ' }.equals(""))) meal.strMeasure1!! else "removeLater",
        if (!(meal.strMeasure2 == null || meal.strMeasure2!!.trim { it <= ' ' }.equals(""))) meal.strMeasure2!! else "removeLater",
        if (!(meal.strMeasure3 == null || meal.strMeasure3!!.trim { it <= ' ' }.equals(""))) meal.strMeasure3!! else "removeLater",
        if (!(meal.strMeasure4 == null || meal.strMeasure4!!.trim { it <= ' ' }.equals(""))) meal.strMeasure4!! else "removeLater",
        if (!(meal.strMeasure5 == null || meal.strMeasure5!!.trim { it <= ' ' }.equals(""))) meal.strMeasure5!! else "removeLater",
        if (!(meal.strMeasure6 == null || meal.strMeasure6!!.trim { it <= ' ' }.equals(""))) meal.strMeasure6!! else "removeLater",
        if (!(meal.strMeasure7 == null || meal.strMeasure7!!.trim { it <= ' ' }.equals(""))) meal.strMeasure7!! else "removeLater",
        if (!(meal.strMeasure8 == null || meal.strMeasure8!!.trim { it <= ' ' }.equals(""))) meal.strMeasure8!! else "removeLater",
        if (!(meal.strMeasure9 == null || meal.strMeasure9!!.trim { it <= ' ' }.equals(""))) meal.strMeasure9!! else "removeLater",
        if (!(meal.strMeasure10 == null || meal.strMeasure10!!.trim { it <= ' ' }.equals(""))) meal.strMeasure10!! else "removeLater",
        if (!(meal.strMeasure11 == null || meal.strMeasure11!!.trim { it <= ' ' }.equals(""))) meal.strMeasure11!! else "removeLater",
        if (!(meal.strMeasure12 == null || meal.strMeasure12!!.trim { it <= ' ' }.equals(""))) meal.strMeasure12!! else "removeLater",
        if (!(meal.strMeasure13 == null || meal.strMeasure13!!.trim { it <= ' ' }.equals(""))) meal.strMeasure13!! else "removeLater",
        if (!(meal.strMeasure14 == null || meal.strMeasure14!!.trim { it <= ' ' }.equals(""))) meal.strMeasure14!! else "removeLater",
        if (!(meal.strMeasure15 == null || meal.strMeasure15!!.trim { it <= ' ' }.equals(""))) meal.strMeasure15!! else "removeLater",
        if (!(meal.strMeasure16 == null || meal.strMeasure16!!.trim { it <= ' ' }.equals(""))) meal.strMeasure16!! else "removeLater",
        if (!(meal.strMeasure17 == null || meal.strMeasure17!!.trim { it <= ' ' }.equals(""))) meal.strMeasure17!! else "removeLater",
        if (!(meal.strMeasure18 == null || meal.strMeasure18!!.trim { it <= ' ' }.equals(""))) meal.strMeasure18!! else "removeLater",
        if (!(meal.strMeasure19 == null || meal.strMeasure19!!.trim { it <= ' ' }.equals(""))) meal.strMeasure19!! else "removeLater",
        if (!(meal.strMeasure20 == null || meal.strMeasure20!!.trim { it <= ' ' }.equals(""))) meal.strMeasure20!! else "removeLater").subList(0,count)
    var source: String = if(meal.strSource != null) meal.strSource!! else "Source not found"
    var image: String = if(meal.strImageSource != null) meal.strImageSource!! else "image source not found"
    var commons: String = if(meal.strCreativeCommonsConfirmed != null) meal.strCreativeCommonsConfirmed!! else "no idea what is this but its not found"
    var date: Date? = if(meal.dateModified != null) meal.dateModified!! else null
}
