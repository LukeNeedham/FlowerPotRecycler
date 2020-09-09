package com.lukeneedham.flowerpotrecyclersample.ui.feature.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import kotlinx.android.synthetic.main.view_flower_pot_item.view.*

class FlowerPotItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<FlowerPotModel> {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_flower_pot_item, this)
    }

    override fun setItem(position: Int, item: FlowerPotModel) {
        potImageView.setImageResource(item.imageResId)
        potNameTextView.setText(item.nameResId)
    }
}
