package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.Ingredient
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.shoort.ShortArea
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.shoort.ShortCategory
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentFilterItemBinding

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>(){

    private var onClickListener : OnClickListener? = null

    inner class FilterViewHolder (val binding: FragmentFilterItemBinding) :RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Any>(){
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if((oldItem is Ingredient) && ( newItem is Ingredient)) return oldItem.idIngredient == newItem.idIngredient
            if((oldItem is ShortArea) && ( newItem is ShortArea)) return oldItem.strArea == newItem.strArea
            if((oldItem is ShortCategory) && ( newItem is ShortCategory)) return oldItem.strCategory == newItem.strCategory
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if((oldItem is Ingredient) && ( newItem is Ingredient)) return oldItem.strIngredient == newItem.strIngredient
            if((oldItem is ShortArea) && ( newItem is ShortArea)) return oldItem.strArea == newItem.strArea
            if((oldItem is ShortCategory) && ( newItem is ShortCategory)) return oldItem.strCategory == newItem.strCategory
            return false
        }
    }
    var originalCategories: MutableList<ShortCategory> = mutableListOf()
    var originalAreas: MutableList<ShortArea> = mutableListOf()
    var originalIngredients: MutableList<Ingredient> = mutableListOf()
    private val differ = AsyncListDiffer(this,diffCallback)
    var shortCategories: List<ShortCategory>
        get() = if(differ.currentList.any{it is ShortCategory }) differ.currentList as List<ShortCategory> else listOf()
        set(value){differ.submitList(value)
            if(originalCategories.isEmpty())
                originalCategories.addAll(value)
        }
    var shortAreas: List<ShortArea>
        get() = if(differ.currentList.any{it is ShortArea }) differ.currentList as List<ShortArea> else listOf()
        set(value){differ.submitList(value)
            if(originalAreas.isEmpty())
                originalAreas.addAll(value)}
    var shortIngredients: List<Ingredient>
        get() = if(differ.currentList.any{it is Ingredient}) differ.currentList as List<Ingredient> else listOf()
        set(value){differ.submitList(value)
            if(originalIngredients.isEmpty())
                originalIngredients.addAll(value)}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(FragmentFilterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = differ.currentList.size


    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val string = differ.currentList[position]
        holder.binding.apply {
            if(string is ShortCategory) fiText.text = string.strCategory
            if(string is ShortArea) fiText.text = string.strArea
            if(string is Ingredient) fiText.text = string.strIngredient
        }
        holder.itemView.setOnClickListener{if(onClickListener != null)onClickListener!!.onClick(position,holder.binding.fiText.text.toString())}
    }

    interface OnClickListener{
        fun onClick(position: Int,item :String)
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun filterList(filterList: ArrayList<Any>){

        differ.submitList(filterList)
    }

}