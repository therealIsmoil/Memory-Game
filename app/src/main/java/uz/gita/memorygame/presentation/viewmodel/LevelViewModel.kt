package uz.gita.memorygame.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.memorygame.data.model.LevelEnum

interface LevelViewModel {

    val openGameScreenLiveData: LiveData<LevelEnum>
    val openMoreGameLiveData: LiveData<Unit>
    val backLiveData: LiveData<Unit>
    val onClickShareLiveData: LiveData<Unit>
    val onCLickSettingsLiveData: LiveData<Unit>
    val onCLickFeedbackLiveData: LiveData<Unit>

    fun onClickLevel(level: LevelEnum)
    fun onClickMoreGame()
    fun onClickBack()
    fun onCLickShare()
    fun onClickSettings()
    fun onClickFeedback()
}