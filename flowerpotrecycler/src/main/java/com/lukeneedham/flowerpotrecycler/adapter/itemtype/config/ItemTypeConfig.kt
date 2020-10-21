package com.lukeneedham.flowerpotrecycler.adapter.itemtype.config

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.ItemTypeDelegate
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher.ItemMatcher

/**
 * Contains a [builderBinder], along with its [featureDelegateConfig],
 * which will be combined to create a [ItemTypeDelegate].
 *
 * This allows the [ItemTypeDelegate] to be created only when a [RecyclerView.Adapter] is provided,
 * which allows [AdapterFeatureDelegate]s to reference the Adapter.
 */
data class ItemTypeConfig<ItemType : Any, ItemViewType : View>(
    val builderBinder: BuilderBinder<ItemType, ItemViewType>,
    val featureDelegateConfig: FeatureDelegateConfig<ItemType, ItemViewType>,
    val itemMatcher: ItemMatcher
) {
    fun createDelegate(adapter: RecyclerView.Adapter<*>): ItemTypeDelegate<ItemType, ItemViewType> {
        val featureDelegates = featureDelegateConfig.delegateCreators.map { it.invoke(adapter) }
        return ItemTypeDelegate(
            builderBinder,
            itemMatcher,
            featureDelegates
        )
    }
}
