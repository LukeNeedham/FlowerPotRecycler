package com.lukeneedham.flowerpotrecycler.adapter

import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.DefaultPositionDelegate

/** A [DelegatedRecyclerAdapter] with default setup values */
abstract class DefaultDelegatedRecyclerAdapter<BaseItemType : Any> :
    DelegatedRecyclerAdapter<BaseItemType>() {

    override val featureDelegates: List<AdapterFeatureDelegate<BaseItemType>> = emptyList()

    override val positionDelegate: AdapterPositionDelegate<BaseItemType> by lazy {
        DefaultPositionDelegate.create<BaseItemType>(this)
    }
}
