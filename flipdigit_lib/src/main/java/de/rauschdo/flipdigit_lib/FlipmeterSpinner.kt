package de.rauschdo.flipdigit_lib

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

class FlipmeterSpinner : RelativeLayout {

    private var flipmeterSpinner: View? = null

    private var flipDigitRandom: FlipDigitRandom? = null

    private var flipDigitLinear: FlipDigitLinear? = null

    /*
     * Customizables
     */
    private var animationSpeed: Long? = null
    private var numberOfFlips: Int? = null
    private var randomNumbersEnabled: Boolean? = null
    private var numberStyle: NumberStyles = NumberStyles.DEFAULT

    /*
     * Simple constructor used when creating a view from code.
     */
    constructor(context: Context) : super(context) {
        initialize(context)
    }

    /*
     * This is called when a view is being constructed from an XML file, supplying attributes that were specified in the
     * XML file.
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    /*
     * Perform inflation from XML and apply a class-specific base style. This constructor of View allows subclasses to
     * use their own base style when they are inflating.
     */
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initialize(context, attrs)
    }

    /*
     * Initialize all of our class members and variables
     */
    private fun initialize(context: Context, attrs: AttributeSet? = null) {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.FlipmeterSpinner).apply {
                animationSpeed = getInt(R.styleable.FlipmeterSpinner_msPerFlip, 250).toLong()
                numberOfFlips = getInt(R.styleable.FlipmeterSpinner_flips, 5)
                randomNumbersEnabled =
                    getBoolean(R.styleable.FlipmeterSpinner_randomFlipping, false)
                numberStyle = getEnum(R.styleable.Flipmeter_numberStyle, NumberStyles.DEFAULT)
                recycle()
            }
        }

        inflateLayout()

        flipDigitRandom =
            flipmeterSpinner?.let {
                FlipDigitRandom(
                    context,
                    id,
                    it,
                    animationSpeed ?: Defaults.msPerFlip,
                    null
                )
            }

        flipDigitLinear =
            flipmeterSpinner?.let {
                FlipDigitLinear(
                    context,
                    id,
                    it,
                    animationSpeed ?: Defaults.msPerFlip,
                    null
                )
            }

    }

    private fun inflateLayout() {
        val layoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        flipmeterSpinner = layoutInflater.inflate(R.layout.view_flipmeter_spinner, this)
    }

    fun setDigit(
        animateTo: Int,
        numberStyle: NumberStyles?,
        flips: Int?,
        msPerFlip: Long?,
        randomFlipping: Boolean?,
        withAnimation: Boolean
    ) {
        randomFlipping?.let {
            randomNumbersEnabled = it
        }

        if (randomNumbersEnabled ?: Defaults.randomNumbersEnabled) {
            flipDigitRandom?.setDigit(
                animateTo,
                numberStyle ?: numberStyle ?: Defaults.numberStyle,
                flips ?: numberOfFlips ?: Defaults.numberOfFlips,
                msPerFlip ?: animationSpeed ?: Defaults.msPerFlip,
                withAnimation
            )
        } else {
            flipDigitLinear?.setDigit(
                animateTo,
                numberStyle ?: numberStyle ?: Defaults.numberStyle,
                msPerFlip ?: animationSpeed ?: Defaults.msPerFlip,
                withAnimation
            )
        }
    }
}