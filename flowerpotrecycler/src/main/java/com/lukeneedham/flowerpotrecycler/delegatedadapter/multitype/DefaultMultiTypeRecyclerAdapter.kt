package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.DefaultPositionDelegate

/** A [MultiTypeRecyclerAdapter] with default setup values */
abstract class DefaultMultiTypeRecyclerAdapter<BaseItemType : Any, BaseViewType : View> :
    MultiTypeRecyclerAdapter<BaseItemType, BaseViewType, TypedViewHolder<BaseViewType>>() {

    override val featureDelegates: List<AdapterFeatureDelegate<BaseItemType>> = emptyList()

    override val positionDelegate: AdapterPositionDelegate<BaseItemType> by lazy {
        DefaultPositionDelegate.create(this)
    }

    override fun createViewHolder(view: BaseViewType): TypedViewHolder<BaseViewType> {
        return TypedViewHolder(view)
    }
}
