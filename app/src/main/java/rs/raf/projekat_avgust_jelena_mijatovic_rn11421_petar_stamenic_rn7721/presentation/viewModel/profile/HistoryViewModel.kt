package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.AppDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.PlanItemForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.HistoryFragment
import java.time.LocalDate

class HistoryViewModel : ViewModel() {

    lateinit var fragment: HistoryFragment
    var data: MutableList<PlanItemForDatabase> = mutableListOf()

    fun loadData(){
        Repository.getInstance().history.clear()
        ReadFromDatabase().execute()

    }

    private inner class ReadFromDatabase(): AsyncTask<Void, Void, Void?>() {
        init {
        }
        override fun doInBackground(vararg p0: Void?): Void? {
            val planItemDao = AppDatabase.getInstance().planItemDao()
            val temp = planItemDao.getAll()
            temp.forEach{
                if(it.toPlanItem().date.isBefore(LocalDate.now()))
                    data.add(it)
            }
            println(data)
            return null
        }

        override fun onPostExecute(result: Void?) {
            data?.forEach {
                Repository.getInstance().history.add(it.toPlanItem())
                Repository.getInstance().historyList.value = Repository.getInstance().history
            }
        }
    }
}