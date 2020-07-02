package com.lukeneedham.flowerpotrecycler.extensions

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.BaseAdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.CyclicPositionDelegate

/** Makes the adapter cyclic / wraparound. See [CyclicPositionDelegate] */
fun <ItemType, ItemViewType> RecyclerAdapterConfig<ItemType, ItemViewType>.setCyclic()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
    positionDelegateCreator = { CyclicPositionDelegate(it, DefaultDiffCallback()) }
}

fun <ItemType, ItemViewType> RecyclerAdapterConfig<ItemType, ItemViewType>.addDelegate(
    delegate: AdapterFeatureDelegate<ItemType, ItemViewType>
) where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
    delegateCreators.add { delegate }
}

fun <ItemType, ItemViewType> RecyclerAdapterConfig<ItemType, ItemViewType>.addDelegate(
    delegateCreator: (adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>) -> AdapterFeatureDelegate<ItemType, ItemViewType>
) where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
    delegateCreators.add(delegateCreator)
}

fun <ItemType, ItemViewType> RecyclerAdapterConfig<ItemType, ItemViewType>.addOnItemClickListener(
    listener: (item: ItemType, position: Int) -> Unit
) where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
    addDelegate(object : BaseAdapterFeatureDelegate<ItemType, ItemViewType>() {
        override fun onItemClick(item: ItemType, position: Int) {
            listener(item, position)
        }
    })
}

fun <ItemType, ItemViewType> RecyclerAdapterConfig<ItemType, ItemViewType>.getDelegates(
    adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>
): List<AdapterFeatureDelegate<ItemType, ItemViewType>>
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
    delegateCreators.map { it.invoke(adapter) }
