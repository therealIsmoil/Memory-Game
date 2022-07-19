package uz.gita.memorygamegita.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPref @Inject constructor(@ApplicationContext context: Context) {
    private val preference = context.getSharedPreferences("MemoryGame", Context.MODE_PRIVATE)

    var sound: Int
        get() = preference.getInt("SOUND", 1)
        set(value) = preference.edit().putInt("SOUND", value).apply()

    var type: Int
        get() = preference.getInt("TYPE", 0)
        set(value) = preference.edit().putInt("TYPE", value).apply()

    var stage: Int
        get() = preference.getInt("STAGE", 1)
        set(value) = preference.edit().putInt("STAGE", value).apply()

    var soundClick: Int
        get() = preference.getInt("soundClick", 1)
        set(value) = preference.edit().putInt("soundClick", value).apply()
}