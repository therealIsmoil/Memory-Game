package uz.gita.memorygame.presentation.viewmodel

import androidx.lifecycle.LiveData

interface SplashViewModel {
    val openNextScreenLiveData: LiveData<Unit>
}