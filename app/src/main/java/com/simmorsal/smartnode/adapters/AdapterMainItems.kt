package com.simmorsal.smartnode.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.simmorsal.smartnode.R
import com.simmorsal.smartnode.models.ModelMainItems
import kotlinx.android.synthetic.main.rv_main_items.view.*


class AdapterMainItems(private val context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private lateinit var view: View
    private lateinit var data: List<ModelMainItems>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.rv_main_items, parent, false)
        return CellFeedViewHolder(view)
    }

    private inner class CellFeedViewHolder internal constructor(view: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    fun setData(data: List<ModelMainItems>) {
        this.data = data
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(
        viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        position: Int
    ) {
        val v = viewHolder.itemView

        Glide.with(v.image)
            .load(data[position].image)
            .into(v.image)

        v.title.text = data[position].title
    }
}
