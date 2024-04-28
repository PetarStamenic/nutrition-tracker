package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.AppDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.PlanItemForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.UniqueCaloriesForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository

class AddCaloriesDialogFragment(val index: Int) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val myMeal = Repository.getInstance().mealWithCalories!!.meal
            var editText = EditText(context)
            builder.setView(editText);
            builder.setMessage("Please insert calories for ingredient " + myMeal.ingredients[index])
                .setPositiveButton("Confirm"
                ) { _, _ ->
                    var uniqueCalories = UniqueCaloriesForDatabase(0, myMeal.id, index, editText.text.toString().toDouble())
                    addToDatabase(uniqueCalories).execute()
                    dismiss()
                }
                .setNegativeButton("Cancel"
                ) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private inner class addToDatabase(var uniqueCaloriesForDatabase: UniqueCaloriesForDatabase): AsyncTask<Void, Void, Void?>() {
        init {
        }
        override fun doInBackground(vararg p0: Void?): Void? {
            val uniqueCaloriesDao = AppDatabase.getInstance().uniqueCaloriesDao()
            Repository.getInstance().customCalories.add(uniqueCaloriesForDatabase)
            uniqueCaloriesDao.insert(uniqueCaloriesForDatabase)
            return null
        }
    }
}