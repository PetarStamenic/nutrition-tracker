package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.fragments.profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.databinding.FragmentStatisticsBinding
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.viewModel.profile.StatisticsViewModel
import java.time.LocalDate


class StatisticsFragment: Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    val viewModel by viewModel<StatisticsViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fragment = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        viewModel.loadData()
        var mealsPerDay = binding.idBarChart
        var caloriesPerDay = binding.idBarChartCalories

        Repository.getInstance().statisticsList.observe(this.viewLifecycleOwner, Observer {

            var mealsArray : MutableList<BarEntry> = mutableListOf()
            var caloriesArray : MutableList<BarEntry> = mutableListOf()
            var sum1 = 0
            var sum2 = 0
            var sum3 = 0
            var sum4 = 0
            var sum5 = 0
            var sum6 = 0
            var sum7 = 0
            var cal1 = 0.0
            var cal2 = 0.0
            var cal3 = 0.0
            var cal4 = 0.0
            var cal5 = 0.0
            var cal6 = 0.0
            var cal7 = 0.0
            println("test")
            it.forEach{item->
                run{
                    when(item.date){
                        LocalDate.ofEpochDay(LocalDate.now().plusDays(1).toEpochDay())->{
                            sum1++
                            cal1 += item.mealWithCalories.calculateCaloriesByMesurements()
                        }
                        LocalDate.ofEpochDay(LocalDate.now().plusDays(2).toEpochDay())->{
                            sum2++
                            cal2 += item.mealWithCalories.calculateCaloriesByMesurements()
                        }
                        LocalDate.ofEpochDay(LocalDate.now().plusDays(3).toEpochDay())->{
                            sum3++
                            cal3 += item.mealWithCalories.calculateCaloriesByMesurements()
                        }
                        LocalDate.ofEpochDay(LocalDate.now().plusDays(4).toEpochDay())->{
                            sum4++
                            cal4 += item.mealWithCalories.calculateCaloriesByMesurements()
                        }
                        LocalDate.ofEpochDay(LocalDate.now().plusDays(5).toEpochDay())->{
                            sum5++
                            cal5 += item.mealWithCalories.calculateCaloriesByMesurements()
                        }
                        LocalDate.ofEpochDay(LocalDate.now().plusDays(6).toEpochDay())->{
                            sum6++
                            cal6 += item.mealWithCalories.calculateCaloriesByMesurements()
                        }
                        LocalDate.ofEpochDay(LocalDate.now().plusDays(7).toEpochDay())->{
                            sum7++
                            cal7 += item.mealWithCalories.calculateCaloriesByMesurements()
                        }

                    }
                }
            }

            mealsArray.add(BarEntry(1.toFloat(),sum1.toFloat()))
            mealsArray.add(BarEntry(2.toFloat(),sum2.toFloat()))
            mealsArray.add(BarEntry(3.toFloat(),sum3.toFloat()))
            mealsArray.add(BarEntry(4.toFloat(),sum4.toFloat()))
            mealsArray.add(BarEntry(5.toFloat(),sum5.toFloat()))
            mealsArray.add(BarEntry(6.toFloat(),sum6.toFloat()))
            mealsArray.add(BarEntry(7.toFloat(),sum7.toFloat()))

            caloriesArray.add(BarEntry(1.toFloat(),cal1.toFloat()))
            caloriesArray.add(BarEntry(2.toFloat(),cal2.toFloat()))
            caloriesArray.add(BarEntry(3.toFloat(),cal3.toFloat()))
            caloriesArray.add(BarEntry(4.toFloat(),cal4.toFloat()))
            caloriesArray.add(BarEntry(5.toFloat(),cal5.toFloat()))
            caloriesArray.add(BarEntry(6.toFloat(),cal6.toFloat()))
            caloriesArray.add(BarEntry(7.toFloat(),cal7.toFloat()))


            var barDataSet = BarDataSet(mealsArray, "Number of meals per day")
            var barDataSetCalories = BarDataSet(caloriesArray, "Calories per day")

            var barData = BarData(barDataSet)
            var barDataCalories = BarData(barDataSetCalories)

            mealsPerDay.setData(barData)
            caloriesPerDay.setData(barDataCalories)


            barDataSet.setValueTextColor(Color.BLACK)
            barDataSetCalories.setValueTextColor(Color.BLACK)


            barDataSet.setValueTextSize(16f)
            mealsPerDay.getDescription().setEnabled(true)


            barDataSetCalories.setValueTextSize(16f)
            binding.warningCalorioes.text = "Calories for past 7 days"
            caloriesPerDay.getDescription().setEnabled(true)
            var tmp = binding.warningCalorioes.text
            if(cal1>2000)
                binding.warningCalorioes.text = binding.warningCalorioes.text.toString()+"\n"+"Calories excided limit for day 1"
            if(cal2>2000)
                binding.warningCalorioes.text = binding.warningCalorioes.text.toString()+"\n"+"Calories excided limit for day 2"
            if(cal3>2000)
                binding.warningCalorioes.text = binding.warningCalorioes.text.toString()+"\n"+"Calories excided limit for day 3"
            if(cal4>2000)
                binding.warningCalorioes.text = binding.warningCalorioes.text.toString()+"\n"+"Calories excided limit for day 4"
            if(cal5>2000)
                binding.warningCalorioes.text = binding.warningCalorioes.text.toString()+"\n"+"Calories excided limit for day 5"
            if(cal6>2000)
                binding.warningCalorioes.text = binding.warningCalorioes.text.toString()+"\n"+"Calories excided limit for day 6"
            if(cal7>2000)
                binding.warningCalorioes.text = binding.warningCalorioes.text.toString()+"\n"+"Calories excided limit for day 7"

            if(binding.warningCalorioes.text != tmp)
                binding.warningCalorioes.setTextColor(Color.RED)
        })
    }
}