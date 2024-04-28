package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.PlanItem
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentDailyPlanItemBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.HistoryFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal.MealSingleFragment
import kotlin.math.roundToInt

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.PlanViewHolder>() {
    private var onClickListener: OnClickListener? = null
    lateinit var fragment: HistoryFragment

    inner class PlanViewHolder(val binding: FragmentDailyPlanItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<PlanItem>() {
        override fun areItemsTheSame(oldItem: PlanItem, newItem: PlanItem): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: PlanItem, newItem: PlanItem): Boolean {
            return oldItem.mealWithCalories.meal.id == newItem.mealWithCalories.meal.id &&
                    oldItem.date == newItem.date && oldItem.mealType == newItem.mealType && oldItem.uid == newItem.uid
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var planItems: List<PlanItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        return PlanViewHolder(
            FragmentDailyPlanItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = planItems.size

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val planItem = planItems[position]
        holder.binding.apply {
            dailuPlanItemName.setText(planItem.mealWithCalories.meal.name)
            dailyPlanDate.setText(planItem.date.toString() + " " + planItem.mealType)
            dailyPlanItemKcal.setText(
                planItem.mealWithCalories.calculateCaloriesByMesurements().roundToInt().toString()
            )
            DownloadImageFromInternet(dailyPlanItemThumb).execute(planItem.mealWithCalories.meal.thumbnail)
            dailyPlanItemDelete.isVisible = false
            dailyPlanItemThumb.setOnClickListener {
                //TODO promeni na curr meal umesto id
                Repository.getInstance().mealWithCalories = planItem.mealWithCalories
                val mealSingleFragment = MealSingleFragment()

                fragment.activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.meal_single_fragment, mealSingleFragment)?.commit()
                fragment.activity?.findViewById<TabLayout>(R.id.activity_main_tabLayout)
                    ?.getTabAt(2)?.select()

            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, planItem: PlanItem)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    private inner class DownloadImageFromInternet(var imageView: ImageView) :
        AsyncTask<String, Void, Bitmap?>() {
        init {
        }

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}