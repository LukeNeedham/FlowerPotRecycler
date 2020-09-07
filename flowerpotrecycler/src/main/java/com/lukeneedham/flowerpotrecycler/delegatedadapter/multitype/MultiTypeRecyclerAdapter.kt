package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate

/** A base RecyclerView Adapter to encourage a delegated approach */
abstract class MultiTypeRecyclerAdapter<
        BaseItemType : Any,
        BaseViewType : View,
        ViewHolderType : TypedViewHolder<BaseViewType>
        > : RecyclerView.Adapter<ViewHolderType>() {

    abstract val featureDelegates: List<AdapterFeatureDelegate<BaseItemType>>
    abstract val positionDelegate: AdapterPositionDelegate<BaseItemType>
    abstract val viewTypesRegistry: ViewTypesRegistry<BaseItemType, BaseViewType>

    abstract fun createViewHolder(view: View): ViewHolderType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderType {
        val view = viewTypesRegistry.createView(parent.context, viewType)
        val viewHolder = createViewHolder(view)
        featureDelegates.forEach {
            it.onViewHolderCreated(viewHolder, parent, viewType)
        }
        return viewHolder
    }

    override fun getItemCount() = positionDelegate.getItemCount()

    override fun onBindViewHolder(
        holder: ViewHolderType,
        position: Int
    ) {
        val item = positionDelegate.getItemAt(position)
        val itemView = holder.itemView
        viewTypesRegistry.bind(holder, position, item)
        itemView.setOnClickListener {
            featureDelegates.forEach {
                it.onItemClick(item, position)
            }
        }
        featureDelegates.forEach {
            it.onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = positionDelegate.getItemAt(position)
        return viewTypesRegistry.getTypeId(item)
    }

    open fun submitList(newItems: List<BaseItemType>, onDiffDone: () -> Unit = {}) {
        positionDelegate.submitList(newItems, onDiffDone)
    }
}
