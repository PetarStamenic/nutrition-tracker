package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentHistoryBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.profile.HistoryAdapter
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal.MealSingleFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile.HistoryViewModel

class HistoryFragment: Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var planAdapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView
    val viewModel by viewModel<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        setupRecyclerView()
        loadData()
    }

    private fun loadData(){
        planAdapter.planItems = listOf()
        viewModel.loadData()
        Repository.getInstance().historyList.observe(this.viewLifecycleOwner, Observer {
            planAdapter.planItems = it.sortedBy { item-> item.date }
        })
    }
    private fun setupRecyclerView(){
        recyclerView = binding.historyRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        planAdapter = HistoryAdapter()
        planAdapter.fragment = this@HistoryFragment
        planAdapter.setOnClickListener(object : HistoryAdapter.OnClickListener{
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