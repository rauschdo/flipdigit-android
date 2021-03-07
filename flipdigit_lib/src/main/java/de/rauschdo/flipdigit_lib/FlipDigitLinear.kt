package de.rauschdo.flipdigit_lib

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.util.*

internal class FlipDigitLinear(
    private val context: Context,
    private val id: Int,
    view: View,
    private var animationSpeed: Long,
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

    private lateinit var numberStyle: NumberStyles

    init {
        imageBackUpper = view.findViewById(R.id.FlipMeterSpinner_image_back_upper)
        imageBackLower = view.findViewById(R.id.FlipMeterSpinner_image_back_lower)
        imageFrontUpper = view.findViewById(R.id.FlipMeterSpinner_image_front_upper)
        imageFrontLower = view.findViewById(R.id.FlipMeterSpinner_image_front_lower)

        init()
    }

    fun setDigit(digit: Int, style: NumberStyles, msPerFlip: Long, withAnimation: Boolean) {
        numberStyle = style
        animationSpeed = msPerFlip

        var mDigit = digit
        if (mDigit < 0)
            mDigit = 0
        if (mDigit > 9)
            mDigit = 9

        animateTo = mDigit

        if (withAnimation)
            animateDigit(msPerFlip)
        else
            setDigitImageToAll(mDigit)
    }

    private fun animateDigit(msPerFlip: Long) {
        animateFrom = getLastDigit()
        startAnimation(msPerFlip)
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

    private fun startAnimation(msPerFlip: Long) {
        if (animateTo == animateFrom) {
            onAnimComplete?.onComplete(id)
        } else {
            startDigitAnimation(msPerFlip, true)
            startDigitAnimation(msPerFlip, false)
        }
    }

    private fun startDigitAnimation(msPerFlip: Long, isUpper: Boolean) {
        if (isUpper) {
            animation1?.duration = msPerFlip
            imageFrontUpper?.clearAnimation()
            imageFrontUpper?.animation = animation1
            imageFrontUpper?.startAnimation(animation1)
        } else {
            animation2?.duration = msPerFlip
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
        val resource: Int =
            try {
                when (digitToShow) {
                    0 -> if (isUpper)
                        numberStyle.resourceList[0]
                    else
                        numberStyle.resourceList[1]
                    1 -> if (isUpper)
                        numberStyle.resourceList[2]
                    else
                        numberStyle.resourceList[3]
                    2 -> if (isUpper)
                        numberStyle.resourceList[4]
                    else
                        numberStyle.resourceList[5]
                    3 -> if (isUpper)
                        numberStyle.resourceList[6]
                    else
                        numberStyle.resourceList[7]
                    4 -> if (isUpper)
                        numberStyle.resourceList[8]
                    else
                        numberStyle.resourceList[9]
                    5 -> if (isUpper)
                        numberStyle.resourceList[10]
                    else
                        numberStyle.resourceList[11]
                    6 -> if (isUpper)
                        numberStyle.resourceList[12]
                    else
                        numberStyle.resourceList[13]
                    7 -> if (isUpper)
                        numberStyle.resourceList[14]
                    else
                        numberStyle.resourceList[15]
                    8 -> if (isUpper)
                        numberStyle.resourceList[16]
                    else
                        numberStyle.resourceList[17]
                    9 -> if (isUpper)
                        numberStyle.resourceList[18]
                    else
                        numberStyle.resourceList[19]
                    else -> R.drawable.resource_missing
                }
            } catch (exception: IndexOutOfBoundsException) {
                exception.printStackTrace()
                R.drawable.resource_missing
            } catch (exception: MissingResourceException) {
                exception.printStackTrace()
                R.drawable.resource_missing
            }
        image.setImageResource(resource)
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

    override fun onAnimationEnd(animation: Animation) {
        if (animation === animation1) {
            imageFrontUpper?.visibility = View.INVISIBLE
        } else if (animation === animation2) {
            imageFrontUpper?.visibility = View.VISIBLE
            imageFrontUpper?.let { setDigitImage(digitToShow, true, it) }
            imageBackLower?.let { setDigitImage(digitToShow, false, it) }

            incrementFromCode()
            animateDigit(animationSpeed)
        }
    }

    override fun onAnimationRepeat(arg0: Animation) {
        //nothing
    }

    interface OnAnimationComplete {
        fun onComplete(id: Int)
    }
}
