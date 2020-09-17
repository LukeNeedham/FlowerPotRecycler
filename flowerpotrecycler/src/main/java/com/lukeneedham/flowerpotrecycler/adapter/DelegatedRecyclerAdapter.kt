package com.lukeneedham.flowerpotrecycler.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.itemtypedelegate.ItemTypeRegistry
import com.lukeneedham.flowerpotrecycler.adapter.itemtypedelegate.ItemTypeBuilder
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate

/** A base RecyclerView Adapter to encourage a delegated approach */
abstract class DelegatedRecyclerAdapter<BaseItemType : Any, BaseItemViewType : View> :
    RecyclerView.Adapter<ViewHolder<BaseItemViewType>>() {

    abstract val positionDelegate: AdapterPositionDelegate<BaseItemType>

    abstract val itemTypeBuilders:
            List<ItemTypeBuilder<out BaseItemType, out BaseItemViewType>>

    private val itemTypeRegistry: ItemTypeRegistry<BaseItemType, BaseItemViewType> by lazy {
        ItemTypeRegistry.create(itemTypeBuilders, this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<BaseItemViewType> {
        return itemTypeRegistry.build(parent, viewType)
    }

    override fun getItemCount(): Int = positionDelegate.getItemCount()

    override fun onBindViewHolder(
        holder: ViewHolder<BaseItemViewType>,
        position: Int
    ) {
        val item = positionDelegate.getItemAt(position)
        itemTypeRegistry.bind(holder, position, item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = positionDelegate.getItemAt(position)
        return itemTypeRegistry.getTypeId(item)
    }

    open fun submitList(newItems: List<BaseItemType>, onDiffDone: () -> Unit = {}) {
        itemTypeRegistry.assertItemsHandled(newItems)
        positionDelegate.submitList(newItems, onDiffDone)
    }
}
