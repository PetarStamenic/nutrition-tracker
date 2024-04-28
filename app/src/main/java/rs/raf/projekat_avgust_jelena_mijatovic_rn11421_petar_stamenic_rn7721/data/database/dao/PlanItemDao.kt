package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.PlanItemForDatabase

@Dao
interface PlanItemDao {
    @Query("SELECT * FROM meals")
    fun getAll(): List<PlanItemForDatabase>

    @Query("SELECT * FROM meals WHERE meal_id IN (:mealIds)")
    fun searchByMealId(mealIds: LongArray): List<PlanItemForDatabase>

    @Query("SELECT * FROM meals WHERE meal_date BETWEEN :from AND :to")
    fun getBetweenDates(from: Long, to: Long): List<PlanItemForDatabase>

    @Insert
    fun insert(planItem: PlanItemForDatabase)

    @Delete
    fun delete(planItem: PlanItemForDatabase)

}