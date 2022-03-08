package com.ksainthi.inance.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.ksainthi.inance.R

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

enum class AlertType {
    ERROR,
    SUCCESS,
    INFO
}

data class AlertDesc(
    val type: AlertType,
    val text: String
)

class Alert(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), CoroutineScope {


    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    private val errorColor = Color.parseColor("#922D50")
    private val errorBorderColor = Color.parseColor("#6D223C")

    private val infoColor = Color.parseColor("#247BA0")
    private val infoBorderColor = Color.parseColor("#1A5974")

    private val successColor = Color.parseColor("#2E933C")
    private val successBorderColor = Color.parseColor("#2E933C")

    init {



        setPadding(30, 50, 30, 50)
        visibility = View.GONE

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.alert, this, true)

    }


    private fun getGradientDrawable(strokeColor: Int, fillColor: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(fillColor)
        gradientDrawable.setStroke(4, strokeColor)
        gradientDrawable.cornerRadius = 15f

        return gradientDrawable
    }

    fun showMessage(text: String, type: AlertType = AlertType.INFO) {
        background = when (type) {
            AlertType.ERROR -> getGradientDrawable(errorBorderColor, errorColor)
            AlertType.SUCCESS -> getGradientDrawable(successBorderColor, successColor)
            AlertType.INFO -> getGradientDrawable(infoBorderColor, infoColor)
        }
        visibility = View.VISIBLE
        findViewById<TextView>(R.id.alertText).text = text
        hide()
    }

    private fun hide(timeout: Long = 3000L) = launch {
        delay(timeout)
        visibility = View.GONE
    }


}