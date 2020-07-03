package com.lukeneedham.flowerpotrecycler.util

import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.OnItemClickDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.CyclicPositionDelegate

/** Makes the adapter cyclic / wraparound. See [CyclicPositionDelegate] */
fun <ItemType> RecyclerAdapterConfig<ItemType>.setCyclic() {
    positionDelegateCreator = { CyclicPositionDelegate(it, DefaultDiffCallback()) }
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.addDelegate(
    delegate: AdapterFeatureDelegate<ItemType>
) {
    featureDelegateCreators.add { delegate }
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.addDelegate(
    delegateCreator: (adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterFeatureDelegate<ItemType>
) {
    featureDelegateCreators.add(delegateCreator)
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.addOnItemClickListener(
    listener: (item: ItemType, position: Int) -> Unit
) {
    addDelegate(OnItemClickDelegate(listener))
}

fun RecyclerAdapterConfig<*>.addItemLayoutParams(
    layoutParams: RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsDelegate(layoutParams))
}

fun <ItemType> RecyclerAdapterConfig<ItemType>.getFeatureDelegates(
    adapter: DelegatedRecyclerAdapter<ItemType, *>
): List<AdapterFeatureDelegate<ItemType>> = featureDelegateCreators.map { it.invoke(adapter) }
