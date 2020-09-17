package com.lukeneedham.flowerpotrecyclersample.ui.feature.customadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.itemtypedelegate.ItemTypeBuilder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.SelectableItemDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation.LinearPositionDelegate
import com.lukeneedham.flowerpotrecycler.util.extensions.addDelegate
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import com.lukeneedham.flowerpotrecyclersample.ui.feature.FlowerPotItemView

/**
 * A demo of extending [DelegatedRecyclerAdapter] to add your own functionality.
 * In this case, adding a [selectableItemDelegate].
 *
 * You can also override [DefaultDelegatedRecyclerAdapter], which comes with defaults,
 * so you don't need to override [featureDelegates] and [positionDelegate]
 */
class CustomAdapter(
    onItemClick: (FlowerPotModel) -> Unit
) : DelegatedRecyclerAdapter<FlowerPotModel, FlowerPotItemView>() {
    private val selectableItemDelegate =
        SelectableItemDelegate(this) { itemView, item, isSelected ->
            // Here we specify how to handle each item view select state update.
            // This is redundant, as the default is just to call [View.setSelected] anyway.
            itemView.isSelected = isSelected
        }

    override val itemTypeBuilders: List<ItemTypeBuilder<FlowerPotModel, FlowerPotItemView>> =
        listOf(
            ItemTypeBuilder.from(RecyclerItemViewBuilderBinder.create()) {
                addItemLayoutParams(
                    RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                addOnItemClickListener { item, _, _ -> onItemClick(item) }
                addDelegate(selectableItemDelegate)
            }
        )

    override val positionDelegate: AdapterPositionDelegate<FlowerPotModel> =
        LinearPositionDelegate(this, DefaultDiffCallback())

    override fun onFailedToRecycleView(holder: ViewHolder<FlowerPotItemView>): Boolean {
        return super.onFailedToRecycleView(holder)
        // Since we've subclassed RecyclerView we can override all its methods
        // Maybe we want to do something here, for example
    }

    fun selectItem(item: FlowerPotModel) {
        selectableItemDelegate.selectItem(item)
    }
}
