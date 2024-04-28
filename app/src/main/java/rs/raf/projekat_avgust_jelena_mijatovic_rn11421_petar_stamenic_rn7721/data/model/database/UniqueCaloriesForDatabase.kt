package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calories")
data class UniqueCaloriesForDatabase(
    @PrimaryKey(autoGenerate = true) val uId: Int = 0,
    @ColumnInfo(name = "meal_id") var mealId: Long,
    @ColumnInfo(name = "ingredient_index") var ingredientIndex: Int,
    @ColumnInfo(name = "ingredient_calories") var calories: Double
) {

}