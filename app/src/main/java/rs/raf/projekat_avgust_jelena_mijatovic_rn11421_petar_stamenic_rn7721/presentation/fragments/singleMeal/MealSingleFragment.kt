package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentMealSingleBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.singleMeal.MealSingleViewModel
import java.util.stream.IntStream.range

class MealSingleFragment:Fragment() {

    private lateinit var binding: FragmentMealSingleBinding
    private val viewModel by viewModel<MealSingleViewModel>()
    private val missingCalories = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealSingleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData(){
        viewModel.getMeal()
        Repository.getInstance().currMeal.observe(this.viewLifecycleOwner, Observer {

            var tags = ""
            for (tag in it.meal.tags) {
                tags = tags.plus("$tag, ")
            }
            tags = tags.dropLast(2)

            var ingrColumn = ""
            var num = it.meal.ingredients.size
            var cals: String
            var cal: Double
            for (i in range(0, num)) {
                cal = it.calculateCaloriesPerIngredient(i)
                if (cal == 0.0 || cal == -1.0) {
                    cals = "CALORIES NOT AVAILABLE"
                    missingCalories.add(i)
                } else {
                    cals = String.format("%.2f", cal)
                }
                ingrColumn = if (it.meal.mesurements[i] == "removeLater") {
                    ingrColumn.plus(it.meal.ingredients[i] + ' ' + "· kcal: " + cals + '\n')
                } else {
                    ingrColumn.plus(it.meal.ingredients[i] + " · " + it.meal.mesurements[i] + ' '  + "· kcal: " + cals + '\n')
                }
            }

            var video = "<iframe width=\"100%\" height=\"100%\" src=\""+it.meal.strYoutube.replace("watch?v=","embed/")+"\" title=\""+it.meal.name+"\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"

            binding.apply {
                mealSingleMealTitle.text = it.meal.name
                mealSingleMealCategory.text = getString(R.string.category, it.meal.category)
                mealSingleMealArea.text = getString(R.string.area, it.meal.area)
                mealSingleMealTags.text = getString(R.string.tags, tags)

                webView.loadData(video,"text/html","utf-8")
                webView.settings.javaScriptEnabled = true
                mealSingleMealLink.text = it.meal.strYoutube
                webView.webChromeClient = WebChromeClient()
                if(it.meal.strYoutube == "") {
                    webView.isVisible = false
                    mealSingleMealLink.text = getString(R.string.video_unavailable)
                } else {
                    mealSingleMealLink.isVisible = false
                }

                DownloadImageFromInternet(mealSingleMealImage).execute(it.meal.thumbnail)
                mealSingleMealImage.setOnClickListener {
                    val cameraDialogueFragment = CameraDialogueFragment()
                    getFragmentManager()?.let { it1 -> cameraDialogueFragment.show(it1, "") }
                }
                mealSingleMealCalories.text = getString(R.string.caloriestotal, String.format("%.2f", it.calculateCaloriesByMesurements()))
                mealSingleIngredients.text = ingrColumn
                mealSingleMealLink.movementMethod = LinkMovementMethod.getInstance()
                mealSingleInstructions.text = it.meal.instructions

                if (missingCalories.isEmpty()) {
                    mealSingleCaloriesButton.visibility = INVISIBLE
                } else {
                    mealSingleCaloriesButton.setOnClickListener {
                        for (index in missingCalories) {
                            val addCaloriesDialogFragment = AddCaloriesDialogFragment(index)
                            getFragmentManager()?.let { it1 -> addCaloriesDialogFragment.show(it1, "") }
                        }
                    }
                }

                mealSingleAddMealButton.setOnClickListener {
                    val addMealFragment: Fragment = AddMealFragment()
                    val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                    transaction.replace(R.id.meal_single_fragment, addMealFragment)
                    transaction.addToBackStack(null)
                    mealSingleAddMealButton.visibility = INVISIBLE
                    transaction.commit()
                }
            }
        })
    }


    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            println("_________________________________________________")
            println(imageURL)
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            Repository.getInstance().curMealImage = result
            imageView.setImageBitmap(result)
        }
    }

}