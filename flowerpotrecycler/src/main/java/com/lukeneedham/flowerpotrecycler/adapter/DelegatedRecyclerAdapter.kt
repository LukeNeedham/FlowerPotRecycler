package com.lukeneedham.flowerpotrecycler.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate

/** A base RecyclerView Adapter to encourage a delegated approach */
abstract class DelegatedRecyclerAdapter<BaseItemType : Any, BaseItemViewType : View> :
    RecyclerView.Adapter<ViewHolder<BaseItemViewType>>() {

    abstract val featureDelegates: List<AdapterFeatureDelegate<BaseItemType, BaseItemViewType>>
    abstract val positionDelegate: AdapterPositionDelegate<BaseItemType>
    abstract val builderBinderRegistry: BuilderBinderRegistry<BaseItemType, BaseItemViewType>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<BaseItemViewType> {
        val view = builderBinderRegistry.build(parent, viewType)
        val viewHolder = ViewHolder(view)

        featureDelegates.forEach {
            it.onViewHolderCreated(parent, viewType, viewHolder, view)
        }

        return viewHolder
    }

    override fun getItemCount(): Int = positionDelegate.getItemCount()

    override fun onBindViewHolder(
        holder: ViewHolder<BaseItemViewType>,
        position: Int
    ) {
        val item = positionDelegate.getItemAt(position)
        val itemView = holder.typedItemView
        builderBinderRegistry.bind(holder, position, item)

        featureDelegates.forEach {
            it.onViewHolderBound(holder, position, itemView, item)
        }
        // Called after onViewHolderBound to override any click listener set on itemView incorrectly
        // during onViewHolderBound. For compatibility, all click handling must go through onItemClick
        itemView.setOnClickListener {
            featureDelegates.forEach {
                it.onItemClick(item, position, itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = positionDelegate.getItemAt(position)
        return builderBinderRegistry.getTypeId(item)
    }

    open fun submitList(newItems: List<BaseItemType>, onDiffDone: () -> Unit = {}) {
        builderBinderRegistry.assertItemsHandled(newItems)
        positionDelegate.submitList(newItems, onDiffDone)
    }
}
