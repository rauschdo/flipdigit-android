package de.rauschdo.flipdigit_lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlin.math.floor
import kotlin.math.pow

class Flipmeter : LinearLayout {

    private var flipmeter: View? = null

    private var value: Int = 0

    private var animationCompleteCounter = 0

    private var mDigitSpinners: Array<FlipmeterSpinner?> = arrayOf(null)

    /*
     * Customizables
     */
    private var animationSpeed: Long? = null
    private var numberOfFlips: Int? = null
    private var randomNumbersEnabled: Boolean? = null
    private var numberStyle: NumberStyles = NumberStyles.DEFAULT

    companion object {
        private const val NUM_DIGITS = 6
    }

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(
        context,
        attrs,
        attributeSetId
    ) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet? = null) {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.Flipmeter).apply {
                animationSpeed = getInt(R.styleable.Flipmeter_msPerFlip, 250).toLong()
                numberOfFlips = getInt(R.styleable.Flipmeter_flips, 5)
                randomNumbersEnabled = getBoolean(R.styleable.Flipmeter_randomFlipping, false)
                numberStyle = getEnum(R.styleable.Flipmeter_numberStyle, NumberStyles.DEFAULT)
                recycle()
            }
        }
        inflateLayout()
    }

    private fun inflateLayout() {
        val layoutInflater = LayoutInflater.from(context)
        flipmeter = layoutInflater.inflate(R.layout.widget_flipmeter, this, true)

        mDigitSpinners = arrayOfNulls(NUM_DIGITS)

        mDigitSpinners[0] = findViewById(R.id.widget_flipmeter_spinner_1)
        mDigitSpinners[1] = findViewById(R.id.widget_flipmeter_spinner_10)
        mDigitSpinners[2] = findViewById(R.id.widget_flipmeter_spinner_100)
        mDigitSpinners[3] = findViewById(R.id.widget_flipmeter_spinner_1k)
        mDigitSpinners[4] = findViewById(R.id.widget_flipmeter_spinner_10k)
        mDigitSpinners[5] = findViewById(R.id.widget_flipmeter_spinner_100k)
    }

    fun setValue(value: Int, withAnimation: Boolean) {
        this.value = value
        var tempValue = value

        for (i in 5 downTo 1) {
            val factor = 10.0.pow(i.toDouble()).toInt()

            val digitVal = floor((tempValue / factor).toDouble()).toInt()
            tempValue -= digitVal * factor
            mDigitSpinners[i]?.setDigit(
                digitVal,
                numberStyle,
                numberOfFlips,
                animationSpeed,
                randomNumbersEnabled,
                withAnimation
            )
            changeAnimationCompleteCounter(withAnimation)
        }
        mDigitSpinners[0]?.setDigit(
            tempValue,
            numberStyle,
            numberOfFlips,
            animationSpeed,
            randomNumbersEnabled,
            withAnimation
        )
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
