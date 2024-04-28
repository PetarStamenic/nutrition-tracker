package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.R
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.database.AppDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.model.database.UniqueCaloriesForDatabase
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.repository.Repository
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.presentation.adapters.PagerAdapter

class MainActivity : AppCompatActivity() {
    var tabLayou: TabLayout? = null
    var viewPager: ViewPager? = null
    val CAMERA_REQUEST_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        window.setFlags(0x00000400, 0x00000400)

        ReadFromDatabase().execute()

        tabLayou = findViewById(R.id.activity_main_tabLayout)
        viewPager = findViewById(R.id.viewPager)
        //TODO - stringovi
        tabLayou!!.addTab(tabLayou!!.newTab().setText("Profile"))
        tabLayou!!.addTab(tabLayou!!.newTab().setText("Meal List"))
        if(Repository.getInstance().mealWithCalories == null){
            tabLayou!!.addTab(tabLayou!!.newTab().setText("Meal of the day"))
        } else {
            tabLayou!!.addTab(tabLayou!!.newTab().setText("Meal"))
        }

        tabLayou!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = PagerAdapter(this, supportFragmentManager,3)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayou))

        tabLayou!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    tabLayou!!.getTabAt(1)!!.select()
    }

    private inner class ReadFromDatabase(): AsyncTask<Void, Void, Void?>() {
        init {
        }
        override fun doInBackground(vararg p0: Void?): Void? {
            val uniqueCaloriesDao = AppDatabase.getInstance().uniqueCaloriesDao()
            Repository.getInstance().customCalories = uniqueCaloriesDao.getAll() as MutableList<UniqueCaloriesForDatabase>
            println(Repository.getInstance().customCalories)
            return null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var imageView: ImageView = findViewById(R.id.addMealImage)
        println(requestCode)
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

}