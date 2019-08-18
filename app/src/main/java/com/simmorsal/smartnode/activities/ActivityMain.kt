package com.simmorsal.smartnode.activities

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.simmorsal.smartnode.R
import com.simmorsal.smartnode.adapters.AdapterMainItems
import com.simmorsal.smartnode.bases.BaseActivity
import com.simmorsal.smartnode.models.ModelMainItems
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : BaseActivity() {

    private val lamps = 1
    private val outlets = 2
    private val switches = 3

    private var adapter: AdapterMainItems? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRv(lamps)
    }

    private fun setupRv(set: Int) {
        if (adapter == null) {
            adapter = AdapterMainItems(this)
            rv.adapter = adapter
            rv.layoutManager = GridLayoutManager(this, 2)
            rv.isNestedScrollingEnabled = false
        }

        when (set) {
            lamps -> adapter!!.setData(ModelMainItems.getLamps())
            outlets -> adapter!!.setData(ModelMainItems.getOutlets())
            switches -> adapter!!.setData(ModelMainItems.getSwitches())
        }

        adapter!!.notifyDataSetChanged()
    }
}
