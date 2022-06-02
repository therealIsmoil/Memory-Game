package uz.gita.memorygame.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.memorygame.data.model.LevelEnum
import uz.gita.memorygame.presentation.viewmodel.LevelViewModel

class LevelViewModelImpl : ViewModel(), LevelViewModel {
    override val openGameScreenLiveData = MutableLiveData<LevelEnum>()
    override val openMoreGameLiveData = MutableLiveData<Unit>()
    override val backLiveData = MutableLiveData<Unit>()
    override val onClickShareLiveData = MutableLiveData<Unit>()
    override val onCLickSettingsLiveData = MutableLiveData<Unit>()
    override val onCLickFeedbackLiveData = MutableLiveData<Unit>()

    override fun onClickLevel(level: LevelEnum) {
        openGameScreenLiveData.value = level
    }

    override fun onClickMoreGame() {
        openMoreGameLiveData.value = Unit
    }

    override fun onClickBack() {
        backLiveData.value = Unit
    }

    override fun onCLickShare() {
        onClickShareLiveData.value = Unit
    }

    override fun onClickSettings() {
        onCLickSettingsLiveData.value = Unit
    }

    override fun onClickFeedback() {
        onCLickFeedbackLiveData.value = Unit
    }
}