package uz.gita.memorygamegita.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.memorygamegita.R
import uz.gita.memorygamegita.data.local.MySharedPref
import uz.gita.memorygamegita.databinding.DialogPauseBinding
import javax.inject.Inject

class PauseDialog @Inject constructor(@ApplicationContext context: Context) :
    DialogFragment(R.layout.dialog_pause) {
    private val binding by viewBinding(DialogPauseBinding::bind)
    private val preference = MySharedPref(context)
    private var onCLickSoundListener: (() -> Unit)? = null
    private var onClickMenuListener: (() -> Unit)? = null
    private var onClickNewGameListener: (() -> Unit)? = null
    private var onClickContinueListener: (() -> Unit)? = null

    /**
    full screen dialog
     * */
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    /**
    background transparent
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (preference.soundClick) {
            0 -> {
                binding.clickSound.setImageResource(R.drawable.soundoff_0)
            }
            1 -> {
                binding.clickSound.setImageResource(R.drawable.soundon_0)
            }
        }

        binding.clickSound.setOnClickListener {
            onCLickSoundListener?.invoke()
            when (preference.soundClick) {
                0 -> {
                    binding.clickSound.setImageResource(R.drawable.soundon_0)
                    preference.soundClick = 1
                }
                1 -> {
                    binding.clickSound.setImageResource(R.drawable.soundoff_0)
                    preference.soundClick = 0
                }
            }
        }
        binding.continueGame.setOnClickListener {
            onClickContinueListener?.invoke()
        }
        binding.menu.setOnClickListener {
            onClickMenuListener?.invoke()
        }
        binding.newGame.setOnClickListener {
            onClickNewGameListener?.invoke()
        }
    }

    fun setOnClickMenuListener(block: () -> Unit) {
        onClickMenuListener = block
    }

    fun setOnClickNewGameListener(block: () -> Unit) {
        onClickNewGameListener = block
    }

    fun setOnClickContinueListener(block: () -> Unit) {
        onClickContinueListener = block
    }
    fun setOnCLickSoundListener(block: () -> Unit){
        onCLickSoundListener = block
    }
}