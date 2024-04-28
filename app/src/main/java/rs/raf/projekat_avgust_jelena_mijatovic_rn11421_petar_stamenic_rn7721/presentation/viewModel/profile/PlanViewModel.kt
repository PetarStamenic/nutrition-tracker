package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.AppDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.PlanItem
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.PlanItemForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.DailyPlanFragment
import java.time.LocalDate
import java.util.stream.IntStream

class PlanViewModel():ViewModel() {

    lateinit var fragment: DailyPlanFragment
    var data: MutableList<PlanItemForDatabase> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadData(){

        // TODO: KAD UBACIMO ROOM DATABASE dodaj logiku da se ucita u repository data
        Repository.getInstance().plan.clear()

        ReadFromDatabase().execute()
        /*

        if(Repository.getInstance().meals.size>5){

            //TODO:- OBRISI KAD NAPRAVIMO PLAN DA SE UCITAVA IZ BAZE
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now(),"Breakfest")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now(),"Launch")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now(),"Dinner")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(1),"Breakfest")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(1),"Launch")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(1),"Dinner")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(2),"Breakfest")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(2),"Launch")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(2),"Dinner")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(3),"Breakfest")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(3),"Launch")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(3),"Dinner")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(4),"Breakfest")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(4),"Launch")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(4),"Dinner")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(5),"Breakfest")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(5),"Launch")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(5),"Dinner")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(6),"Breakfest")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(6),"Launch")
            )
            Repository.getInstance().plan.add(
                PlanItem(Repository.getInstance().meals.random(),
                    LocalDate.now().plusDays(6),"Dinner")
            )

            Repository.getInstance().planList.value = Repository.getInstance().plan

        }

         */

    }

    private inner class ReadFromDatabase(): AsyncTask<Void, Void, Void?>() {
        init {
        }
        override fun doInBackground(vararg p0: Void?): Void? {
            val planItemDao = AppDatabase.getInstance().planItemDao()
            val temp = planItemDao.getAll()
            temp.forEach{
                if(it.toPlanItem().date.isAfter(LocalDate.now().minusDays(1)))
                    data.add(it)
            }
            println(data)
            return null
        }

        override fun onPostExecute(result: Void?) {
            data?.forEach {
                Repository.getInstance().plan.add(it.toPlanItem())
                Repository.getInstance().planList.value = Repository.getInstance().plan
            }
        }
    }


    inner class DeleteFromDatabase(var planItem: PlanItem): AsyncTask<Void, Void, Void?>() {
        init {
        }
        override fun doInBackground(vararg p0: Void?): Void? {
            val planItemDao = AppDatabase.getInstance().planItemDao()
            val planItemForDatabase = PlanItemForDatabase(planItem.uid,planItem.mealWithCalories.meal.id, planItem.date.toEpochDay(), planItem.mealType, Gson().toJson(planItem.mealWithCalories))
            planItemDao.delete(planItemForDatabase)

            return null
        }
    }


    fun sendEmail(recipient: String) {
        var subject = "Plan"
        var message = formatMessage()
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            fragment.activity?.startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(fragment.requireContext(), e.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun formatMessage():String{
        var meals = Repository.getInstance().plan
        var text = ""
        var newRow ="\n"
        var separator = "___________________________________________________\n"
        text = "Breakfest:"
        text += newRow
        text += separator
        meals.forEach{
            if(it.date.isBefore(LocalDate.now().plusDays(7))){
                if(it.mealType.equals("Breakfast", ignoreCase = true)){
                    text+= planItemBodyFormat(it)
                    text+= separator
                }
            }
        }
        text += "Lunch:"
        text += newRow
        text += separator
        meals.forEach{
            if(it.date.isBefore(LocalDate.now().plusDays(7))){
                if(it.mealType.equals("Lunch", ignoreCase = true)){
                    text+= planItemBodyFormat(it)
                    text+= separator
                }
            }
        }
        text += "Dinner"
        text += newRow
        text += separator
        meals.forEach{
            if(it.date.isBefore(LocalDate.now().plusDays(7))){
                if(it.mealType.equals("Dinner", ignoreCase = true)){
                    text+= planItemBodyFormat(it)
                    text+= separator
                }
            }
        }

        return text
    }

    private fun planItemBodyFormat(planItem: PlanItem):String{
        var meal = planItem.mealWithCalories.meal
        return  planItem.date.toString()+"\n"+
                meal.name+"\n"+
                "Category: "+meal.category+"\n"+
                "Area: "+meal.area+"\n"+
                formatCalories(planItem.mealWithCalories)+"\n"+
                meal.instructions+"\n"

    }

    private fun formatCalories(it: MealWithCalories):String{
        var ingrColumn = ""
        var num = it.meal.ingredients.size
        var cals: String
        var cal: Double
        for (i in IntStream.range(0, num)) {
            cal = it.calculateCaloriesPerIngredient(i)
            if (cal == 0.0 || cal == -1.0) {
                cals = "you can assume its close to 0"
            } else {
                cals = String.format("%.2f", cal)
            }
            ingrColumn = if (it.meal.mesurements[i] == "removeLater") {
                ingrColumn.plus(it.meal.ingredients[i] + ' ' + "· kcal: " + cals + '\n')
            } else {
                ingrColumn.plus(it.meal.ingredients[i] + " · " + it.meal.mesurements[i] + ' '  + "· kcal: " + cals + '\n')
            }
        }
        return ingrColumn
    }


}