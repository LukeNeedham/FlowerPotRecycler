package com.lukeneedham.flowerpotrecyclersample.ui.feature.view.multitype

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.view_flower_pot_item.view.*

class IntItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<Int> {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_flower_pot_item, this)
    }

    override fun setItem(position: Int, item: Int) {
        potNameTextView.text = item.toString()
    }
}
