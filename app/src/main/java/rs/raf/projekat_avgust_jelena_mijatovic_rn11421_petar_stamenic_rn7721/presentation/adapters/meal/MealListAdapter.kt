package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.meal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentMealListItemBinding
import kotlin.math.roundToInt

class MealListAdapter:RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {
    private var onClickListener : OnClickListener? = null
    inner class MealViewHolder(val binding: FragmentMealListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<MealWithCalories>(){
        override fun areItemsTheSame(oldItem: MealWithCalories, newItem: MealWithCalories): Boolean {
            return oldItem.meal.id == newItem.meal.id
        }

        override fun areContentsTheSame(oldItem: MealWithCalories, newItem: MealWithCalories): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)
    var shortMeals: List<MealWithCalories>
        get() = differ.currentList
        set(value) {differ.submitList(value)}


    override fun getItemCount() = shortMeals.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(FragmentMealListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val shortMeal = shortMeals[position]
        holder.binding.apply {
            smName.text = shortMeal.meal.name
            smKcal.text = shortMeal.calculateCaloriesByMesurements().roundToInt().toString()+" kcal"
            DownloadImageFromInternet(smThumb).execute(shortMeal.meal.thumbnail)
        }

        holder.itemView.setOnClickListener{
            if(onClickListener != null){
                onClickListener!!.onClick(position,shortMeal)
            }
        }
    }
    fun add(meal: MealWithCalories){
        var list = differ.currentList.toMutableList()
        list.add(meal)
        differ.submitList(list)

    }

    interface OnClickListener{
        fun onClick(position: Int,shortMeal: MealWithCalories)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
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
            imageView.setImageBitmap(result)
        }
    }
}
