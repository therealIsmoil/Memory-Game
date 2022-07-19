package uz.gita.memorygamegita.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygamegita.R
import uz.gita.memorygamegita.databinding.DialogNewGameBinding

class NewGameDialog : DialogFragment(R.layout.dialog_new_game) {
    private val binding by viewBinding(DialogNewGameBinding::bind)
    private var onCLickYesListener: (() -> Unit)? = null
    private var onCLickNoListener: (() -> Unit)? = null

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
        binding.no.setOnClickListener {
            onCLickNoListener?.invoke()
        }
        binding.yes.setOnClickListener {
            onCLickYesListener?.invoke()
        }
    }

    fun setOnCLickYesListener(block: () -> Unit) {
        onCLickYesListener = block
    }

    fun setOnCLickNoListener(block: () -> Unit) {
        onCLickNoListener = block
    }
}