package com.simmorsal.smartnode.activities

import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import com.simmorsal.smartnode.R
import com.simmorsal.smartnode.adapters.AdapterMainItems
import com.simmorsal.smartnode.bases.BaseActivity
import com.simmorsal.smartnode.models.ModelMainItems
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.backgroundResource

class ActivityMain : BaseActivity() {

    private val lamps = 1
    private val outlets = 2
    private val switches = 3

    private var isAnimating = false
    private var currentTab = 0

    private var adapter: AdapterMainItems? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onClicks()
        setupRv(2, false)
    }

    private fun onClicks() {
        linLamps.setOnClickListener {
            if (!isAnimating) {
                setupRv(lamps, true)
                linLamps.backgroundDrawable = null
                linOutlets.backgroundResource = R.drawable.bg_tab_mid_off
                linSwitches.backgroundResource = R.drawable.bg_tab_left_off
            }
        }
        linOutlets.setOnClickListener {
            if (!isAnimating) {
                setupRv(outlets, true)
                linLamps.backgroundResource = R.drawable.bg_tab_right_off
                linOutlets.backgroundDrawable = null
                linSwitches.backgroundResource = R.drawable.bg_tab_left_off
            }
        }
        linSwitches.setOnClickListener {
            if (!isAnimating) {
                setupRv(switches, true)
                linLamps.backgroundResource = R.drawable.bg_tab_right_off
                linOutlets.backgroundResource = R.drawable.bg_tab_mid_off
                linSwitches.backgroundDrawable = null
            }
        }
    }

    private fun setupRv(set: Int, animate: Boolean) {
        if (set != currentTab) {
            currentTab = set
            if (adapter == null) {
                adapter = AdapterMainItems(this)
                rv.adapter = adapter
                rv.layoutManager = GridLayoutManager(this, 2)
                rv.isNestedScrollingEnabled = false
            }

            val delay = if (animate) 200 else 0
            if (animate)
                rv.animate().alpha(0f).setDuration(100).withStartAction { isAnimating = true }

            Handler().postDelayed({
                when (set) {
                    lamps -> {
                        adapter!!.setData(ModelMainItems.getLamps())
                    }
                    outlets -> {
                        adapter!!.setData(ModelMainItems.getOutlets())
                    }
                    switches -> {
                        adapter!!.setData(ModelMainItems.getSwitches())
                    }
                }

                if (animate)
                    rv.animate().alpha(1f).setDuration(200).withEndAction { isAnimating = false }
            }, delay.toLong())
        }
    }
}
