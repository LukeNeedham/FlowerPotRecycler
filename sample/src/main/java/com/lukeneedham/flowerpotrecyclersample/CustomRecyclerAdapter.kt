package com.lukeneedham.flowerpotrecyclersample

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.TypedRecyclerViewHolder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.OnItemClickDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.SelectableItemDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate

/**
 * A demo of extending [DefaultDelegatedRecyclerAdapter] to add your own functionality.
 * In this case, adding [selectableItemDelegate].
 *
 * You can also override [DelegatedRecyclerAdapter], which comes without the defaults,
 * so requires you to override [featureDelegates] and [positionDelegate]
 */
class CustomRecyclerAdapter(
    onItemClick: (FlowerPotModel) -> Unit
) : DefaultDelegatedRecyclerAdapter<FlowerPotModel, FlowerPotItemView>() {
    private val selectableItemDelegate = SelectableItemDelegate(this)

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