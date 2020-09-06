package com.lukeneedham.flowerpotrecyclersample.ui.view

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultSingleTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.SingleTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.TypedRecyclerViewHolder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.OnItemClickDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.SelectableItemDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel

/**
 * A demo of extending [DefaultSingleTypeRecyclerAdapter] to add your own functionality.
 * In this case, adding [selectableItemDelegate].
 *
 * You can also override [SingleTypeRecyclerAdapter], which comes without the defaults,
 * so requires you to override [featureDelegates] and [positionDelegate]
 */
class CustomRecyclerAdapter(
    onItemClick: (FlowerPotModel) -> Unit
) : DefaultSingleTypeRecyclerAdapter<FlowerPotModel, FlowerPotItemView>() {
    private val selectableItemDelegate = SelectableItemDelegate(this)

    override val itemClass = FlowerPotModel::class

    // Optional override
    override val featureDelegates: List<AdapterFeatureDelegate<FlowerPotModel>> = listOf(
        ItemLayoutParamsDelegate(
            RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        ),
        OnItemClickDelegate { item, position -> onItemClick(item) },
        selectableItemDelegate
    )

    // Optional override
    override val positionDelegate: AdapterPositionDelegate<FlowerPotModel> =
        LinearPositionDelegate(this, DefaultDiffCallback())

    override fun createItemView(context: Context) = FlowerPotItemView(context)

    override fun onFailedToRecycleView(holder: TypedRecyclerViewHolder<FlowerPotModel, FlowerPotItemView>): Boolean {
        return super.onFailedToRecycleView(holder)
        // Since we've subclassed RecyclerView we can override all its methods
        // Maybe we want to do something here, for example
    }

    fun selectItem(item: FlowerPotModel) {
        selectableItemDelegate.selectItem(item)
    }
}
