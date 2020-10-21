package com.lukeneedham.flowerpotrecyclersample.ui.feature.customadapter

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.MultipleSelectableItemDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation.LinearPositionDelegate
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.config.ItemTypeConfigListRegistry
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.config.ItemTypeConfigRegistry
import com.lukeneedham.flowerpotrecycler.util.ItemTypeConfigCreator
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
    private val selectableItemDelegate = MultipleSelectableItemDelegate(
        this,
        { itemView, item, isSelected ->
            // Here we specify how to handle each item view select state update.
            // This is redundant, as the default is just to call [View.setSelected] anyway.
            itemView.isSelected = isSelected
        }
    )

    override val itemTypeConfigRegistry: ItemTypeConfigRegistry<FlowerPotModel, FlowerPotItemView> =
        ItemTypeConfigListRegistry(
            listOf(
                ItemTypeConfigCreator.fromRecyclerItemView {
                    addItemLayoutParams(RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
                    addOnItemClickListener { item, _, _ -> onItemClick(item) }
                    addDelegate(selectableItemDelegate)
                }
            )
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
