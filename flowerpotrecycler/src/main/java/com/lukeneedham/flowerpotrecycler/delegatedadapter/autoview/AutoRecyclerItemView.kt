package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemBuilderBinder

/**
 * A [RecyclerItemView] which wraps around a provided [builderBinder]
 */
@SuppressLint("ViewConstructor")
class AutoRecyclerItemView<ItemType : Any> constructor(
    context: Context,
    private val builderBinder: ItemBuilderBinder<ItemType, View>
) : FrameLayout(context), RecyclerItemView<ItemType> {

    init {
        addView(builderBinder.createView(this))
    }

    override fun setItem(position: Int, item: ItemType) {
        builderBinder.bind(this, position, item)
    }
}
