package com.lukeneedham.flowerpotrecycler.adapter.itemtypedelegate

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateConfig

/**
 * Contains a [builderBinder], along with its [featureDelegateConfig],
 * which will be combined to create a [ItemTypeDelegate].
 *
 * This allows the [ItemTypeDelegate] to be created only when a [RecyclerView.Adapter] is provided,
 * which allows [AdapterFeatureDelegate]s to reference the Adapter.
 */
data class ItemTypeBuilder<ItemType : Any, ItemViewType : View>(
    val builderBinder: BuilderBinder<ItemType, ItemViewType>,
    val itemMatcher: ItemMatcher<ItemType>,
    val featureDelegateConfig: FeatureDelegateConfig<ItemType, ItemViewType>
) {
    fun build(adapter: RecyclerView.Adapter<*>): ItemTypeDelegate<ItemType, ItemViewType> {
        val featureDelegates = featureDelegateConfig.delegateCreators.map { it.invoke(adapter) }
        return ItemTypeDelegate(builderBinder, itemMatcher, featureDelegates)
    }

    companion object {
        inline fun <reified ItemType : Any, ItemViewType : View> from(
            builderBinder: BuilderBinder<ItemType, ItemViewType>,
            featureConfig: FeatureDelegateConfig<ItemType, ItemViewType>? = null,
            itemMatcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class)
            ): ItemTypeBuilder<ItemType, ItemViewType> {
            val config = featureConfig ?: FeatureConfig()
            return ItemTypeBuilder(builderBinder, itemMatcher, config)
        }

        inline fun <reified ItemType : Any, ItemViewType : View> from(
            builderBinder: BuilderBinder<ItemType, ItemViewType>,
            itemMatcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class),
            featureDelegateDsl: FeatureDelegateConfig<ItemType, ItemViewType>.() -> Unit = {}
        ): ItemTypeBuilder<ItemType, ItemViewType> {
            val featureConfig = FeatureConfig<ItemType, ItemViewType>()
            featureConfig.featureDelegateDsl()
            return ItemTypeBuilder(builderBinder, itemMatcher, featureConfig)
        }
    }
}
