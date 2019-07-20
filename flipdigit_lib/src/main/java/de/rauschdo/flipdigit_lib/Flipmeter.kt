package de.rauschdo.flipdigit_lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlin.math.floor
import kotlin.math.pow

class Flipmeter : LinearLayout {

    private var value: Int = 0

    private var animationCompleteCounter = 0

    private var mDigitSpinners: Array<FlipmeterSpinner?> = arrayOf(null)

    companion object {
        private const val NUM_DIGITS = 6
    }

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet? = null) {
        mDigitSpinners = arrayOfNulls(NUM_DIGITS)

        val infService = Context.LAYOUT_INFLATER_SERVICE
        val inflater: LayoutInflater
        inflater = context.getSystemService(infService) as LayoutInflater
        inflater.inflate(R.layout.widget_flipmeter, this, true)

        mDigitSpinners[0] = findViewById<FlipmeterSpinner>(R.id.widget_flipmeter_spinner_1)
        mDigitSpinners[1] = findViewById<FlipmeterSpinner>(R.id.widget_flipmeter_spinner_10)
        mDigitSpinners[2] = findViewById<FlipmeterSpinner>(R.id.widget_flipmeter_spinner_100)
        mDigitSpinners[3] = findViewById<FlipmeterSpinner>(R.id.widget_flipmeter_spinner_1k)
        mDigitSpinners[4] = findViewById<FlipmeterSpinner>(R.id.widget_flipmeter_spinner_10k)
        mDigitSpinners[5] = findViewById<FlipmeterSpinner>(R.id.widget_flipmeter_spinner_100k)
    }

    fun setValue(value: Int, withAnimation: Boolean) {
        this.value = value
        var tempValue = value

        for (i in 5 downTo 1) {
            val factor = 10.0.pow(i.toDouble()).toInt()

            val digitVal = floor((tempValue / factor).toDouble()).toInt()
            tempValue -= digitVal * factor
            mDigitSpinners[i]?.setDigit(digitVal, withAnimation)
            changeAnimationCompleteCounter(withAnimation)
        }
        mDigitSpinners[0]?.setDigit(tempValue, withAnimation)
        changeAnimationCompleteCounter(withAnimation)
    }

    @Synchronized
    private fun changeAnimationCompleteCounter(increment: Boolean?): Int {
        return when {
            increment == null -> animationCompleteCounter
            increment -> ++animationCompleteCounter
            else -> --animationCompleteCounter
        }
    }

    interface OnValueChangeListener {
        fun onValueChange(sender: Flipmeter, newValue: Int)
    }
}
