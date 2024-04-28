package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.AppDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MyMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.PlanItem
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.PlanItemForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentAddMealBinding
import java.time.LocalDate


class AddMealFragment : Fragment(R.layout.fragment_add_meal) {

    private lateinit var binding: FragmentAddMealBinding
    private lateinit var addMealSpinner: Spinner
    private lateinit var addMealDatePicker: DatePicker
    private lateinit var addMealImage: ImageView
    private lateinit var addMealButton : Button
    private lateinit var myMeal : MyMeal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupInfo()
        setupUI()
    }

    private fun setupInfo() {
        var progressBar = activity?.findViewById<ProgressBar>(R.id.progress_bar)
        progressBar?.isVisible = true
        myMeal = Repository.getInstance().mealWithCalories!!.meal
        binding.apply {
            addMealTitle.text = myMeal.name
            addMealImage.setImageBitmap(Repository.getInstance().curMealImage)
        }
    }

    private fun setupUI() {

        addMealSpinner = binding.addMealSpinner
        addMealSpinner.setSelection(0,false)
        addMealSpinner.adapter = ArrayAdapter.createFromResource(this.requireContext(),R.array.meal_options, android.R.layout.simple_spinner_item)

        addMealImage = binding.addMealImage
        addMealImage.setOnClickListener {
            val cameraDialogueFragment = CameraDialogueFragment()
            getFragmentManager()?.let { it1 -> cameraDialogueFragment.show(it1, "") }
        }

        addMealDatePicker = binding.addMealDatePicker

        val mealAddSingleButton: Button? = activity?.findViewById(R.id.mealSingleAddMealButton)
        addMealButton = binding.addMealButton
        addMealButton.setOnClickListener {

            val day: Int = addMealDatePicker.dayOfMonth
            val month: Int = addMealDatePicker.month
            val year: Int = addMealDatePicker.year
            val date: LocalDate = if (month < 10 && day<10) {
                LocalDate.parse("$year-0$month-0$day")
            } else if(month<10){
                LocalDate.parse("$year-0$month-$day")
            } else if(day<10){

                LocalDate.parse("$year-$month-0$day")
            }else{

                LocalDate.parse("$year-$month-$day")
            }

            val planItem = PlanItem(0,Repository.getInstance().mealWithCalories!!, date, addMealSpinner.selectedItem.toString())
            val planItemForDatabase = PlanItemForDatabase(0, planItem.mealWithCalories.meal.id, planItem.date.plusMonths(1).toEpochDay(), planItem.mealType, Gson().toJson(planItem.mealWithCalories))
            addToDatabase(planItemForDatabase).execute()

            Toast.makeText(this.requireContext(), "Your meal has been saved", Toast.LENGTH_LONG).show()
            if (mealAddSingleButton != null) {
                mealAddSingleButton.visibility = VISIBLE
            }
            getFragmentManager()?.popBackStackImmediate();
        }
    }

    private inner class addToDatabase(var planItemForDatabase: PlanItemForDatabase): AsyncTask<Void, Void, Void?>() {
        init {
        }
        override fun doInBackground(vararg p0: Void?): Void? {
            val planItemDao = AppDatabase.getInstance().planItemDao()
            planItemDao.insert(planItemForDatabase)
            return null
        }
    }

}