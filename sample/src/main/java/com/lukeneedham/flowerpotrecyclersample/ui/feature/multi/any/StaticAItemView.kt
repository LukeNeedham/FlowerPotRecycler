package com.lukeneedham.flowerpotrecyclersample.ui.feature.multi.any

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.view_flower_pot_item.view.*

class StaticAItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_flower_pot_item, this)
        potNameTextView.text = "Static text: A"
    }
}
