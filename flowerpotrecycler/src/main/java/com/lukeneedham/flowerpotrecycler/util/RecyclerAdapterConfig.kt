package com.lukeneedham.flowerpotrecycler.util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.SingleTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.ItemLayoutParamsLazyDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.OnItemClickDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.CyclicPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate

/**
 * Makes the adapter cyclic / wraparound. See [CyclicPositionDelegate]
 * @param diffCallback an optional [DiffUtil.ItemCallback], used to calculate the diff when items change.
 * Defaults to [DefaultDiffCallback]
 */
fun <ItemType : Any> RecyclerAdapterConfig<ItemType>.setCyclic(
    diffCallback: DiffUtil.ItemCallback<ItemType> = DefaultDiffCallback()
) {
    positionDelegateCreator = { CyclicPositionDelegate(it, diffCallback) }
}

/**
 * Makes the adapter linear. See [LinearPositionDelegate]
 * @param diffCallback an optional [DiffUtil.ItemCallback], used to calculate the diff when items change.
 * Defaults to [DefaultDiffCallback]
 */
fun <ItemType : Any> RecyclerAdapterConfig<ItemType>.setLinear(
    diffCallback: DiffUtil.ItemCallback<ItemType> = DefaultDiffCallback()
) {
    positionDelegateCreator = { LinearPositionDelegate(it, diffCallback) }
}

fun <ItemType : Any> RecyclerAdapterConfig<ItemType>.addDelegate(
    delegate: AdapterFeatureDelegate<ItemType>
) {
    featureDelegateCreators.add { delegate }
}

fun <ItemType : Any> RecyclerAdapterConfig<ItemType>.addDelegate(
    delegateCreator: (adapter: SingleTypeRecyclerAdapter<ItemType, *>) -> AdapterFeatureDelegate<ItemType>
) {
    featureDelegateCreators.add(delegateCreator)
}

fun <ItemType : Any> RecyclerAdapterConfig<ItemType>.addOnItemClickListener(
    listener: (item: ItemType, position: Int) -> Unit
) {
    addDelegate(OnItemClickDelegate(listener))
}

fun RecyclerAdapterConfig<*>.addItemLayoutParams(
    layoutParams: RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsDelegate(layoutParams))
}

/**
 * [layoutParamsCreator] will be called exactly once, to create the layoutParams lazily,
 * when the first view is created.
 */
fun RecyclerAdapterConfig<*>.addItemLayoutParamsLazy(
    layoutParamsCreator: () -> RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsLazyDelegate(layoutParamsCreator))
}

fun <ItemType : Any> RecyclerAdapterConfig<ItemType>.getFeatureDelegates(
    adapter: SingleTypeRecyclerAdapter<ItemType, *>
): List<AdapterFeatureDelegate<ItemType>> = featureDelegateCreators.map { it.invoke(adapter) }
