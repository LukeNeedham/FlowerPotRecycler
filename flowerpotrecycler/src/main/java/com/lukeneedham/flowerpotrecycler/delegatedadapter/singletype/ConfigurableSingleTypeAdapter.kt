package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.Binder
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.util.getFeatureDelegates

/**
 * Sets up the adapter with values from [config], if not null.
 * If [config] is null, the values are the defaults as defined in [DefaultSingleTypeRecyclerItemViewAdapter]
 */
class ConfigurableSingleTypeAdapter<ItemType : Any, ItemViewType : View>(
    config: RecyclerAdapterConfig<ItemType>?,
    private val builder: Builder<ItemViewType>,
    private val binder: Binder<ItemType, ItemViewType>
) : DefaultSingleTypeAdapter<ItemType, ItemViewType>() {

    override val featureDelegates: List<AdapterFeatureDelegate<ItemType>> =
        config?.getFeatureDelegates(this) ?: super.featureDelegates
    override val positionDelegate: AdapterPositionDelegate<ItemType> =
        config?.positionDelegateCreator?.invoke(this) ?: super.positionDelegate

    init {
        if (config != null) {
            submitList(config.items)
        }
    }

    override fun createItemView(parent: ViewGroup): ItemViewType = builder(parent)

    override fun bindItemView(itemView: ItemViewType, position: Int, item: ItemType) {
        binder(itemView, position, item)
    }
}
