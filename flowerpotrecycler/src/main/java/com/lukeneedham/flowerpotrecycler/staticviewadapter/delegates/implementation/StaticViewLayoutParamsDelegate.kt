package com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.implementation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.staticviewadapter.StaticViewRecyclerViewHolder
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.BaseStaticViewAdapterFeatureDelegate

/** Use this delegate to add layout params to your item views */
class StaticViewLayoutParamsDelegate(
    private val layoutParams: RecyclerView.LayoutParams
) : BaseStaticViewAdapterFeatureDelegate() {

    override fun onViewHolderCreated(
        viewHolder: StaticViewRecyclerViewHolder,
        parent: ViewGroup,
        viewType: Int
    ) {
        viewHolder.itemView.layoutParams = RecyclerView.LayoutParams(layoutParams)
    }
}
