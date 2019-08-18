package com.simmorsal.smartnode.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simmorsal.smartnode.R
import com.simmorsal.smartnode.interfaces.OnSplashAnimFinish
import kotlinx.android.synthetic.main.activity_splash.*

class ActivitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setFinishListener()
    }

    private fun setFinishListener() {
        splashView.onSplashAnimFinish = object : OnSplashAnimFinish {
            override fun onFinish() {
                overridePendingTransition(0, 0)
                finish()
            }
        }
    }
}
