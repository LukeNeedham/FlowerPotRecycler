package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.util.getFeatureDelegates

/**
 * Sets up the adapter with values from [config], if not null.
 * If [config] is null, the values are the defaults as defined in [DefaultSingleTypeRecyclerAdapter]
 */
class ConfigurableRecyclerAdapter<BaseItemType : Any>(
    override val builderBinderRegistry: BuilderBinderRegistry<BaseItemType>,
    config: RecyclerAdapterConfig<BaseItemType>?
) : DefaultDelegatedRecyclerAdapter<BaseItemType>() {

    override val featureDelegates: List<AdapterFeatureDelegate<BaseItemType>> =
        config?.getFeatureDelegates(this) ?: super.featureDelegates
    override val positionDelegate: AdapterPositionDelegate<BaseItemType> =
        config?.positionDelegateCreator?.invoke(this) ?: super.positionDelegate

    init {
        if (config != null) {
            submitList(config.items)
        }
    }
}
