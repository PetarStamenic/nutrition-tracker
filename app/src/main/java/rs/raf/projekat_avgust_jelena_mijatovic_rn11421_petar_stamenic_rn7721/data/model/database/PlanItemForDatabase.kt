package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.PlanItem
import java.time.LocalDate

@Entity(tableName = "meals")
class PlanItemForDatabase(
    @PrimaryKey(autoGenerate = true) val uId: Int = 0,
    @ColumnInfo(name = "meal_id")  var mealId: Long,
    @ColumnInfo(name = "meal_date") var unixDay: Long,
    @ColumnInfo(name = "meal_type") var mealType: String,
    @ColumnInfo(name = "meal_content") var content: String
) {

    @Ignore
    fun toPlanItem(): PlanItem {
        return PlanItem(uId,Gson().fromJson(content,MealWithCalories::class.java), LocalDate.ofEpochDay(unixDay),mealType)
    }

}