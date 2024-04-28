package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.list.FilterFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.list.MealListFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.singleMeal.MealSingleFragment
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile.ProfileFragment

class PagerAdapter(
    private val context : Context,
    fm: FragmentManager,
    internal var totalTabs: Int
    ) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                return ProfileFragment()
            }
            1->{
                if(Repository.getInstance().category == null &&Repository.getInstance().area == null &&Repository.getInstance().ingredient == null && (Repository.getInstance().search == null || Repository.getInstance().search!!.isEmpty())){
                    return FilterFragment()
                } else {
                    return MealListFragment()
                }
            }
            else ->{
                return MealSingleFragment()
            }
        }
        return Fragment()
    }

    override fun getCount(): Int {
        return totalTabs
    }
}