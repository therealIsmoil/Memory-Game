package uz.gita.memorygame.presentation.viewmodel.impl

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.memorygame.data.local.MySharedPref
import uz.gita.memorygame.data.model.GameData
import uz.gita.memorygame.data.model.LevelEnum
import uz.gita.memorygame.domain.usecase.GetDataByLevelUseCase
import uz.gita.memorygame.presentation.viewmodel.GameViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val useCase: GetDataByLevelUseCase
) :
    ViewModel(),
    GameViewModel {
    private var firstClickView: ImageView? = null
    private var counter = 0
    private var enable = true
    private var finishGame = 0
    private val preference = MySharedPref(context)

    override val getAllDataByLevel = MutableLiveData<List<GameData>>()
    override val openImageLiveData = MutableLiveData<ImageView>()
    override val closeImageLiveData = MutableLiveData<Pair<ImageView, ImageView>>()
    override val goOutLiveData = MutableLiveData<Pair<ImageView, ImageView>>()
    override val finishLiveData = MutableLiveData<Unit>()
    override val onClickPauseLiveData = MutableLiveData<Unit>()
    override val shakeLiveData = MutableLiveData<ImageView>()


    override fun loadData(level: LevelEnum) {
        useCase.getDataByLevel(level, preference.type).onEach {
            getAllDataByLevel.value = it
            finishGame = it.size
        }.launchIn(viewModelScope)
    }

    override fun openCloseImage(view: ImageView) {
        if (enable) {
            counter = 1 - counter
            if (counter == 0) {
                enable = false
            }
            viewModelScope.launch(Dispatchers.IO) {
                if (firstClickView == null) {
                    firstClickView = view
                    openImageLiveData.postValue(view)
                    return@launch
                }
                firstClickView?.let {
                    openImageLiveData.postValue(view)
                    if ((it.tag as GameData).id != (view.tag as GameData).id) {
                        delay(700)
                        closeImageLiveData.postValue(Pair(it, view))
                    } else {
                        delay(700)
                        goOutLiveData.postValue(Pair(it, view))
                        finishGame -= 2
                        if (finishGame == 0) {
                            preference.stage++
                            delay(1300)
                            finishLiveData.postValue(Unit)
                        }
                     }
                    firstClickView = null
                }

            }
        } else {
            shakeLiveData.value = view
        }
    }

    override fun onClickPause() {
        onClickPauseLiveData.value = Unit
    }

//    override fun startTimer(time: Int) {
//        job?.cancel()
//        var minute = time
//        var second = 0
//        var seconds = time * 60
//        timerLiveData.value = "0$minute:00"
//        job = viewModelScope.launch(Dispatchers.IO) {
//            while (seconds >= 0) {
//                delay(1000)
//                minute = seconds / 60
//                second = seconds % 60
//                timerLiveData.postValue("0$minute:${
//                    if (second < 10) "0$second"
//                    else second
//                }")
//                seconds--
//            }
//        }
//    }

    override fun setOK() {
        enable = true
    }
}