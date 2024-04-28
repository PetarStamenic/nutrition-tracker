package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentProfileBinding

class ProfileFragment:Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    private fun initButtons() {
        binding.apply {
            profileButtonDailyPlan.setOnClickListener {
                val dailyPlanFragment: Fragment = DailyPlanFragment()
                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.profile_fragment_container, dailyPlanFragment)
                transaction.commit()
            }
            profileButtonHistory.setOnClickListener {
                val historyFragment: Fragment = HistoryFragment()
                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.profile_fragment_container, historyFragment)
                transaction.commit()
            }
            profileButtonStatistics.setOnClickListener {
                val statisticsFragment: Fragment = StatisticsFragment()
                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.profile_fragment_container, statisticsFragment)
                transaction.commit()
            }
            profileButtonFavorites.setOnClickListener {
                val favoritesFragment: Fragment = FavoritesFragment()
                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.profile_fragment_container, favoritesFragment)
                transaction.commit()
            }

        }
    }
}