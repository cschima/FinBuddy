package com.example.fingrow.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fingrow.databinding.FragmentNewGoalTitleAmountBinding
import java.text.NumberFormat
import kotlin.math.max
import kotlin.math.min


class NewGoalTitleAmountFragment : Fragment() {

    private var _binding: FragmentNewGoalTitleAmountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val newGoalViewModel =
            ViewModelProvider(requireActivity())[NewGoalViewModel::class.java]

        _binding = FragmentNewGoalTitleAmountBinding.inflate(inflater, container, false)
        binding.amountEdit.inputType = InputType.TYPE_CLASS_NUMBER

        newGoalViewModel.setPos(1)
        newGoalViewModel.setValid(isValid())

        var actData: Intent? = null
        newGoalViewModel.data.observe(requireActivity(), Observer {
            actData = it ?: return@Observer
        })

        binding.titleEdit.afterTextChanged {
            actData!!.putExtra("title", binding.titleEdit.text.toString())
            newGoalViewModel.setData(actData!!)
            newGoalViewModel.setValid(isValid())
        }

        var currentFormattedAmountText = ""
        binding.amountEdit.apply {
            afterTextChanged { s ->
                if (s != currentFormattedAmountText) {
                    val cleanString: String = s.replace("""[$,.]""".toRegex(), "")
                    val parsed = cleanString.ifEmpty{"0"}.toDouble()
                    val formattedWithDec = NumberFormat.getCurrencyInstance().format((parsed))
                    val formatted = formattedWithDec.substring(0, formattedWithDec.length - 3)

                    val oldSelection = binding.amountEdit.selectionStart
                    val lengthDif = formatted.length - currentFormattedAmountText.length
                    currentFormattedAmountText = formatted
                    binding.amountEdit.setText(formatted)
                    if (formatted == "$0") {
                        binding.amountEdit.setSelection(formatted.length)
                    } else {
                        binding.amountEdit.setSelection(
                            max(
                                min(
                                    oldSelection + (lengthDif / 2),
                                    formatted.length
                                ), 1
                            )
                        )
                    }

                    actData!!.putExtra("amount", binding.amountEdit.text.toString().replace("[$|,]".toRegex(), ""))
                    newGoalViewModel.setData(actData!!)

                    newGoalViewModel.setValid(isValid())
                }
            }

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE && isValid()) {
                    (activity as NewGoalActivity).incrementStage(1)
                }
                false
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun isValid(): Boolean {
        return binding.titleEdit.text.isNotEmpty() &&
                binding.amountEdit.text.isNotEmpty() &&
                binding.amountEdit.text.replace("[$|,]".toRegex(), "").toDouble() > 0
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}