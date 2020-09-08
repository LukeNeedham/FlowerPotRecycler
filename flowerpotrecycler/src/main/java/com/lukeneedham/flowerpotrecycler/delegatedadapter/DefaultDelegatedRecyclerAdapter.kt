package com.lukeneedham.flowerpotrecycler.delegatedadapter

import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.DefaultPositionDelegate

/** A [DelegatedRecyclerAdapter] with default setup values */
abstract class DefaultDelegatedRecyclerAdapter<BaseItemType : Any> :
    DelegatedRecyclerAdapter<BaseItemType>() {

    override val featureDelegates: List<AdapterFeatureDelegate<BaseItemType>> = emptyList()

    override val positionDelegate: AdapterPositionDelegate<BaseItemType> by lazy {
        DefaultPositionDelegate.create<BaseItemType>(this)
    }
}
