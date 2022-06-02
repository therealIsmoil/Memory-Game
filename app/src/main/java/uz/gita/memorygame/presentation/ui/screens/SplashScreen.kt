package uz.gita.memorygame.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.gita.memorygame.R
import uz.gita.memorygame.presentation.viewmodel.SplashViewModel
import uz.gita.memorygame.presentation.viewmodel.impl.SplashViewModelImpl

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openNextScreenLiveData.observe(viewLifecycleOwner, openNextObserver)
    }

    private val openNextObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_splashScreen_to_levelScreen)
    }
}