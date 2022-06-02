package uz.gita.memorygame.presentation.ui.dialog

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
import uz.gita.memorygame.R
import uz.gita.memorygame.data.local.MySharedPref
import uz.gita.memorygame.databinding.DialogSettingsBinding
import javax.inject.Inject

class SettingsDialog @Inject constructor(@ApplicationContext context: Context) :
    DialogFragment(R.layout.dialog_settings) {
    private val binding by viewBinding(DialogSettingsBinding::bind)
    private var onClickReviewListener: (() -> Unit)? = null
    private var onClickInfoListener: (() -> Unit)? = null
    private var onClickMusicListener: (() -> Unit)? = null
    private var preference = MySharedPref(context)

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
        when (preference.sound) {
            0 -> {
                binding.soundManage.setImageResource(R.drawable.soundoff_0)
            }
            1 -> {
                binding.soundManage.setImageResource(R.drawable.soundon_0)
            }
        }

        when (preference.type) {
            0 -> {
                binding.categorySport.setBackgroundResource(R.drawable.select_effect_0)
            }
            1 -> {
                binding.categoryFlag.setBackgroundResource(R.drawable.select_effect_0)
            }
            2 -> {
                binding.categoryNumbers.setBackgroundResource(R.drawable.select_effect_0)
            }
            3 -> {
                binding.categoryOther.setBackgroundResource(R.drawable.select_effect_0)
            }
        }

        binding.categorySport.setOnClickListener {
            binding.categorySport.setBackgroundResource(R.drawable.select_effect_0)
            when (preference.type) {
                1 -> {
                    binding.categoryFlag.setBackgroundResource(0)
                    preference.type = 0
                }
                2 -> {
                    binding.categoryNumbers.setBackgroundResource(0)
                    preference.type = 0
                }
                3 -> {
                    binding.categoryOther.setBackgroundResource(0)
                    preference.type = 0
                }

            }
        }
        binding.categoryFlag.setOnClickListener {
            binding.categoryFlag.setBackgroundResource(R.drawable.select_effect_0)
            when (preference.type) {
                0 -> {
                    binding.categorySport.setBackgroundResource(0)
                    preference.type = 1
                }
                2 -> {
                    binding.categoryNumbers.setBackgroundResource(0)
                    preference.type = 1
                }
                3 -> {
                    binding.categoryOther.setBackgroundResource(0)
                    preference.type = 1
                }

            }
        }
        binding.categoryNumbers.setOnClickListener {
            binding.categoryNumbers.setBackgroundResource(R.drawable.select_effect_0)

            when (preference.type) {
                0 -> {
                    binding.categorySport.setBackgroundResource(0)
                    preference.type = 2
                }
                1 -> {
                    binding.categoryFlag.setBackgroundResource(0)
                    preference.type = 2
                }
                3 -> {
                    binding.categoryOther.setBackgroundResource(0)
                    preference.type = 2
                }

            }
        }
        binding.categoryOther.setOnClickListener {
            binding.categoryOther.setBackgroundResource(R.drawable.select_effect_0)

            when (preference.type) {
                0 -> {
                    binding.categorySport.setBackgroundResource(0)
                    preference.type = 3
                }
                1 -> {
                    binding.categoryFlag.setBackgroundResource(0)
                    preference.type = 3
                }
                2 -> {
                    binding.categoryNumbers.setBackgroundResource(0)
                    preference.type = 3
                }
            }
        }

        binding.soundManage.setOnClickListener {
            preference.sound = when (preference.sound) {
                0 -> {
                    binding.soundManage.setImageResource(R.drawable.soundon_0)
                    1
                }
                else -> {
                    binding.soundManage.setImageResource(R.drawable.soundoff_0)
                    0
                }
            }
            onClickMusicListener?.invoke()
        }
        binding.info.setOnClickListener {
            onClickInfoListener?.invoke()
        }
        binding.review.setOnClickListener {
            onClickReviewListener?.invoke()
        }
        binding.continueGame.setOnClickListener {
            dismiss()
        }
    }

    fun setOnClickReviewListener(block: () -> Unit) {
        onClickReviewListener = block
    }

    fun setOnClickInfoListener(block: () -> Unit) {
        onClickInfoListener = block
    }

    fun setOnClickMusicListener(block: () -> Unit) {
        onClickMusicListener = block
    }

}