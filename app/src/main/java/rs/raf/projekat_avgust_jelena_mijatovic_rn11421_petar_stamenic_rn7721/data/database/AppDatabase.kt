package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.MyApplication
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.dao.PlanItemDao
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.dao.UniqueCaloriesDao
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.PlanItemForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.UniqueCaloriesForDatabase

@Database(entities = [PlanItemForDatabase::class, UniqueCaloriesForDatabase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(): AppDatabase {
            if(instance == null){
                synchronized(this){
                    if(instance == null){
                        instance = Room.databaseBuilder(
                            MyApplication.appContext,
                            AppDatabase::class.java, "meals"
                        ).build()

                    }
                }
            }
            return instance!!
        }
    }

    abstract fun planItemDao(): PlanItemDao
    abstract fun uniqueCaloriesDao(): UniqueCaloriesDao
}
