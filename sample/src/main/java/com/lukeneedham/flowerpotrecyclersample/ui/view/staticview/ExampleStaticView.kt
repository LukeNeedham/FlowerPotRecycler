package com.lukeneedham.flowerpotrecyclersample.ui.view.staticview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.lukeneedham.flowerpotrecyclersample.R

class ExampleStaticView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        val textView = TextView(context).apply {
            text = context.getString(R.string.app_name)
            textSize = 20f
        }
        addView(textView)
    }
}
