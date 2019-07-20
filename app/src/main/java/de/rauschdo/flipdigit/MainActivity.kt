package de.rauschdo.flipdigit

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val initialValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Section Flipmeter
        button_set?.setOnClickListener {
            try {
                val input = numberInput?.text.toString().trim().toInt()
                flipMeter?.setValue(
                    input,
                    withAnimation = true
                )
            } catch (e: NumberFormatException) {
                Toast.makeText(this@MainActivity, "Input is not a Number!", Toast.LENGTH_SHORT).show()
            }
            numberInput?.text?.clear()
        }

        numberInput?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        val input = numberInput?.text.toString().trim().toInt()
                        flipMeter?.setValue(
                            input,
                            withAnimation = true
                        )
                    } catch (e: NumberFormatException) {
                        Toast.makeText(this@MainActivity, "Input is not a Number!", Toast.LENGTH_SHORT).show()
                    }
                    return true
                }
                numberInput?.text?.clear()
                return false
            }
        })

        button_random_set?.setOnClickListener {
            val number = (0..999999).random()
            Toast.makeText(this, "Selected Random Number for Flipmeter is $number", Toast.LENGTH_SHORT).show()
            flipMeter?.setValue(
                number,
                withAnimation = true
            )
        }

//        flipMeter?.setValue(initialValue, withAnimation = true)

        //Section FlipmeterSpinner
        button_set_spinner?.setOnClickListener {
            val inputStringBuilder = StringBuilder()
            try {
                val input = numberInputSpinner?.text.toString().trim()
                if (input.length == 1 && input.toInt() <= 9) {
                    inputStringBuilder.append("0").append(input)
                } else {
                    inputStringBuilder.append(input)
                }

                inputStringBuilder.forEachIndexed { index, char ->
                    when (index) {
                        0 -> flipMeterSpinner1?.setDigit(
                            char.toString().toInt(),
                            withAnimation = true
                        )
                        1 -> flipMeterSpinner2?.setDigit(
                            char.toString().toInt(),
                            withAnimation = true
                        )
                    }
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this@MainActivity, "Input is not a Number!", Toast.LENGTH_SHORT).show()
            }
            numberInputSpinner?.text?.clear()
        }

        numberInputSpinner?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val inputStringBuilder = StringBuilder()
                    try {
                        val input = numberInputSpinner?.text.toString().trim()
                        if (input.length == 1 && input.toInt() <= 9) {
                            inputStringBuilder.append("0").append(input)
                        } else {
                            inputStringBuilder.append(input)
                        }

                        inputStringBuilder.forEachIndexed { index, char ->
                            when (index) {
                                0 -> flipMeterSpinner1?.setDigit(
                                    char.toString().toInt(),
                                    withAnimation = true
                                )
                                1 -> flipMeterSpinner2?.setDigit(
                                    char.toString().toInt(),
                                    withAnimation = true
                                )
                            }
                        }
                        return true
                    } catch (e: NumberFormatException) {
                        Toast.makeText(this@MainActivity, "Input is not a Number!", Toast.LENGTH_SHORT).show()
                    }
                    numberInputSpinner?.text?.clear()
                }
                return false
            }
        })

        button_random_set_spinner?.setOnClickListener {
            val inputStringBuilder = StringBuilder()
            val number = (0..9).random().toString()
            if (number.length == 1 && number.toInt() <= 9) {
                inputStringBuilder.append("0").append(number)
            } else {
                inputStringBuilder.append(number)
            }

            Toast.makeText(this, "Selected Random Number for FlipmeterSpinner is $number", Toast.LENGTH_SHORT).show()

            inputStringBuilder.forEachIndexed { index, char ->
                when (index) {
                    0 -> flipMeterSpinner1?.setDigit(
                        char.toString().toInt(),
                        withAnimation = true
                    )
                    1 -> flipMeterSpinner2?.setDigit(
                        char.toString().toInt(),
                        withAnimation = true
                    )
                }
            }
            numberInputSpinner?.text?.clear()
        }

//        flipMeterSpinner1?.setDigit(initialValue, withAnimation = true)
//        flipMeterSpinner2?.setDigit(initialValue, withAnimation = true)
    }
}