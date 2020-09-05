package com.lukeneedham.flowerpotrecycler.staticview.delegates

import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.staticview.StaticViewRecyclerViewHolder

/** A delegate for extended functionality on the adapter */
interface StaticViewAdapterFeatureDelegate {

    /**
     * A hook for after the ViewHolder has been created in [onCreateViewHolder].
     * This hook can be used to modify the created View after creation.
     */
    fun onViewHolderCreated(
        viewHolder: StaticViewRecyclerViewHolder,
        parent: ViewGroup,
        viewType: Int
    )

    fun onClick()
}
