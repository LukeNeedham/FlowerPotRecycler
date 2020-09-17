package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config

import android.view.View

class FeatureConfig<ItemType : Any, ItemViewType : View>(
    override var delegateCreators: MutableList<FeatureDelegateCreator<ItemType, ItemViewType>> =
        mutableListOf()
) : FeatureDelegateConfig<ItemType, ItemViewType>
