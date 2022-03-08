package com.ksainthi.inance.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.ksainthi.inance.R

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Code(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var firstDigit: EditText
    private lateinit var secondDigit: EditText
    private lateinit var thirdDigit: EditText
    private lateinit var fourthDigit: EditText

    init {


        setPadding(30, 50, 30, 50)

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.code, this, true)

        firstDigit = findViewById(R.id.first_digit)
        secondDigit = findViewById(R.id.second_digit)
        thirdDigit = findViewById(R.id.third_digit)
        fourthDigit = findViewById(R.id.fourth_digit)

    }

    private fun concatenate(vararg string: String?): String {
        return string.joinToString("")
    }

    fun getValue(): String = concatenate(
        firstDigit.text.toString(),
        secondDigit.text.toString(),
        thirdDigit.text.toString(),
        fourthDigit.text.toString()
    )

    fun clear() {
        firstDigit.setText("")
        secondDigit.setText("")
        thirdDigit.setText("")
        fourthDigit.setText("")
    }


}