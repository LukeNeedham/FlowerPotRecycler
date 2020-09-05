package com.lukeneedham.flowerpotrecycler.staticview.config

import com.lukeneedham.flowerpotrecycler.staticview.StaticViewRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.staticview.delegates.StaticViewAdapterFeatureDelegate

interface StaticViewRecyclerAdapterConfig {
    /** A list of functions to create [StaticViewAdapterFeatureDelegate]s */
    var featureDelegateCreators:
            MutableList<(adapter: StaticViewRecyclerAdapter) -> StaticViewAdapterFeatureDelegate>
}
