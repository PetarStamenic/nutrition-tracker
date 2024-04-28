package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite

class FavoriteArea (
    var numberOfMeals : Int,
    var areaName: String,
    var percantage: Double
) : Favorites {
    override fun getPercentage(): String? {
        return ((percantage*10000).toInt()/100.0).toString()
    }

    override fun getTitle(): String? {
        return areaName
    }

    override fun getImage(): String? {
        return null
    }

    override fun getTimes(): String? {
        return numberOfMeals.toString()
    }

    override fun getCalories(): String? {
        return null
    }
}