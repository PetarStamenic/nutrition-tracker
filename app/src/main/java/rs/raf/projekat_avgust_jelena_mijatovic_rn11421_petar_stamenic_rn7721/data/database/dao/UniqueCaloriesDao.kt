package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.UniqueCaloriesForDatabase

@Dao
interface UniqueCaloriesDao {

    @Query("SELECT * FROM calories")
    fun getAll(): List<UniqueCaloriesForDatabase>

    @Query("SELECT * FROM calories WHERE meal_id IN (:mealIds)")
    fun getByMealId(mealIds: LongArray): List<UniqueCaloriesForDatabase>

    @Insert
    fun insert(calories: UniqueCaloriesForDatabase)

    @Delete
    fun delete(calories: UniqueCaloriesForDatabase)
}