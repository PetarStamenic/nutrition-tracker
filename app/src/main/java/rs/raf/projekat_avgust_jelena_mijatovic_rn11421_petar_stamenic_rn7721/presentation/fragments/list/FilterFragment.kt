package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentFilterBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.meal.FilterAdapter
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.list.FilterViewModel

class FilterFragment() :Fragment(){
    private lateinit var binding: FragmentFilterBinding
    lateinit var filterAdapter: FilterAdapter
    private lateinit var fSearch: SearchView
    private lateinit var fFilter: SearchView
    private lateinit var fSpinner: Spinner
    private lateinit var fText: TextView
    private val viewModel by viewModel<FilterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater,container,false)!!
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setupRecyclerView()
        setupSpiner()
        populateList()
        setupSearch()
        setupText()
    }

    private fun setupText(){
        fText = binding.fText
        fText.setText("Filter")
        fText.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val mealListFragment = MealListFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.filter_fragment_daily,mealListFragment)?.commit()
            }

        })
    }

    private fun setupSearch(){
        fFilter = binding.fFilter
        fFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.filter(p0)
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })


        fSearch = binding.fSearch
        fSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                try {
                    Repository.getInstance().filterKcalMin = binding.filterKcalMin.text.toString().toInt()
                } catch (e:Exception){
                    Repository.getInstance().filterKcalMin = 0
                }
                try {
                    Repository.getInstance().filterKcalMax = binding.filterKcalMax.text.toString().toInt()
                } catch (e:Exception){
                    Repository.getInstance().filterKcalMax = Int.MAX_VALUE
                }
                Repository.getInstance().category = null
                Repository.getInstance().area = null
                Repository.getInstance().ingredient = null
                Repository.getInstance().search = p0.toString()
                val mealListFragment = MealListFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.filter_fragment_daily,mealListFragment)?.commit()
                return false
            }
        })
    }

    private fun populateList(){
        viewModel.loadData()
        Repository.getInstance().shortCategoriesList.observe(this.viewLifecycleOwner, Observer {
            filterAdapter.shortCategories = it.sortedBy{item -> item.strCategory}
        })
        Repository.getInstance().shortAreaList.observe(this.viewLifecycleOwner, Observer {
            filterAdapter.shortAreas = it.sortedBy{item -> item.strArea}
        })
        Repository.getInstance().ingredientList.observe(this.viewLifecycleOwner, Observer {
            filterAdapter.shortIngredients = it.sortedBy{item -> item.strIngredient}
        })
    }

    private fun setupSpiner(){
        fSpinner = binding.fSpiner
        fSpinner.setSelection(0,false)
        fSpinner.adapter = ArrayAdapter.createFromResource(this.requireContext(),R.array.filtering_options, android.R.layout.simple_spinner_item)
        fSpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(Repository.getInstance().filter != p2) {
                    Repository.getInstance().filter = p2
                    populateList()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
    private fun setupRecyclerView(){
        binding.filterRecyclerView.layoutManager = LinearLayoutManager(this.context)
        filterAdapter = FilterAdapter()
        filterAdapter.setOnClickListener(object : FilterAdapter.OnClickListener{
            override fun onClick(position: Int,item : String) {
                println("CLICK")
                try {
                    Repository.getInstance().filterKcalMin = binding.filterKcalMin.text.toString().toInt()
                } catch (e:Exception){
                    Repository.getInstance().filterKcalMin = 0
                }
                try {
                    Repository.getInstance().filterKcalMax = binding.filterKcalMax.text.toString().toInt()
                } catch (e:Exception){
                    Repository.getInstance().filterKcalMax = Int.MAX_VALUE
                }
                when(Repository.getInstance().filter){
                    0-> {
                        Repository.getInstance().category = item
                        Repository.getInstance().area = null
                        Repository.getInstance().ingredient = null
                    }
                    1-> {
                        Repository.getInstance().category = null
                        Repository.getInstance().area = item
                        Repository.getInstance().ingredient = null
                    }
                    2-> {
                        Repository.getInstance().category = null
                        Repository.getInstance().area = null
                        Repository.getInstance().ingredient = item.replace(" ","_")
                    }
                }
                val mealListFragment = MealListFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.filter_fragment_daily,mealListFragment)?.commit()
            }
        })
        binding.filterRecyclerView.addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
        binding.filterRecyclerView.adapter = filterAdapter
    }

}