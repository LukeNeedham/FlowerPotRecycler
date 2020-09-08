package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder

/**
 * A [RecyclerItemView] which wraps around a provided [builderBinder]
 */
@SuppressLint("ViewConstructor")
class AutoRecyclerItemView<ItemType : Any> constructor(
    context: Context,
    private val builderBinder: BuilderBinder<ItemType, View>
) : FrameLayout(context), RecyclerItemView<ItemType> {

    init {
        addView(builderBinder.build(this))
    }

    override fun setItem(position: Int, item: ItemType) {
        builderBinder.bind(this, position, item)
    }
}
