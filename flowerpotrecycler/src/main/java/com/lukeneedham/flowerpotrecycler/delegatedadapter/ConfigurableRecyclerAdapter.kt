package com.lukeneedham.flowerpotrecycler.delegatedadapter

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.util.getFeatureDelegates
import kotlin.reflect.KClass

/**
 * Sets up the adapter with values from [config], if not null.
 * If [config] is null, the values are the defaults as defined in [DefaultSingleTypeRecyclerAdapter]
 */
class ConfigurableRecyclerAdapter<ItemType : Any, ItemViewType>(
    config: RecyclerAdapterConfig<ItemType>?,
    override val itemClass: KClass<ItemType>,
    private val itemViewCreator: (Context) -> ItemViewType
) : DefaultSingleTypeRecyclerAdapter<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override val featureDelegates: List<AdapterFeatureDelegate<ItemType>> =
        config?.getFeatureDelegates(this) ?: super.featureDelegates
    override val positionDelegate: AdapterPositionDelegate<ItemType> =
        config?.positionDelegateCreator?.invoke(this) ?: super.positionDelegate

    init {
        if (config != null) {
            positionDelegate.submitList(config.items)
        }
    }

    override fun createItemView(context: Context): ItemViewType = itemViewCreator(context)

    companion object {
        inline fun <reified ItemType : Any, ItemViewType> create(
            config: RecyclerAdapterConfig<ItemType>?,
            noinline itemViewCreator: (Context) -> ItemViewType
        ): ConfigurableRecyclerAdapter<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
            return ConfigurableRecyclerAdapter(config, ItemType::class, itemViewCreator)
        }
    }
}
