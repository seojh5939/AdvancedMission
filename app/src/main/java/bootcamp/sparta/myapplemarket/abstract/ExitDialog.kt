package bootcamp.sparta.myapplemarket.abstract

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import bootcamp.sparta.myapplemarket.databinding.MainDialogExitBinding
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener

open interface dialogButtonClicked {
    fun onPositiveButtonClick()
}

class ExitDialog(dialogButtonClicked: dialogButtonClicked, title: String, message: String, icon: Int) : DialogFragment(){
    private var _binding : MainDialogExitBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvTitle : TextView
    private lateinit var tvMessage: TextView
    private lateinit var ivIcon : ImageView
    private lateinit var negativeButton : Button
    private lateinit var positiveButton: Button

    private val mDialogButtonClicked : dialogButtonClicked
    private val mTitle : String
    private val mMessage : String
    private val mIcon: Int

    init {
        mDialogButtonClicked = dialogButtonClicked
        mTitle = title
        mMessage = message
        mIcon = icon
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainDialogExitBinding.inflate(inflater, container, false)
        val view = binding.root

        initView()
        setViewValue()
        initButtonOnClickListener()

        return view
    }


    private fun initView() {
        tvTitle = binding.mainDialogTitle
        tvMessage = binding.mainDialogMessage
        ivIcon = binding.mainDialogIcon
        negativeButton = binding.mainDialogNegativeButton
        positiveButton = binding.mainDialogPositiveButton
    }

    private fun setViewValue() {
        tvTitle.text = mTitle
        tvMessage.text = mMessage
        ivIcon.setImageResource(mIcon)
    }

    private fun initButtonOnClickListener() {
        positiveButton.setOnClickListener {
            mDialogButtonClicked.onPositiveButtonClick()
            dismiss()
        }

        negativeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}