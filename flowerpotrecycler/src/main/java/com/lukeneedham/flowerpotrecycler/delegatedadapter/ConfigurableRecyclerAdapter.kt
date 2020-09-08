package com.lukeneedham.flowerpotrecycler.delegatedadapter

import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.util.extensions.getFeatureDelegates

/**
 * @param config supplies the setup for the adapter.
 * If null, the values are the defaults as defined in [DefaultDelegatedRecyclerAdapter]
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
