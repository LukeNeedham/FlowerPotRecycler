package com.lukeneedham.flowerpotrecycler.adapter.itemtype.config

import android.view.View

interface ItemTypeConfigRegistry<BaseItemType, BaseItemViewType : View> {
    fun getItemTypeConfigs(): List<ItemTypeConfig<out BaseItemType, out BaseItemViewType>>
}
