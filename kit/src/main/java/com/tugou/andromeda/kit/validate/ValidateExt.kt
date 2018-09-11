@file:JvmName("ValidateKit")

package com.tugou.andromeda.kit.validate

import java.util.regex.Pattern

fun String?.assertPhoneNumber(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }

    return Pattern.matches("^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}\$", this)
}

fun String?.assertPassword(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }

    return this!!.trim().length in 6..20
}

fun String?.assertVerifyCode(): Boolean {
    return this?.trim()?.length == 6
}

fun String?.assertMailAddress(): Boolean {
    if (isNullOrEmpty()) {
        return false
    }
    return Pattern.matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", this)
}