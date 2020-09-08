package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.recycleritemview

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.util.getFeatureDelegates

/**
 * Sets up the adapter with values from [config], if not null.
 * If [config] is null, the values are the defaults as defined in [DefaultSingleTypeRecyclerItemViewAdapter]
 */
class ConfigurableSingleTypeRecyclerItemViewAdapter<ItemType : Any, ItemViewType>(
    config: RecyclerAdapterConfig<ItemType>?,
    private val itemViewCreator: Builder<ItemViewType>
) : DefaultSingleTypeRecyclerItemViewAdapter<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override val featureDelegates: List<AdapterFeatureDelegate<ItemType>> =
        config?.getFeatureDelegates(this) ?: super.featureDelegates
    override val positionDelegate: AdapterPositionDelegate<ItemType> =
        config?.positionDelegateCreator?.invoke(this) ?: super.positionDelegate

    init {
        if (config != null) {
            submitList(config.items)
        }
    }

    override fun createItemView(parent: ViewGroup): ItemViewType = itemViewCreator(parent)
}
