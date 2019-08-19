package com.simmorsal.smartnode.utils

import android.app.Activity
import android.view.WindowManager

fun setNoLimit(act: Activity) {
    act.window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}