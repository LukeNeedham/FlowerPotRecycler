package com.lukeneedham.flowerpotrecycler.adapter

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.DefaultPositionDelegate

/** A [DelegatedRecyclerAdapter] with default setup values */
@Suppress("RemoveExplicitTypeArguments")
abstract class DefaultDelegatedRecyclerAdapter<BaseItemType : Any, BaseItemViewType : View> :
    DelegatedRecyclerAdapter<BaseItemType, BaseItemViewType>() {

    override val positionDelegate: AdapterPositionDelegate<BaseItemType> by lazy {
        DefaultPositionDelegate.create<BaseItemType>(this)
    }
}
