package com.lukeneedham.flowerpotrecycler.staticviewadapter.config

import com.lukeneedham.flowerpotrecycler.staticviewadapter.StaticViewRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.StaticViewAdapterFeatureDelegate

/**
 * A basic [StaticViewRecyclerAdapterConfig].
 * By default, it uses no feature delegates.
 */
class StaticViewAdapterConfig :
    StaticViewRecyclerAdapterConfig {
    override var featureDelegateCreators: MutableList<(adapter: StaticViewRecyclerAdapter) -> StaticViewAdapterFeatureDelegate> =
        mutableListOf()
}
