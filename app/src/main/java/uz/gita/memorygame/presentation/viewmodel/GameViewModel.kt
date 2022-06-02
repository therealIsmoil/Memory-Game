package uz.gita.memorygame.presentation.viewmodel

import android.widget.ImageView
import androidx.lifecycle.LiveData
import uz.gita.memorygame.data.model.GameData
import uz.gita.memorygame.data.model.LevelEnum

interface GameViewModel {
    val getAllDataByLevel: LiveData<List<GameData>>
    val openImageLiveData: LiveData<ImageView>
    val closeImageLiveData: LiveData<Pair<ImageView, ImageView>>
    val goOutLiveData: LiveData<Pair<ImageView, ImageView>>
    val finishLiveData: LiveData<Unit>
    val onClickPauseLiveData: LiveData<Unit>
    val shakeLiveData: LiveData<ImageView>

    fun setOK()
    fun loadData(level: LevelEnum)
    fun openCloseImage(view: ImageView)
    fun onClickPause()

}


//    val timerLiveData: LiveData<String>
//    fun startTimer(time: Int)
