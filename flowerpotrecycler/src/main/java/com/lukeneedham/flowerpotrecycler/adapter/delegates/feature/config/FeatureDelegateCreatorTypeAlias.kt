package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config

import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate

typealias FeatureDelegateCreator<ItemType, ItemViewType> = (adapter: RecyclerView.Adapter<*>) -> AdapterFeatureDelegate<ItemType, ItemViewType>
