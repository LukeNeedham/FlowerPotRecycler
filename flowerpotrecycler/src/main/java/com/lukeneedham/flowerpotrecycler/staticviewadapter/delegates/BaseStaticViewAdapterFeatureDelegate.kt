package com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates

import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.staticviewadapter.StaticViewRecyclerViewHolder

/** Implements the methods of [StaticViewAdapterFeatureDelegate] with NO-OP */
abstract class BaseStaticViewAdapterFeatureDelegate : StaticViewAdapterFeatureDelegate {
    override fun onViewHolderCreated(
        viewHolder: StaticViewRecyclerViewHolder,
        parent: ViewGroup,
        viewType: Int
    ) {
    }

    override fun onClick() {
    }
}
