[![Build Status](https://travis-ci.com/rauschdo/flipdigit-android.svg?branch=master)](https://travis-ci.com/rauschdo/flipdigit-android)
[![](https://jitpack.io/v/rauschdo/flipdigit-android.svg)](https://jitpack.io/#rauschdo/flipdigit-android)
[![API](https://img.shields.io/badge/API-14%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/rauschdo/flipdigit-android/blob/master/LICENSE)

# Flipdigit-Android
Android Library in Kotlin to implement Flipperclock like View.
Numbers inside the View are based on upper and lower Images.
To customize numbers need to implement library locally.

![Flipmeter-demo](/gif/meter.gif "Flipmeter demo")  ![Flipspinner-demo](/gif/spinners.gif "Individual Flipspinners demo")<br />
Inspired by https://github.com/Vinayrraj/Android-FlipDigitView

# Usage

## Add to project

Step 1: Add it in your root build.gradle at the end of repositories
```Gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Step 2: Add the dependency in your app build.gradle
```Gradle
dependencies {
    implementation 'com.github.rauschdo:flipdigit-android:1.0'
}
```

## Usage
There are two Views that can be implemented.
* Flipmeter - Pre-build View to display numbers until 999.999
* FlipmeterSpinner - Single FlipView to display one number (customizable AnimationSpeed and flippingMode)

### Initialize in XML
```xml
    <de.rauschdo.flipdigit_lib.Flipmeter
                android:id="@+id/flipMeter"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
```
or
```xml
    <de.rauschdo.flipdigit_lib.FlipmeterSpinner
                android:id="@+id/flipMeterSpinner"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:animSpeed="100"
                app:randomFlipping="true"/>
```

* One FlipmeterSpinner can only contain one number,
if you use multiple individual (Flipmeter)Spinner Views you need to assign each number to each view e.g.

MainActivity.kt
```java
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
```

### Attributes
* Only available for FlipmeterSpinner
(prebuild Flipmeter uses defaults as seen here)

|attr|format|default|description|
|---|:---|:---|:---:|
|animSpeed|integer|250|animation time in milliseconds|
|randomFlipping|boolean|false|view will display random numbers while flipping before locking to correct position instead of linear counting upwards|

## License
```
Copyright 2019 Dominik Rausch

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```