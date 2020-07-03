package com.lukeneedham.flowerpotrecycler.delegatedadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate

/** A base RecyclerView Adapter to encourage a delegated approach */
abstract class DelegatedRecyclerAdapter<ItemType, ItemViewType> :
    RecyclerView.Adapter<TypedRecyclerViewHolder<ItemType, ItemViewType>>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    abstract val featureDelegates: List<AdapterFeatureDelegate<ItemType>>
    abstract val positionDelegate: AdapterPositionDelegate<ItemType>

    protected abstract fun createItemView(context: Context): ItemViewType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypedRecyclerViewHolder<ItemType, ItemViewType> {
        val view = createItemView(parent.context)
        val viewHolder = TypedRecyclerViewHolder(view)
        featureDelegates.forEach {
            it.onViewHolderCreated(viewHolder, parent, viewType)
        }
        return viewHolder
    }

    override fun getItemCount() = positionDelegate.getItemCount()

    override fun onBindViewHolder(
        holder: TypedRecyclerViewHolder<ItemType, ItemViewType>,
        position: Int
    ) {
        val item = positionDelegate.getItemAt(position)
        val itemView = holder.typedItemView
        itemView.setItem(position, item)
        itemView.setOnClickListener {
            featureDelegates.forEach {
                it.onItemClick(item, position)
            }
        }
        featureDelegates.forEach {
            it.onBindViewHolder(holder, position)
        }
    }

    open fun submitList(newItems: List<ItemType>, onDiffDone: () -> Unit = {}) {
        positionDelegate.submitList(newItems, onDiffDone)
    }
}