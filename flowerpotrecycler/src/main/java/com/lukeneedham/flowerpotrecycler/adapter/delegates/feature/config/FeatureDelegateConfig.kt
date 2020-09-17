package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config

import android.view.View

interface FeatureDelegateConfig<ItemType : Any, ItemViewType : View> {
    var delegateCreators: MutableList<FeatureDelegateCreator<ItemType, ItemViewType>>
}
