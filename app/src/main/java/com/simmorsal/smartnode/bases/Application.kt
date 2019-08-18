package com.simmorsal.smartnode.bases

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        // calligraphy
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran_sans.ttf")
//                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}