package de.rauschdo.flipdigit_lib

import androidx.annotation.DrawableRes

//TODO add more styles in the future

/**
 * Enum containing Lists of the Resources for different NumberStyles
 * List is structured in following way
 *      - listOf(
 *                img_zero_upper, img_zero_lower,
 *                img_one_upper, img_one_lower,
 *                ...
 *               )
 */
enum class NumberStyles(@DrawableRes val resourceList: List<Int>) {
    DEFAULT(
        listOf(
            R.drawable.digit_0_upper, R.drawable.digit_0_lower,
            R.drawable.digit_1_upper, R.drawable.digit_1_lower,
            R.drawable.digit_2_upper, R.drawable.digit_2_lower,
            R.drawable.digit_3_upper, R.drawable.digit_3_lower,
            R.drawable.digit_4_upper, R.drawable.digit_4_lower,
            R.drawable.digit_5_upper, R.drawable.digit_5_lower,
            R.drawable.digit_6_upper, R.drawable.digit_6_lower,
            R.drawable.digit_7_upper, R.drawable.digit_7_lower,
            R.drawable.digit_8_upper, R.drawable.digit_8_lower,
            R.drawable.digit_9_upper, R.drawable.digit_9_lower
        )
    )
}