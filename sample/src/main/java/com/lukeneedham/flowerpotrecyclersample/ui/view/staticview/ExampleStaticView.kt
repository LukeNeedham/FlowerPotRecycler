package com.lukeneedham.flowerpotrecyclersample.ui.view.staticview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.view_flower_pot_item.view.*

class ExampleStaticView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_flower_pot_item, this)
        potNameTextView.text = "Static text: A"
    }
}
