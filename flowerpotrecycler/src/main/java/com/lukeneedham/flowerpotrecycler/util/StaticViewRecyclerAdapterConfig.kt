package com.lukeneedham.flowerpotrecycler.util

import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.staticviewadapter.StaticViewRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.staticviewadapter.config.StaticViewRecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.StaticViewAdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.implementation.StaticViewLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.implementation.StaticViewLayoutParamsLazyDelegate
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.implementation.StaticViewOnClickDelegate

fun StaticViewRecyclerAdapterConfig.addDelegate(
    delegate: StaticViewAdapterFeatureDelegate
) {
    featureDelegateCreators.add { delegate }
}

fun StaticViewRecyclerAdapterConfig.addDelegate(
    delegateCreator: (adapter: StaticViewRecyclerAdapter) -> StaticViewAdapterFeatureDelegate
) {
    featureDelegateCreators.add(delegateCreator)
}

fun StaticViewRecyclerAdapterConfig.addOnClickListener(
    listener: () -> Unit
) {
    addDelegate(StaticViewOnClickDelegate(listener))
}

fun StaticViewRecyclerAdapterConfig.addItemLayoutParams(
    layoutParams: RecyclerView.LayoutParams
) {
    addDelegate(StaticViewLayoutParamsDelegate(layoutParams))
}

/**
 * [layoutParamsCreator] will be called exactly once, to create the layoutParams lazily,
 * when the first view is created.
 */
fun StaticViewRecyclerAdapterConfig.addItemLayoutParamsLazy(
    layoutParamsCreator: () -> RecyclerView.LayoutParams
) {
    addDelegate(StaticViewLayoutParamsLazyDelegate(layoutParamsCreator))
}

fun StaticViewRecyclerAdapterConfig.getFeatureDelegates(
    adapter: StaticViewRecyclerAdapter
): List<StaticViewAdapterFeatureDelegate> = featureDelegateCreators.map { it.invoke(adapter) }
