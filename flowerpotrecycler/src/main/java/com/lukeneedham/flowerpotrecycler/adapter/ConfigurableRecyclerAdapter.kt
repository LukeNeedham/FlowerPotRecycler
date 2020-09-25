package com.lukeneedham.flowerpotrecycler.adapter

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.ItemTypeConfig
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate

/**
 * @param config supplies the setup for the adapter.
 * If null, the values are the defaults as defined in [DefaultDelegatedRecyclerAdapter]
 */
class ConfigurableRecyclerAdapter<BaseItemType, BaseItemViewType : View>(
    override val itemTypeConfigs: List<ItemTypeConfig<out BaseItemType, out BaseItemViewType>>,
    config: RecyclerAdapterConfig<BaseItemType, BaseItemViewType>?
) : DefaultDelegatedRecyclerAdapter<BaseItemType, BaseItemViewType>() {

    override val positionDelegate: AdapterPositionDelegate<BaseItemType> =
        config?.positionDelegateCreator?.invoke(this) ?: super.positionDelegate

    init {
        if (config != null) {
            submitList(config.items)
        }
    }
}
