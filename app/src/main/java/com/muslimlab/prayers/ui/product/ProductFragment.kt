package com.muslimlab.prayers.ui.product

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlab.prayers.R
import com.muslimlab.prayers.ui.product.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 */
class ProductFragment : Fragment() {

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        view.findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            this.addItemDecoration(
                ProductSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.feed_space))
            )
            adapter = ProductRecyclerViewAdapter(DummyContent.ITEMS)
        }


        view.findViewById<RecyclerView>(R.id.category_list).apply {
            this.addItemDecoration(
                CategorySpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.feed_space))
            )
            adapter = CategoryRecyclerViewAdapter(DummyContent.ITEMS)
        }

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    class ProductSpaceItemDecoration(
        val space: Int
    ) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (space >= 0) {
                outRect.left = space
                outRect.right = space
                outRect.bottom = space
                outRect.top = space
            }
        }
    }
}

class CategorySpaceItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (space >= 0) {
            outRect.right = space

            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = space
            }
        }
    }

}
