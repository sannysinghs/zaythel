package com.muslimlab.prayers.ui.product

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.muslimlab.prayers.R

import com.muslimlab.prayers.ui.product.dummy.DummyContent.ProductItem

/**
 * [RecyclerView.Adapter] that can display a [ProductItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CategoryRecyclerViewAdapter(
    private val values: List<ProductItem>
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.idView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.category_name)
        val imageView: ImageView = view.findViewById(R.id.category_image)

        override fun toString(): String {
            return super.toString()
        }
    }
}