package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.AppDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.PlanItemForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.HistoryFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.StatisticsFragment
import java.time.LocalDate

class StatisticsViewModel  : ViewModel() {

    lateinit var fragment: StatisticsFragment
    var data: MutableList<PlanItemForDatabase> = mutableListOf()

    fun loadData(){
        Repository.getInstance().statistics.clear()
        ReadFromDatabase().execute()
    }

    private inner class ReadFromDatabase(): AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            val planItemDao = AppDatabase.getInstance().planItemDao()
            data = planItemDao.getAll().toMutableList()
            println(data)
            return null
        }

        override fun onPostExecute(result: Void?) {
            data?.forEach {
                Repository.getInstance().statistics.add(it.toPlanItem())
                Repository.getInstance().statisticsList.value = Repository.getInstance().statistics
            }
        }
    }
    }