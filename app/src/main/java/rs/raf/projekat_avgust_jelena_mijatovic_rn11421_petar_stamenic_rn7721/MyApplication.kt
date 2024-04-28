package rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rs.raf.projekat_avgust_jelena_mijatovic_rn11421_petar_stamenic_rn7721.data.coreModule

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.appContext = applicationContext
        startKoin{
            androidContext(this@MyApplication)
            modules(coreModule)
        }
    }

    companion object {
        lateinit  var appContext: Context
    }
}