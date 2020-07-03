package com.lukeneedham.flowerpotrecycler.extensions

import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.BaseAdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.CyclicPositionDelegate

/** Makes the adapter cyclic / wraparound. See [CyclicPositionDelegate] */
fun <ItemType> RecyclerAdapterConfig<ItemType>.setCyclic() {
    positionDelegateCreator = { CyclicPositionDelegate(it, DefaultDiffCallback()) }
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.addDelegate(
    delegate: AdapterFeatureDelegate<ItemType>
) {
    delegateCreators.add { delegate }
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.addDelegate(
    delegateCreator: (adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterFeatureDelegate<ItemType>
) {
    delegateCreators.add(delegateCreator)
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.addOnItemClickListener(
    listener: (item: ItemType, position: Int) -> Unit
) {
    addDelegate(object : BaseAdapterFeatureDelegate<ItemType>() {
        override fun onItemClick(item: ItemType, position: Int) {
            listener(item, position)
        }
    })
}

fun RecyclerAdapterConfig<*>.addItemLayoutParams(
    layoutParams: RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsDelegate(layoutParams))
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.getDelegates(
    adapter: DelegatedRecyclerAdapter<ItemType, *>
): List<AdapterFeatureDelegate<ItemType>> = delegateCreators.map { it.invoke(adapter) }
