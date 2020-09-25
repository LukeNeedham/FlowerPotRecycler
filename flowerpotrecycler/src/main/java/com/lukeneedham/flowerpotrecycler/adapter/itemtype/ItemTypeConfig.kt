package com.lukeneedham.flowerpotrecycler.adapter.itemtype

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher.ItemMatcher

/**
 * Contains a [builderBinder], along with its [featureDelegateConfig],
 * which will be combined to create a [ItemTypeDelegate].
 *
 * This allows the [ItemTypeDelegate] to be created only when a [RecyclerView.Adapter] is provided,
 * which allows [AdapterFeatureDelegate]s to reference the Adapter.
 */
data class ItemTypeConfig<ItemType, ItemViewType : View>(
    val builderBinder: BuilderBinder<ItemType, ItemViewType>,
    val featureDelegateConfig: FeatureDelegateConfig<ItemType, ItemViewType>,
    val itemMatcher: ItemMatcher
) {
    fun createItemTypeDelegate(adapter: RecyclerView.Adapter<*>):
            ItemTypeDelegate<ItemType, ItemViewType> {
        val featureDelegates = featureDelegateConfig.delegateCreators.map { it.invoke(adapter) }
        return ItemTypeDelegate(builderBinder, itemMatcher, featureDelegates)
    }

    companion object {
        /** Create an [ItemTypeConfig] */
        inline fun <reified ItemType : Any, ItemViewType : View> newInstance(
            builderBinder: BuilderBinder<ItemType, ItemViewType>,
            featureConfig: FeatureDelegateConfig<ItemType, ItemViewType>? = null,
            itemMatcher: ItemMatcher = ClassMatcher.newInstance<ItemType>()
        ): ItemTypeConfig<ItemType, ItemViewType> {
            val featureConfigOrEmpty = featureConfig ?: FeatureConfig()
            return ItemTypeConfig(builderBinder, featureConfigOrEmpty, itemMatcher)
        }
    }
}
