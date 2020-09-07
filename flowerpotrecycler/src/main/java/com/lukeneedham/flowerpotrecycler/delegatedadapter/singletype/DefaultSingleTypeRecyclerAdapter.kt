package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.DefaultPositionDelegate

/** A [SingleTypeRecyclerAdapter] with default setup values */
abstract class DefaultSingleTypeRecyclerAdapter<ItemType : Any, ItemViewType>(
) : SingleTypeRecyclerAdapter<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override val featureDelegates: List<AdapterFeatureDelegate<ItemType>> = emptyList()

    override val positionDelegate: AdapterPositionDelegate<ItemType> by lazy {
        DefaultPositionDelegate.create<ItemType>(this)
    }
}
