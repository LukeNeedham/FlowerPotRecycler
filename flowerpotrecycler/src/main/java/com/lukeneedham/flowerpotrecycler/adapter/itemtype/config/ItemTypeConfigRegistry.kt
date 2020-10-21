package com.lukeneedham.flowerpotrecycler.adapter.itemtype.config

import android.view.View

interface ItemTypeConfigRegistry<BaseItemType : Any, BaseItemViewType : View> {
    fun getItemTypeConfigs(): List<ItemTypeConfig<out BaseItemType, out BaseItemViewType>>
}
