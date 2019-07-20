package de.rauschdo.flipdigit_lib

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.util.*

internal class FlipDigitRandom(
    private val context: Context,
    private val id: Int,
    view: View,
    private val animationSpeed: Long,
    private val onAnimComplete: OnAnimationComplete?
) : AnimationListener {

    private var imageBackUpper: ImageView? = null
    private var imageBackLower: ImageView? = null

    private var imageFrontUpper: ImageView? = null
    private var imageFrontLower: ImageView? = null

    private var animation1: Animation? = null
    private var animation2: Animation? = null

    private var randomDigitsListIndex = 0
    private var randomDigitsList: MutableList<Int>? = null

    private val lastDigit: Int
        get() {
            var digit = 0
            try {
                digit = imageFrontUpper?.tag as Int
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (digit > 9)
                digit = 0

            return digit
        }

    private val digitToShow: Int?
        get() = randomDigitsList?.get(randomDigitsListIndex + 1)

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

        val lastUpperDigit = lastDigit

        randomDigitsList = ArrayList()
        randomDigitsList?.add(0)
        randomDigitsList?.add(1)
        randomDigitsList?.add(2)
        randomDigitsList?.add(3)
        randomDigitsList?.add(4)
        randomDigitsList?.add(5)
        randomDigitsList?.add(6)
        randomDigitsList?.add(7)
        randomDigitsList?.add(8)
        randomDigitsList?.add(9)
        randomDigitsList?.removeAt(lastUpperDigit)
        randomDigitsList?.shuffle()
        randomDigitsList?.add(0, lastUpperDigit)
        randomDigitsList?.add(mDigit)

        randomDigitsListIndex = 0

        if (withAnimation)
            startAnimation()
        else
            setDigitImageToAll(mDigit)
    }

    private fun init() {
        imageBackUpper?.tag = 0
        imageBackLower?.tag = 0
        imageFrontUpper?.tag = 0
        imageFrontLower?.tag = 0

        animation1 = AnimationUtils.loadAnimation(context, R.anim.flip_point_to_middle)
        animation1?.duration = animationSpeed
        animation1?.setAnimationListener(this)
        animation2 = AnimationUtils.loadAnimation(context, R.anim.flip_point_from_middle)
        animation2?.duration = animationSpeed
        animation2?.setAnimationListener(this)
    }

    private fun startAnimation() {

        if (randomDigitsListIndex > 9) {
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
        when {
            randomDigitsListIndex < 0 -> randomDigitsListIndex = 0
            randomDigitsListIndex > randomDigitsList!!.size -> randomDigitsListIndex = randomDigitsList!!.size - 1
            else -> randomDigitsListIndex++
        }
    }

    private fun setDigitImageToAll(digit: Int) {
        imageBackUpper?.let { setDigitImage(digit, true, it) }
        imageBackLower?.let { setDigitImage(digit, false, it) }
        imageFrontUpper?.let { setDigitImage(digit, true, it) }
        imageFrontLower?.let { setDigitImage(digit, false, it) }
    }

    private fun setDigitImage(digitToShow: Int, isUpper: Boolean, image: ImageView) {
        image.tag = digitToShow
        val resource: Int = when (digitToShow) {
            0 -> if (isUpper)
                R.drawable.digit_0_upper
            else
                R.drawable.digit_0_lower

            1 -> if (isUpper)
                R.drawable.digit_1_upper
            else
                R.drawable.digit_1_lower

            2 -> if (isUpper)
                R.drawable.digit_2_upper
            else
                R.drawable.digit_2_lower

            3 -> if (isUpper)
                R.drawable.digit_3_upper
            else
                R.drawable.digit_3_lower

            4 -> if (isUpper)
                R.drawable.digit_4_upper
            else
                R.drawable.digit_4_lower

            5 -> if (isUpper)
                R.drawable.digit_5_upper
            else
                R.drawable.digit_5_lower

            6 -> if (isUpper)
                R.drawable.digit_6_upper
            else
                R.drawable.digit_6_lower

            7 -> if (isUpper)
                R.drawable.digit_7_upper
            else
                R.drawable.digit_7_lower

            8 -> if (isUpper)
                R.drawable.digit_8_upper
            else
                R.drawable.digit_8_lower

            9 -> if (isUpper)
                R.drawable.digit_9_upper
            else
                R.drawable.digit_9_lower
            else -> -1
        }
        image.setImageResource(resource)
    }

    override fun onAnimationEnd(animation: Animation) {
        if (animation === animation1) {
            imageFrontUpper?.visibility = View.INVISIBLE
        } else if (animation === animation2) {

            setDigitImage(digitToShow!!, true, imageFrontUpper!!)
            setDigitImage(digitToShow!!, false, imageBackLower!!)
            imageFrontUpper?.visibility = View.VISIBLE
            incrementFromCode()
            startAnimation()

        }
    }

    override fun onAnimationRepeat(arg0: Animation) {
        //nothing
    }

    override fun onAnimationStart(animation: Animation) {
        if (animation === animation1) {
            imageFrontLower?.visibility = View.INVISIBLE

            setDigitImage(digitToShow!!, false, imageFrontLower!!)
            setDigitImage(digitToShow!!, true, imageBackUpper!!)

        } else if (animation === animation2) {
            imageFrontLower?.visibility = View.VISIBLE
        }
    }

    interface OnAnimationComplete {
        fun onComplete(id: Int)
    }
}
