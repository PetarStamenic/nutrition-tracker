package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.MealWithCalories
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentMealListBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.meal.MealListAdapter
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal.MealSingleFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.list.MealListViewModel

class MealListFragment() : Fragment() {

    private lateinit var binding: FragmentMealListBinding
    private lateinit var mealListAdapter: MealListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var sortSpiner:Spinner
    private lateinit var orderSpinner: Spinner
    private val viewModel by viewModel<MealListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealListBinding.inflate(inflater, container, false)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        setupRecyclerView()
        loadData()
        setupFilter()
        changeTodo()
        setupFilter()
        setupSpiner()
    }

    private fun setupSpiner(){
        sortSpiner = binding.sortSpiner
        sortSpiner.setSelection(0,false)
        sortSpiner.adapter = ArrayAdapter.createFromResource(this.requireContext(),R.array.sortBy, android.R.layout.simple_spinner_item)
        sortSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Repository.getInstance().sortBy = p2
                sortData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        orderSpinner = binding.orderSpiner
        orderSpinner.setSelection(0,false)
        orderSpinner.adapter = ArrayAdapter.createFromResource(this.requireContext(),R.array.orderBy, android.R.layout.simple_spinner_item)
        orderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Repository.getInstance().orederBy = p2
                sortData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun changeTodo(){
        var text = ""
        if(Repository.getInstance().category != null)
            text ="Category: "+ Repository.getInstance().category!!
        if(Repository.getInstance().area != null)
            text ="Area: "+ Repository.getInstance().area!!
        if(Repository.getInstance().ingredient != null)
            text ="Ingredient: "+ Repository.getInstance().ingredient!!
        if(Repository.getInstance().search != null)
            text ="Search: "+ Repository.getInstance().search!!
        binding.mealListCategoryName.text = text
    }
    private fun setupFilter(){
        binding.mealListFilter.setOnClickListener {
            val filterFragment = FilterFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.meal_list_fragment_daily,filterFragment)?.commit()
        }
    }
    private fun loadData(){
        mealListAdapter.shortMeals = listOf()
        viewModel.loadData()
        Repository.getInstance().mealList.observe(this.viewLifecycleOwner, Observer {
            if(Repository.getInstance().sortBy == 0){
                if(Repository.getInstance().orederBy == 0){
                    mealListAdapter.shortMeals = (mealListAdapter.shortMeals + it).distinct().sortedBy{item -> item.meal.name}
                }else{
                    mealListAdapter.shortMeals = (mealListAdapter.shortMeals + it).distinct().sortedByDescending{item -> item.meal.name}
                }
            }else{
                if(Repository.getInstance().orederBy == 0){
                    mealListAdapter.shortMeals = (mealListAdapter.shortMeals + it).distinct().sortedBy{item -> item.calculateCaloriesByMesurements()}
                }else{
                    mealListAdapter.shortMeals = (mealListAdapter.shortMeals + it).distinct().sortedByDescending{item -> item.calculateCaloriesByMesurements()}

                }
            }
            recyclerView.scrollToPosition(0)
        })
    }

    private fun sortData(){
        Repository.getInstance().mealList.value = Repository.getInstance().meals
    }

    private fun setupRecyclerView() {
        recyclerView = binding.mealListRecyclerView
        binding.mealListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        mealListAdapter = MealListAdapter()
        mealListAdapter.setOnClickListener(object : MealListAdapter.OnClickListener{
            override fun onClick(position: Int, shortMeal: MealWithCalories) {
                //TODO promeni na curr meal umesto id
                Repository.getInstance().mealWithCalories = shortMeal
                val mealSingleFragment = MealSingleFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.meal_single_fragment,mealSingleFragment)?.commit()
                println(shortMeal)
                activity?.findViewById<TabLayout>(R.id.activity_main_tabLayout)?.getTabAt(2)?.select()
            }
        })
        binding.mealListRecyclerView.addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
        binding.mealListRecyclerView.adapter = mealListAdapter
    }




}