package uz.gita.memorygame.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygame.R
import uz.gita.memorygame.databinding.DialogBackBinding

class BackGameDialog : DialogFragment(R.layout.dialog_back) {
    private val binding by viewBinding(DialogBackBinding::bind)
    private var yesClickListener: (() -> Unit)? = null
    private var moreGameClickListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.no.setOnClickListener {
            dismiss()
        }
        binding.yes.setOnClickListener {
            yesClickListener?.invoke()
        }
        binding.moreGames.setOnClickListener {
            moreGameClickListener?.invoke()
        }
    }

    fun setYesClickListener(block: () -> Unit) {
        yesClickListener = block
    }

    fun setMoreGameClickListener(block: () -> Unit) {
        moreGameClickListener = block
    }
}