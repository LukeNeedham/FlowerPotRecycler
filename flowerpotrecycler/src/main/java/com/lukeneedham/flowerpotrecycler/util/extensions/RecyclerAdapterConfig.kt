@file:Suppress("unused")

package com.lukeneedham.flowerpotrecycler.util.extensions

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.ItemLayoutParamsLazyDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.OnItemClickDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation.CyclicPositionDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation.LinearPositionDelegate

/**
 * Makes the adapter cyclic / wraparound. See [CyclicPositionDelegate]
 * @param diffCallback an optional [DiffUtil.ItemCallback], used to calculate the diff when items change.
 * Defaults to [DefaultDiffCallback]
 */
fun <ItemType : Any> RecyclerAdapterConfig<ItemType, *>.setCyclic(
    diffCallback: DiffUtil.ItemCallback<ItemType> = DefaultDiffCallback()
) {
    positionDelegateCreator = { CyclicPositionDelegate(it, diffCallback) }
}

/**
 * Makes the adapter linear. See [LinearPositionDelegate]
 * @param diffCallback an optional [DiffUtil.ItemCallback], used to calculate the diff when items change.
 * Defaults to [DefaultDiffCallback]
 */
fun <ItemType : Any> RecyclerAdapterConfig<ItemType, *>.setLinear(
    diffCallback: DiffUtil.ItemCallback<ItemType> = DefaultDiffCallback()
) {
    positionDelegateCreator = { LinearPositionDelegate(it, diffCallback) }
}

/**
 * Overrides the value of [RecyclerAdapterConfig.items] to instead use [numberOfItems] dummy items.
 * The dummy items are simply [Unit].
 * As such, it is your responsibility to ensure that your adapter can handle items of type [Unit].
 */
fun RecyclerAdapterConfig<Unit, *>.useDummyItems(numberOfItems: Int) {
    items = List(numberOfItems) { Unit }
}

fun <ItemType : Any, ItemViewType : View> RecyclerAdapterConfig<ItemType, ItemViewType>.addDelegate(
    delegate: AdapterFeatureDelegate<ItemType, ItemViewType>
) {
    featureDelegateCreators.add { delegate }
}

fun <ItemType : Any, ItemViewType : View> RecyclerAdapterConfig<ItemType, ItemViewType>.addDelegate(
    delegateCreator: (adapter: RecyclerView.Adapter<*>) -> AdapterFeatureDelegate<ItemType, ItemViewType>
) {
    featureDelegateCreators.add(delegateCreator)
}

fun <ItemType : Any, ItemViewType : View> RecyclerAdapterConfig<ItemType, ItemViewType>.addOnItemClickListener(
    listener: (item: ItemType, position: Int) -> Unit
) {
    addDelegate(OnItemClickDelegate(listener))
}

fun <ItemType : Any, ItemViewType : View> RecyclerAdapterConfig<ItemType, ItemViewType>.addItemLayoutParams(
    layoutParams: RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsDelegate(layoutParams))
}

/**
 * [layoutParamsCreator] will be called exactly once, to create the layoutParams lazily,
 * when the first view is created.
 */
fun <ItemType : Any, ItemViewType : View> RecyclerAdapterConfig<ItemType, ItemViewType>.addItemLayoutParamsLazy(
    layoutParamsCreator: () -> RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsLazyDelegate(layoutParamsCreator))
}

fun <ItemType : Any, ItemViewType : View> RecyclerAdapterConfig<ItemType, ItemViewType>.getFeatureDelegates(
    adapter: RecyclerView.Adapter<*>
): List<AdapterFeatureDelegate<ItemType, ItemViewType>> = featureDelegateCreators.map { it.invoke(adapter) }
