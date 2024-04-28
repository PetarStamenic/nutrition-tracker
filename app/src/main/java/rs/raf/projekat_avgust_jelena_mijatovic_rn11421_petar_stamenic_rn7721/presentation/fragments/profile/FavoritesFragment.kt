package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile

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
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.meal.PlanItem
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentFavoritesBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.profile.FavoritesAdapter
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal.MealSingleFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile.FavoritesViewModel

class FavoritesFragment: Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var planAdapter: FavoritesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteSpiner:Spinner
    val viewModel by viewModel<FavoritesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
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
        setupSpiner()
    }

    private fun setupFilter(){
        binding.favoriteFilter.setOnClickListener {
            val favoritesFilterFragment = FavoritesFilterFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_favorites,favoritesFilterFragment)?.commit()
        }
    }

    private fun setupSpiner(){
        favoriteSpiner = binding.favoriteSpiner
        favoriteSpiner.setSelection(0,false)
        favoriteSpiner.adapter = ArrayAdapter.createFromResource(this.requireContext(),R.array.favorites_options, android.R.layout.simple_spinner_item)
        favoriteSpiner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Repository.getInstance().favoritesSpinner = p2
                loadData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun loadData(){
        planAdapter.favoriteItems = listOf()
        viewModel.loadData()
        planAdapter.favoriteItems = listOf()
        Repository.getInstance().favoritesList.observe(this.viewLifecycleOwner, Observer {
            if(Repository.getInstance().favoritesSpinner == 3){
                planAdapter.favoriteItems = (it).distinct().sortedByDescending{item -> item.getCalories()?.toDouble() }

            }else{
                planAdapter.favoriteItems = (it).distinct().sortedByDescending{item -> item.getPercentage()}

            }
        })
    }
    private fun setupRecyclerView(){
        recyclerView = binding.favorttesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        planAdapter = FavoritesAdapter()
        planAdapter.fragment = this@FavoritesFragment
        planAdapter.setOnClickListener(object : FavoritesAdapter.OnClickListener{
            override fun onClick(position: Int, planItem: PlanItem) {
                //TODO promeni na curr meal umesto id
                Repository.getInstance().mealWithCalories = planItem.mealWithCalories
                val mealSingleFragment = MealSingleFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.meal_single_fragment,mealSingleFragment)?.commit()
                activity?.findViewById<TabLayout>(R.id.activity_main_tabLayout)?.getTabAt(2)?.select()
            }
        })
        recyclerView.addItemDecoration(
            DividerItemDecoration(this.context,
                DividerItemDecoration.VERTICAL)
        )
        recyclerView.adapter = planAdapter
    }
}