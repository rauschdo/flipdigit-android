package de.rauschdo.flipdigit_lib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

class FlipmeterSpinner : RelativeLayout {

    private var mContext: Context? = null

    private var flipmeterSpinner: View? = null

    //val currentDigit: Int = 0

    private var flipDigitRandom: FlipDigitRandom? = null

    private var flipDigitLinear: FlipDigitLinear? = null

    private var animationSpeed = 250L

    var randomNumbersEnabled: Boolean? = null

    /*
     * Simple constructor used when creating a view from code.
     */
    constructor(context: Context) : super(context) {
        this.mContext = context
        initialize(context)
    }

    /*
     * This is called when a view is being constructed from an XML file, supplying attributes that were specified in the
     * XML file.
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
        initialize(context, attrs)
    }

    /*
     * Perform inflation from XML and apply a class-specific base style. This constructor of View allows subclasses to
     * use their own base style when they are inflating.
     */
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        this.mContext = context
        initialize(context, attrs)
    }

    private fun inflateLayout() {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        flipmeterSpinner = layoutInflater.inflate(R.layout.view_flipmeter_spinner, this)
    }

    fun setDigit(animateTo: Int, withAnimation: Boolean) {
        if (randomNumbersEnabled == true) {
            flipDigitRandom?.setDigit(animateTo, withAnimation)
        } else {
            flipDigitLinear?.setDigit(animateTo, withAnimation)
        }
    }

    /*
     * Initialize all of our class members and variables
     */
    private fun initialize(context: Context, attrs: AttributeSet? = null) {
        if (randomNumbersEnabled == null) {
            if (attrs != null) {
                context.obtainStyledAttributes(attrs, R.styleable.FlipmeterSpinner).apply {
                    animationSpeed = getInt(R.styleable.FlipmeterSpinner_animSpeed, 250).toLong()
                    randomNumbersEnabled = getBoolean(R.styleable.FlipmeterSpinner_randomFlipping, false)
                    recycle()
                }
            }
        }

        inflateLayout()
        if (randomNumbersEnabled == true) {
            flipDigitRandom = flipmeterSpinner?.let { FlipDigitRandom(context, id, it, animationSpeed, null) }
        } else {
            flipDigitLinear = flipmeterSpinner?.let { FlipDigitLinear(context, id, it, animationSpeed, null) }
        }
    }
}