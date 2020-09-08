package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.DefaultPositionDelegate

/** A [SingleTypeAdapter] with default setup values */
abstract class DefaultSingleTypeAdapter<ItemType : Any, ItemViewType : View>(
) : SingleTypeAdapter<ItemType, ItemViewType>() {

    override val featureDelegates: List<AdapterFeatureDelegate<ItemType>> = emptyList()

    override val positionDelegate: AdapterPositionDelegate<ItemType> by lazy {
        DefaultPositionDelegate.create<ItemType>(this)
    }
}
