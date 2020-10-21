package com.lukeneedham.flowerpotrecycler.util

import android.view.View
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.config.ItemTypeConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.view.ViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.xml.XmlBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher.ItemMatcher

/**
 * A collection of utils for creating [ItemTypeConfig]s with default configuration.
 * For full flexibility, instantiate the [ItemTypeConfig] manually.
 */
object ItemTypeConfigCreator {

    /**
     * Create an [ItemTypeConfig] from [builderBinder],
     * with defaults for an empty [FeatureDelegateConfig]
     * and standard [ItemMatcher]
     */
    inline fun <reified ItemType : Any, ItemViewType : View> fromBuilderBinder(
        builderBinder: BuilderBinder<ItemType, ItemViewType>,
        featureConfig: FeatureDelegateConfig<ItemType, ItemViewType>? = null,
        itemMatcher: ItemMatcher = ClassMatcher.newInstance<ItemType>()
    ): ItemTypeConfig<ItemType, ItemViewType> {
        val featureConfigOrEmpty = featureConfig ?: FeatureConfig()
        return ItemTypeConfig(
            builderBinder,
            featureConfigOrEmpty,
            itemMatcher
        )
    }

    /**
     * @param configDsl a DSL for setting up the [FeatureDelegateConfig] to use
     * @return an [ItemTypeConfig] using common defaults and a [RecyclerItemViewBuilderBinder]
     */
    inline fun <reified ItemType : Any, reified ItemViewType> fromRecyclerItemView(
        configDsl: FeatureDelegateConfig<ItemType, ItemViewType>.() -> Unit = {}
    ): ItemTypeConfig<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        val config = FeatureConfig<ItemType, ItemViewType>()
        config.configDsl()
        return fromBuilderBinder(RecyclerItemViewBuilderBinder.newInstance(), config)
    }

    /**
     * @param configDsl a DSL for setting up the [FeatureDelegateConfig] to use
     * @return an [ItemTypeConfig] using common defaults and a [ViewBuilderBinder] with no binding
     */
    inline fun <reified ItemType : Any, reified ItemViewType : View> fromStaticView(
        configDsl: FeatureDelegateConfig<ItemType, ItemViewType>.() -> Unit = {}
    ): ItemTypeConfig<ItemType, ItemViewType> {
        val config = FeatureConfig<ItemType, ItemViewType>()
        config.configDsl()
        return fromBuilderBinder(ViewBuilderBinder.newInstance(), config)
    }

    /**
     * @param xmlLayoutResId layout resource to inflate
     * @param configDsl a DSL for setting up the [FeatureDelegateConfig] to use
     * @return an [ItemTypeConfig] using common defaults and a [XmlBuilderBinder] with no binding
     */
    inline fun <reified ItemType : Any> fromStaticXml(
        @LayoutRes xmlLayoutResId: Int,
        configDsl: FeatureDelegateConfig<ItemType, View>.() -> Unit = {}
    ): ItemTypeConfig<ItemType, View> {
        val config = FeatureConfig<ItemType, View>()
        config.configDsl()
        return fromBuilderBinder(XmlBuilderBinder.newInstance(xmlLayoutResId), config)
    }
}
