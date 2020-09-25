package com.lukeneedham.flowerpotrecycler.util.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.ItemLayoutParamsLazyDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation.OnItemClickDelegate

fun <ItemType, ItemViewType : View> FeatureDelegateConfig<ItemType, ItemViewType>.addDelegate(
    delegate: AdapterFeatureDelegate<ItemType, ItemViewType>
) {
    delegateCreators.add { delegate }
}

fun <ItemType, ItemViewType : View> FeatureDelegateConfig<ItemType, ItemViewType>.addDelegate(
    delegateCreator: (adapter: RecyclerView.Adapter<*>) -> AdapterFeatureDelegate<ItemType, ItemViewType>
) {
    delegateCreators.add(delegateCreator)
}

fun <ItemType, ItemViewType : View> FeatureDelegateConfig<ItemType, ItemViewType>.addOnItemClickListener(
    listener: (item: ItemType, position: Int, itemView: ItemViewType) -> Unit
) {
    addDelegate(OnItemClickDelegate(listener))
}

fun <ItemType, ItemViewType : View> FeatureDelegateConfig<ItemType, ItemViewType>.addItemLayoutParams(
    layoutParams: RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsDelegate(layoutParams))
}

/**
 * [layoutParamsCreator] will be called exactly once, to create the layoutParams lazily,
 * when the first view is created.
 */
fun <ItemType, ItemViewType : View> FeatureDelegateConfig<ItemType, ItemViewType>.addItemLayoutParamsLazy(
    layoutParamsCreator: () -> RecyclerView.LayoutParams
) {
    addDelegate(ItemLayoutParamsLazyDelegate(layoutParamsCreator))
}

fun <ItemType, ItemViewType : View> FeatureDelegateConfig<ItemType, ItemViewType>.getFeatureDelegates(
    adapter: RecyclerView.Adapter<*>
): List<AdapterFeatureDelegate<ItemType, ItemViewType>> =
    delegateCreators.map { it(adapter) }
