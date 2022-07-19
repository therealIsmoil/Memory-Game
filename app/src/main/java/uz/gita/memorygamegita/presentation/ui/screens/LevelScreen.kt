package uz.gita.memorygamegita.presentation.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import uz.gita.memorygamegita.BuildConfig
import uz.gita.memorygamegita.R
import uz.gita.memorygamegita.data.local.MySharedPref
import uz.gita.memorygamegita.data.model.LevelEnum
import uz.gita.memorygamegita.databinding.ScreenLevelBinding
import uz.gita.memorygamegita.presentation.ui.dialog.BackGameDialog
import uz.gita.memorygamegita.presentation.ui.dialog.SettingsDialog
import uz.gita.memorygamegita.presentation.viewmodel.LevelViewModel
import uz.gita.memorygamegita.presentation.viewmodel.impl.LevelViewModelImpl
import javax.inject.Inject

@AndroidEntryPoint
class LevelScreen @Inject constructor() :
    Fragment(R.layout.screen_level) {
    private val binding by viewBinding(ScreenLevelBinding::bind)
    private val viewModel: LevelViewModel by viewModels<LevelViewModelImpl>()
    private lateinit var dialogSettings: SettingsDialog
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var boolShare = false
    private lateinit var preference: MySharedPref
    private var mediaPlayerMain = MediaPlayer()
    private var mediaPlayerItem = MediaPlayer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.backLiveData.observe(this@LevelScreen, backObserver)
        viewModel.onCLickSettingsLiveData.observe(this@LevelScreen, onClickSettingsObserver)
        viewModel.openGameScreenLiveData.observe(this@LevelScreen, openGameScreenObserver)
        viewModel.openMoreGameLiveData.observe(this@LevelScreen, openMoreGameObserver)
        viewModel.onClickShareLiveData.observe(this@LevelScreen, onCLickShareObserver)
        viewModel.onCLickFeedbackLiveData.observe(this@LevelScreen, onClickFeedbackObserver)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        preference = MySharedPref(requireContext())
        dialogSettings = SettingsDialog(requireContext())
        mediaPlayerMain = MediaPlayer.create(requireContext(), R.raw.main)
        mediaPlayerMain.isLooping = true
        mediaPlayerItem = MediaPlayer.create(requireContext(), R.raw.item_click)

        dialogSettings.setOnClickMusicListener {
            mediaPlayerItem.start()
            if (preference.sound == 1) {
                mediaPlayerMain = MediaPlayer.create(requireContext(), R.raw.main)
                mediaPlayerMain.start()
            } else {
                mediaPlayerMain.stop()
            }
        }
        dialogSettings.setOnClickReviewListener {
            mediaPlayerItem.start()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${requireActivity().packageName}")
                )
            )
        }
        dialogSettings.setOnClickInfoListener {
            mediaPlayerItem.start()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://en.m.wikipedia.org/wiki/Concentration_(card_game)")
                )
            )
        }

        binding.easy.setOnClickListener {
            viewModel.onClickLevel(LevelEnum.EASY)
            mediaPlayerItem.start()
            mediaPlayerMain.stop()
        }
        binding.medium.setOnClickListener {
            viewModel.onClickLevel(LevelEnum.MEDIUM)
            mediaPlayerItem.start()
            mediaPlayerMain.stop()
        }
        binding.hard.setOnClickListener {
            viewModel.onClickLevel(LevelEnum.HARD)
            mediaPlayerItem.start()
            mediaPlayerMain.stop()
        }

        binding.moreGame.setOnClickListener {
            mediaPlayerItem.start()
            viewModel.onClickMoreGame()
        }
        binding.feedback.setOnClickListener {
            mediaPlayerItem.start()
            viewModel.onClickFeedback()
        }
        binding.backGame.setOnClickListener {
            mediaPlayerItem.start()
            viewModel.onClickBack()
        }
        binding.share.setOnClickListener {
            if (!boolShare) {
                mediaPlayerItem.start()
                boolShare = true
                viewModel.onCLickShare()

                scope.launch {
                    delay(300)
                    boolShare = false
                    cancel()
                }
            }
        }
        binding.settings.setOnClickListener {
            mediaPlayerItem.start()
            viewModel.onClickSettings()
        }
    }

    private val openGameScreenObserver = Observer<LevelEnum> {
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(it))
    }
    private val openMoreGameObserver = Observer<Unit> {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/developer?id=GITA+Dasturchilar+Akademiyasi&hl=ru")
            )
        )
    }

    private val backObserver = Observer<Unit> {
        val dialog = BackGameDialog()
        dialog.setYesClickListener {
            requireActivity().finish()
        }
        dialog.setMoreGameClickListener {
            viewModel.onClickMoreGame()
        }
        dialog.isCancelable = false
        dialog.show(childFragmentManager, "back")
    }
    private val onCLickShareObserver = Observer<Unit> {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val body =
            "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(intent, "share using"))
    }
    private val onClickSettingsObserver = Observer<Unit> {
        dialogSettings.isCancelable = false
        dialogSettings.show(childFragmentManager, "SettingsDialog")
    }
    private val onClickFeedbackObserver = Observer<Unit> {
        val emailIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "khusanov.ismoilbek@gmail.com"))
        startActivity(emailIntent)
    }

    override fun onResume() {
        super.onResume()
        if (preference.sound == 1)
            mediaPlayerMain.start()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayerMain.stop()
    }
}


//        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.infinite_animation)
//        binding.easy.startAnimation(anim)
//        binding.easyText.startAnimation(anim)
//        binding.medium.startAnimation(anim)
//        binding.mediumText.startAnimation(anim)
//        binding.hard.startAnimation(anim)
//        binding.hardText.startAnimation(anim)