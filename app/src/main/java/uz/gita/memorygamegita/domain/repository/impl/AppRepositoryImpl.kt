package uz.gita.memorygamegita.domain.repository.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.memorygamegita.data.model.GameData
import uz.gita.memorygamegita.data.model.LevelEnum
import uz.gita.memorygamegita.domain.repository.AppRepository
import javax.inject.Inject


class AppRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    AppRepository {
    private val sportImage = ArrayList<GameData>()
    private val flagImage = ArrayList<GameData>()
    private val numberImage = ArrayList<GameData>()
    private val otherImage = ArrayList<GameData>()

    init {
        loadData()
    }

    //    private fun loadData() {
//        imageList.add(GameData(0, R.drawable.card_0))
//        imageList.add(GameData(1, R.drawable.card_1))
//        imageList.add(GameData(2, R.drawable.card_2))
//        imageList.add(GameData(3, R.drawable.card_3))
//        imageList.add(GameData(4, R.drawable.card_4))
//        imageList.add(GameData(5, R.drawable.card_5))
//        imageList.add(GameData(6, R.drawable.card_6))
//        imageList.add(GameData(7, R.drawable.card_7))
//        imageList.add(GameData(8, R.drawable.card_8))
//        imageList.add(GameData(9, R.drawable.card_9))
//        imageList.add(GameData(10, R.drawable.card_10))
//        imageList.add(GameData(11, R.drawable.card_11))
//        imageList.add(GameData(12, R.drawable.card_12))
//        imageList.add(GameData(13, R.drawable.card_13))
//        imageList.add(GameData(14, R.drawable.card_14))
//        imageList.add(GameData(15, R.drawable.card_15))
//        imageList.add(GameData(16, R.drawable.card_16))
//        imageList.add(GameData(17, R.drawable.card_17))
//        imageList.add(GameData(18, R.drawable.card_18))
//        imageList.add(GameData(19, R.drawable.card_19))
//        imageList.add(GameData(20, R.drawable.card_20))
//        imageList.add(GameData(21, R.drawable.card_21))
//        imageList.add(GameData(22, R.drawable.card_22))
//        imageList.add(GameData(23, R.drawable.card_23))
//        imageList.add(GameData(24, R.drawable.card_24))
//        imageList.add(GameData(25, R.drawable.card_25))
//        imageList.add(GameData(26, R.drawable.card_26))
//        imageList.add(GameData(27, R.drawable.card_27))
//    }
    private fun loadData() {
        for (i in 0..27) {
            sportImage.add(
                GameData(
                    i,
                    context.resources.getIdentifier("card_$i", "drawable", context.packageName),
                )
            )
            flagImage.add(
                GameData(
                    i,
                    context.resources.getIdentifier("b$i", "drawable", context.packageName)
                )
            )
            numberImage.add(
                GameData(
                    i,
                    context.resources.getIdentifier("number_$i", "drawable", context.packageName),
                )
            )
            otherImage.add(
                GameData(
                    i,
                    context.resources.getIdentifier("other_$i", "drawable", context.packageName),
                )
            )
        }
    }

    override suspend fun getDataByLevel(level: LevelEnum, type: Int): List<GameData> {
        when (type) {
            0 -> {
                sportImage.shuffle()
                val count = level.x * level.y
                return sportImage.subList(0, count / 2)
            }
            1 -> {
                flagImage.shuffle()
                val count = level.x * level.y
                return flagImage.subList(0, count / 2)
            }
            2 -> {
                numberImage.shuffle()
                val count = level.x * level.y
                return numberImage.subList(0, count / 2)
            }
            else -> {
                otherImage.shuffle()
                val count = level.x * level.y
                return otherImage.subList(0, count / 2)
            }
        }
    }
}