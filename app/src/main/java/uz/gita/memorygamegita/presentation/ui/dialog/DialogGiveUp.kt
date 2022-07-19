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
import uz.gita.memorygamegita.databinding.DialogGiveUpBinding

class DialogGiveUp : DialogFragment(R.layout.dialog_give_up) {
    private val binding by viewBinding(DialogGiveUpBinding::bind)
    private var onClickYesListener: (() -> Unit)? = null
    private var onClickNoListener: (() -> Unit)? = null

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
            onClickNoListener?.invoke()
        }
        binding.yes.setOnClickListener {
            onClickYesListener?.invoke()
        }
    }

    fun setOnClickYeListener(block: () -> Unit) {
        onClickYesListener = block
    }

    fun setOnClickNoListener(block: () -> Unit) {
        onClickNoListener = block
    }
}