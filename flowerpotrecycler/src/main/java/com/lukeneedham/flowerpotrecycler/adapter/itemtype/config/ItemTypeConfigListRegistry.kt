package com.lukeneedham.flowerpotrecycler.adapter.itemtype.config

import android.view.View

class ItemTypeConfigListRegistry<BaseItemType : Any, BaseItemViewType : View>(
    private val configs: List<ItemTypeConfig<out BaseItemType, out BaseItemViewType>>
) : ItemTypeConfigRegistry<BaseItemType, BaseItemViewType> {
    override fun getItemTypeConfigs(): List<ItemTypeConfig<out BaseItemType, out BaseItemViewType>> {
        return configs
    }
}
