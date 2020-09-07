package com.lukeneedham.flowerpotrecyclersample.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import kotlinx.android.synthetic.main.recycler_item_flower_pot.view.*

class FlowerPotItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<FlowerPotModel> {

    init {
        LayoutInflater.from(context).inflate(R.layout.recycler_item_flower_pot, this)
    }

    override fun setItem(position: Int, item: FlowerPotModel) {
        potImageView.setImageResource(item.imageResId)
        potNameTextView.setText(item.nameResId)
    }
}


class StringItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<String> {

    init {
        LayoutInflater.from(context).inflate(R.layout.recycler_item_flower_pot, this)
    }

    override fun setItem(position: Int, item: String) {
        potNameTextView.text = item
    }
}
