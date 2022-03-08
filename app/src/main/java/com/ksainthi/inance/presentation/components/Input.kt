package com.ksainthi.inance.presentation.components

import android.content.Context
import android.graphics.Color
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.ksainthi.inance.R

class Input(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val label: TextView
    private var errorTextView: TextView
    private var input: EditText

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.edit_text, this, true)

        label = findViewById(R.id.label)
        errorTextView =  findViewById(R.id.errorText)
        input = findViewById(R.id.editText)

        context.theme.obtainStyledAttributes(attrs, R.styleable.EditText, 0, 0)
            .apply {
                try {
                    label.text = getString(R.styleable.EditText_inputLabel)!!
                    input.inputType = when(getString(R.styleable.EditText_inputType)) {
                        "password" -> TYPE_TEXT_VARIATION_PASSWORD
                        else -> TYPE_CLASS_TEXT
                    }
                }
                finally {
                    recycle()
                }
            }
    }

    fun showError(text: String) {
        errorTextView.visibility = VISIBLE
        errorTextView.text = text
        errorTextView.setTextColor(Color.RED)
    }

    fun getValue() : String = input.text.toString()
}