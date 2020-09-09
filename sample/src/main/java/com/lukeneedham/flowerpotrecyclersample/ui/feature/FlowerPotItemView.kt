package com.lukeneedham.flowerpotrecyclersample.ui.feature

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import kotlinx.android.synthetic.main.view_flower_pot_item.view.*
import org.jetbrains.anko.textColor

class FlowerPotItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<FlowerPotModel> {

    private val unselectedTextColor = Color.BLACK

    init {
        LayoutInflater.from(context).inflate(R.layout.view_flower_pot_item, this)
    }

    override fun setItem(position: Int, item: FlowerPotModel) {
        potImageView.setImageResource(item.imageResId)
        potNameTextView.setText(item.nameResId)
        potNameTextView.setTextColor(unselectedTextColor)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        potNameTextView.textColor = if(selected) Color.GREEN else unselectedTextColor
    }
}
