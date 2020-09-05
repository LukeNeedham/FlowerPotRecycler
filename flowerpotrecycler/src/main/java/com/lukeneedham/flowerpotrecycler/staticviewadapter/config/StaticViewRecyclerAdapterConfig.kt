package com.lukeneedham.flowerpotrecycler.staticviewadapter.config

import com.lukeneedham.flowerpotrecycler.staticviewadapter.StaticViewRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.StaticViewAdapterFeatureDelegate

interface StaticViewRecyclerAdapterConfig {
    /** A list of functions to create [StaticViewAdapterFeatureDelegate]s */
    var featureDelegateCreators:
            MutableList<(adapter: StaticViewRecyclerAdapter) -> StaticViewAdapterFeatureDelegate>
}
