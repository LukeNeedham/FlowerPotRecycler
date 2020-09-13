package com.lukeneedham.flowerpotrecycler.adapter

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.util.extensions.getFeatureDelegates

/**
 * @param config supplies the setup for the adapter.
 * If null, the values are the defaults as defined in [DefaultDelegatedRecyclerAdapter]
 */
class ConfigurableRecyclerAdapter<BaseItemType : Any, BaseItemViewType : View>(
    override val builderBinderRegistry: BuilderBinderRegistry<BaseItemType, BaseItemViewType>,
    config: RecyclerAdapterConfig<BaseItemType, BaseItemViewType>?
) : DefaultDelegatedRecyclerAdapter<BaseItemType, BaseItemViewType>() {

    override val featureDelegates: List<AdapterFeatureDelegate<BaseItemType, BaseItemViewType>> =
        config?.getFeatureDelegates(this) ?: super.featureDelegates
    override val positionDelegate: AdapterPositionDelegate<BaseItemType> =
        config?.positionDelegateCreator?.invoke(this) ?: super.positionDelegate

    init {
        if (config != null) {
            submitList(config.items)
        }
    }
}
