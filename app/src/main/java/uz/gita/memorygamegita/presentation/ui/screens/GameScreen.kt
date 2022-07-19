package uz.gita.memorygamegita.presentation.ui.screens

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.memorygamegita.R
import uz.gita.memorygamegita.data.local.MySharedPref
import uz.gita.memorygamegita.data.model.GameData
import uz.gita.memorygamegita.data.model.LevelEnum
import uz.gita.memorygamegita.databinding.ScreenGameBinding
import uz.gita.memorygamegita.presentation.ui.dialog.DialogGiveUp
import uz.gita.memorygamegita.presentation.ui.dialog.NewGameDialog
import uz.gita.memorygamegita.presentation.ui.dialog.PauseDialog
import uz.gita.memorygamegita.presentation.ui.dialog.WinDialog
import uz.gita.memorygamegita.presentation.viewmodel.GameViewModel
import uz.gita.memorygamegita.presentation.viewmodel.impl.GameViewModelImpl
import uz.gita.memorygamegita.utils.scope

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {
    private val binding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val args: GameScreenArgs by navArgs()
    private var count = 0
    private var heightItem = 0
    private var widthItem = 0
    private var xCount = 0
    private var yCount = 0
    private var level: LevelEnum = LevelEnum.EASY
    private val views = ArrayList<ImageView>()
    private var temp = 0f
    private lateinit var preference: MySharedPref
    private var mediaPlayerItem = MediaPlayer()
    private var mediaPlayerSame = MediaPlayer()
    private var mediaPlayerNonSame = MediaPlayer()
    private var mediaPlayerWinner = MediaPlayer()
    private var counterOfSearch = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        mediaPlayerItem = MediaPlayer.create(requireContext(), R.raw.item_click)
        mediaPlayerSame = MediaPlayer.create(requireContext(), R.raw.same_item)
        mediaPlayerNonSame = MediaPlayer.create(requireContext(), R.raw.non_same)
        mediaPlayerWinner = MediaPlayer.create(requireContext(), R.raw.winner_game)
        preference = MySharedPref(requireContext())

        binding.pause.isEnabled = false

        binding.pause.setOnClickListener {

        }

        level = args.level
        xCount =
            level.x // xCount va yCount biz maydon qilib ketishimiz kerak chunki biz ularni post metodi ichida chaqiramiz post metodi esa view inflate bolgandan keyin chaqiriladi, bu vaziyatda esa lifecyclening metodlarining scoplari yopilgan boladi. Agar xCount va yCount ni metodni ichida chaqirilsa uni post metodi chaqirib yuboradi va hatolik beradi
        yCount = level.y
        count = xCount * yCount

        main.post {
            heightItem = (main.height / (yCount + 3))
            widthItem = (main.width / (xCount + 0.7)).toInt()

//            temp = (main.width - widthItem * xCount) / 20f

            imageGroup.layoutParams.height = heightItem * (yCount)
            imageGroup.layoutParams.width = widthItem * (xCount)

            loadView()
            viewModel.loadData(level)
        }
        viewModel.getAllDataByLevel.observe(viewLifecycleOwner, getAllDataObserver)
        viewModel.openImageLiveData.observe(viewLifecycleOwner, openImageObserver)
        viewModel.closeImageLiveData.observe(viewLifecycleOwner, closeImageObserver)
        viewModel.goOutLiveData.observe(viewLifecycleOwner, goOutObserver)
        viewModel.finishLiveData.observe(viewLifecycleOwner, finishObserver)
        viewModel.onClickPauseLiveData.observe(viewLifecycleOwner, onClickPauseObserver)
        viewModel.shakeLiveData.observe(viewLifecycleOwner, shakeObserver)

        binding.textLevel.text = preference.stage.toString()

        binding.pause.setOnClickListener {
            if (preference.soundClick == 1)
                mediaPlayerItem.start()
            viewModel.onClickPause()
        }
    }

    private val finishObserver = Observer<Unit> {
        mediaPlayerWinner.start()
        val dialog = WinDialog()
        dialog.setOkClickListener {
            if (preference.soundClick == 1) {
                mediaPlayerItem.start()
            }
            dialog.dismiss()

            binding.textLevel.text = preference.stage.toString()
            views.clear()
            loadView()
            viewModel.loadData(level)
            counterOfSearch = 0
        }
        dialog.isCancelable = false
        dialog.show(childFragmentManager, "WIN")
    }

    private val goOutObserver = Observer<Pair<ImageView, ImageView>> {
        if (preference.soundClick == 1)
            mediaPlayerSame.start()

        val x11 = binding.imageGroup.width / 2 - it.first.width
        val y11 = binding.imageGroup.height / 2 - it.first.height / 2f
        val x22 = binding.imageGroup.width / 2
        val y22 = binding.imageGroup.height / 2 - it.second.height / 2f

        val x1 = -it.first.width
        val x2 = binding.imageGroup.width

        it.first.animate().setDuration(400).x(x11.toFloat()).y(y11)
            .setInterpolator(DecelerateInterpolator()).start()
        it.second.animate().setDuration(400).x(x22.toFloat()).y(y22)
            .setInterpolator(DecelerateInterpolator()).withEndAction {

                it.first.animate().setDuration(500).x(x1.toFloat()).y(y11)
                    .setInterpolator(AccelerateInterpolator()).start()
                it.second.animate().setDuration(500).x(x2.toFloat()).y(y22)
                    .setInterpolator(AccelerateInterpolator()).start()
            }.start()


        viewModel.setOK()
    }

    private val getAllDataObserver = Observer<List<GameData>> {
        lifecycleScope.launch {
            for (i in it.indices) {
                views[i].isEnabled = false
                views[i].tag = it[i]
                views[i].setImageResource(it[i].image)
                views[i].setOnClickListener { view ->
                    view.tag = it[i]
                    viewModel.openCloseImage(views[i])
                }
            }

            when (level) {
                LevelEnum.EASY -> {
                    delay(2000)
                }
                LevelEnum.MEDIUM -> {
                    delay(3000)
                }
                LevelEnum.HARD -> {
                    delay(4000)
                }
            }
            for (i in views) {
                i.isEnabled = true
                i.rotationY = 360f
                i.animate().setDuration(200).rotationY(271f).withEndAction {
                    i.setImageResource(R.drawable.card_back)
                    i.rotationY = 89f
                    i.animate().setDuration(200).rotationY(0f).withEndAction {
                        binding.pause.isEnabled = true
                    }
                }.start()
            }
        }
    }

    private val openImageObserver = Observer<ImageView> {
        if (preference.soundClick == 1) {
            mediaPlayerItem.start()
        }
        it.isEnabled = false
        it.animate().setDuration(200).rotationY(90f).withEndAction {
            it.setImageResource((it.tag as GameData).image)
            it.rotationY = 270f
            it.animate().setDuration(200).rotationY(360f).withEndAction {
            }
        }.start()
    }
    private val closeImageObserver = Observer<Pair<ImageView, ImageView>> {
        if (preference.soundClick == 1) {
            mediaPlayerNonSame.start()
        }

        it.first.rotationY = 360f
        it.first.animate().setDuration(200).rotationY(270f).withEndAction {
            it.first.setImageResource(R.drawable.card_back)
            it.first.rotationY = 90f
            it.first.animate().setDuration(200).rotationY(0f).withEndAction {
                it.first.isEnabled = true
            }
        }.start()
        it.second.rotationY = 360f
        it.second.animate().setDuration(200).rotationY(270f).withEndAction {
            it.second.setImageResource(R.drawable.card_back)
            it.second.rotationY = 90f
            it.second.animate().setDuration(200).rotationY(0f).withEndAction {
                it.second.isEnabled = true
                viewModel.setOK()
            }
        }.start()
    }
    private val onClickPauseObserver = Observer<Unit> {
        val dialogPause = PauseDialog(requireContext())
        val dialogNewGame = NewGameDialog()
        val dialogGiveUp = DialogGiveUp()

        dialogPause.setOnCLickSoundListener {
            if (preference.soundClick == 1) {
                mediaPlayerItem.start()
            }
        }

        dialogPause.setOnClickMenuListener {
            if (preference.soundClick == 1) {
                mediaPlayerItem.start()
            }
            dialogGiveUp.setOnClickYeListener {
                if (preference.soundClick == 1) {
                    mediaPlayerItem.start()
                }
                dialogGiveUp.dismiss()
                dialogPause.dismiss()
                findNavController().popBackStack()
            }
            dialogGiveUp.setOnClickNoListener {
                if (preference.soundClick == 1) {
                    mediaPlayerItem.start()
                }
                dialogGiveUp.dismiss()
            }
            dialogGiveUp.show(childFragmentManager, "dialogGiveUp")
        }
        dialogPause.setOnClickNewGameListener {
            if (preference.soundClick == 1) {
                mediaPlayerItem.start()
            }
            dialogNewGame.setOnCLickYesListener {
                if (preference.soundClick == 1) {
                    mediaPlayerItem.start()
                }
                dialogPause.dismiss()
                dialogNewGame.dismiss()
                newGameDialog()
            }
            dialogNewGame.setOnCLickNoListener {
                if (preference.soundClick == 1) {
                    mediaPlayerItem.start()
                }
                dialogNewGame.dismiss()
            }
            dialogNewGame.show(childFragmentManager, "dialogNewGame")
        }
        dialogPause.setOnClickContinueListener {
            if (preference.soundClick == 1) {
                mediaPlayerItem.start()
            }
            dialogPause.dismiss()
        }

        dialogPause.show(childFragmentManager, "dialogPause")
    }

    private fun newGameDialog() {
        for (i in views.indices) {
            views[i].setImageResource(0)
        }
        views.clear()
        loadView()
        viewModel.loadData(level)
        counterOfSearch = 0
    }

    private val shakeObserver = Observer<ImageView> {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(
                it,
                "rotation",
                0f,
                25f,
                -25f,
                25f,
                -25f,
                15f,
                -15f,
                6f,
                -6f,
                0f
            )
        )
        animatorSet.start()
    }

    private fun loadView() {
        for (i in 0 until level.x) {
            for (j in 0 until level.y) {
                val image = ImageView(requireContext())
                binding.imageGroup.addView(image)
                image.layoutParams.height = heightItem
                image.layoutParams.width = widthItem

                image.x = i * (widthItem) + temp
                image.y = j * (heightItem) * 1f
                image.scaleType = ImageView.ScaleType.FIT_XY
                image.setPadding(8, 8, 8, 8)

                image.setImageResource(R.drawable.card_back)
                views.add(image)
            }
        }
    }
}