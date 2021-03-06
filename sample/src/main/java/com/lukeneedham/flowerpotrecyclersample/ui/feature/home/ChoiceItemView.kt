package com.lukeneedham.flowerpotrecyclersample.ui.feature.home

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.view_choice_item.view.*

class ChoiceItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<ChoiceItem> {
    init {
        View.inflate(context, R.layout.view_choice_item, this)
    }

    override fun setItem(position: Int, item: ChoiceItem) {
        textView.text = item.text
    }
}
