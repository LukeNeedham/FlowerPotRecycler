package com.lukeneedham.flowerpotrecycler.staticview.delegates.implementation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.staticview.StaticViewRecyclerViewHolder
import com.lukeneedham.flowerpotrecycler.staticview.delegates.BaseStaticViewAdapterFeatureDelegate

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
