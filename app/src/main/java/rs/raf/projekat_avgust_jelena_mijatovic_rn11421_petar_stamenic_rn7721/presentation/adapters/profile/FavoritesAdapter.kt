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
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite.FavoriteMeal
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.favorite.Favorites
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.PlanItem
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentDailyPlanItemBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.FavoritesFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal.MealSingleFragment
import kotlin.math.roundToInt

class FavoritesAdapter:RecyclerView.Adapter<FavoritesAdapter.PlanViewHolder>() {
    private var onClickListener: OnClickListener? = null
    lateinit var fragment: FavoritesFragment

    inner class PlanViewHolder(val binding: FragmentDailyPlanItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Favorites>() {
        override fun areItemsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem.getTitle() == newItem.getCalories() && oldItem.getPercentage() == newItem.getPercentage()
        }

        override fun areContentsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem.getTitle() == newItem.getCalories() && oldItem.getPercentage() == newItem.getPercentage()        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var favoriteItems: List<Favorites>
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

    override fun getItemCount() = favoriteItems.size

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val favoriteItem = favoriteItems[position]
        holder.binding.apply {
            dailuPlanItemName.setText(favoriteItem.getTitle())
            dailyPlanDate.setText("times eaten " +favoriteItem.getTimes()+" aka: "+favoriteItem.getPercentage()+"%")
            if(favoriteItem.getCalories() != null) {
                dailyPlanItemKcal.setText(favoriteItem.getCalories())
                dailyPlanItemKcal.isVisible = true
            }else{
                dailyPlanItemKcal.isVisible = false
            }
            if(favoriteItem.getImage() != null){
                DownloadImageFromInternet(dailyPlanItemThumb).execute(favoriteItem.getImage())
            } else{
                dailyPlanItemThumb.isVisible = false
            }
            dailyPlanItemDelete.isVisible = false
            dailyPlanItemThumb.setOnClickListener {
                //TODO promeni na curr meal umesto id
                if(favoriteItem is FavoriteMeal) {
                    Repository.getInstance().mealWithCalories = favoriteItem.mealWithCalories
                    val mealSingleFragment = MealSingleFragment()

                    fragment.activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.meal_single_fragment, mealSingleFragment)?.commit()
                    fragment.activity?.findViewById<TabLayout>(R.id.activity_main_tabLayout)
                        ?.getTabAt(2)?.select()
                }
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
            imageView.isVisible = true
            imageView.setImageBitmap(result)
        }
    }
}