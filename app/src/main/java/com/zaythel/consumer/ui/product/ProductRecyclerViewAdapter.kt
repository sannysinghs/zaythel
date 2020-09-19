package com.zaythel.consumer.ui.product

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zaythel.consumer.R

import com.zaythel.consumer.model.dummy.DummyContent.ProductItem

/**
 * [RecyclerView.Adapter] that can display a [ProductItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ProductRecyclerViewAdapter(
    private val values: List<ProductItem>
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.idView.text = item.name
        holder.price.text = item.price
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.category_name)
        val price: TextView = view.findViewById(R.id.item_price)

        override fun toString(): String {
            return super.toString()
        }
    }
}