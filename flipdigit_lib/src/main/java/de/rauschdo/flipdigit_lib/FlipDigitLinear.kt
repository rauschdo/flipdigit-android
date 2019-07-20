package de.rauschdo.flipdigit_lib

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

internal class FlipDigitLinear(
    private val context: Context,
    private val id: Int,
    view: View,
    private val animationSpeed: Long,
    private val onAnimComplete: OnAnimationComplete?
) : Animation.AnimationListener {

    private var imageBackUpper: ImageView? = null
    private var imageBackLower: ImageView? = null

    private var imageFrontUpper: ImageView? = null
    private var imageFrontLower: ImageView? = null

    private var animation1: Animation? = null
    private var animation2: Animation? = null

    private var animateTo = 0
    private var animateFrom = 0

    private val digitToShow: Int
        get() = if (animateFrom + 1 > 9)
            0
        else
            animateFrom + 1

    init {
        imageBackUpper = view.findViewById(R.id.FlipMeterSpinner_image_back_upper)
        imageBackLower = view.findViewById(R.id.FlipMeterSpinner_image_back_lower)
        imageFrontUpper = view.findViewById(R.id.FlipMeterSpinner_image_front_upper)
        imageFrontLower = view.findViewById(R.id.FlipMeterSpinner_image_front_lower)

        init()
    }

    fun setDigit(digit: Int, withAnimation: Boolean) {
        var mDigit = digit
        if (mDigit < 0)
            mDigit = 0
        if (mDigit > 9)
            mDigit = 9


        animateTo = mDigit

        if (withAnimation)
            animateDigit()
        else
            setDigitImageToAll(mDigit)

    }

    private fun animateDigit() {
        animateFrom = getLastDigit()
        startAnimation()
    }

    private fun init() {
        imageBackUpper?.tag = 0
        imageBackLower?.tag = 0
        imageFrontUpper?.tag = 0
        imageFrontLower?.tag = 0

        //Top to Middle
        animation1 = AnimationUtils.loadAnimation(context, R.anim.flip_point_to_middle)
        animation1?.duration = animationSpeed
        animation1?.setAnimationListener(this)

        //Middle to Bottom
        animation2 = AnimationUtils.loadAnimation(context, R.anim.flip_point_from_middle)
        animation2?.duration = animationSpeed
        animation2?.setAnimationListener(this)
    }

    private fun startAnimation() {
        if (animateTo == animateFrom) {
            onAnimComplete?.onComplete(id)
        } else {
            startDigitAnimation(true)
            startDigitAnimation(false)
        }
    }

    private fun startDigitAnimation(isUpper: Boolean) {
        if (isUpper) {
            imageFrontUpper?.clearAnimation()
            imageFrontUpper?.animation = animation1
            imageFrontUpper?.startAnimation(animation1)
        } else {
            imageFrontLower?.clearAnimation()
            imageFrontLower?.animation = animation2
            imageFrontLower?.startAnimation(animation2)
        }
    }

    private fun incrementFromCode() {
        if (animateFrom < 0)
            animateFrom = 0

        if (animateFrom > 9)
            animateFrom = 9

        animateFrom++
    }

    private fun getLastDigit(): Int {
        var digit = 0
        try {
            digit = (imageFrontUpper?.tag ?: 0) as Int
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (digit > 9)
            digit = 0

        return digit
    }

    private fun setDigitImageToAll(digit: Int) {
        imageBackUpper?.let { setDigitImage(digit, true, it) }
        imageBackLower?.let { setDigitImage(digit, false, it) }
        imageFrontUpper?.let { setDigitImage(digit, true, it) }
        imageFrontLower?.let { setDigitImage(digit, false, it) }
    }

    private fun setDigitImage(digitToShow: Int, isUpper: Boolean, image: ImageView) {
        image.tag = digitToShow
        var resource = 0

        when (digitToShow) {
            0 -> resource = if (isUpper)
                R.drawable.digit_0_upper
            else
                R.drawable.digit_0_lower

            1 -> resource = if (isUpper)
                R.drawable.digit_1_upper
            else
                R.drawable.digit_1_lower

            2 -> resource = if (isUpper)
                R.drawable.digit_2_upper
            else
                R.drawable.digit_2_lower

            3 -> resource = if (isUpper)
                R.drawable.digit_3_upper
            else
                R.drawable.digit_3_lower

            4 -> resource = if (isUpper)
                R.drawable.digit_4_upper
            else
                R.drawable.digit_4_lower

            5 -> resource = if (isUpper)
                R.drawable.digit_5_upper
            else
                R.drawable.digit_5_lower

            6 -> resource = if (isUpper)
                R.drawable.digit_6_upper
            else
                R.drawable.digit_6_lower

            7 -> resource = if (isUpper)
                R.drawable.digit_7_upper
            else
                R.drawable.digit_7_lower

            8 -> resource = if (isUpper)
                R.drawable.digit_8_upper
            else
                R.drawable.digit_8_lower

            9 -> resource = if (isUpper)
                R.drawable.digit_9_upper
            else
                R.drawable.digit_9_lower
        }
        image.setImageResource(resource)
    }

    override fun onAnimationEnd(animation: Animation) {
        if (animation === animation1) {
            imageFrontUpper?.visibility = View.INVISIBLE
        } else if (animation === animation2) {
            imageFrontUpper?.visibility = View.VISIBLE
            imageFrontUpper?.let { setDigitImage(digitToShow, true, it) }
            imageBackLower?.let { setDigitImage(digitToShow, false, it) }

            incrementFromCode()
            animateDigit()
        }
    }

    override fun onAnimationRepeat(arg0: Animation) {
        //nothing
    }

    override fun onAnimationStart(animation: Animation) {

        if (animation === animation1) {
            imageFrontLower?.visibility = View.INVISIBLE
            imageFrontUpper?.visibility = View.VISIBLE

            imageFrontLower?.let { setDigitImage(digitToShow, false, it) }
            imageBackUpper?.let { setDigitImage(digitToShow, true, it) }

        } else if (animation === animation2) {
            imageFrontLower?.visibility = View.VISIBLE
        }
    }

    interface OnAnimationComplete {
        fun onComplete(id: Int)
    }
}