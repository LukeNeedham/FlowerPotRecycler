package com.lukeneedham.flowerpotrecycler.util

import android.view.View
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.ItemTypeConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.view.ViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.xml.XmlBuilderBinder

/**
 * A collection of utils for creating [ItemTypeConfig]s with default configuration.
 * For full flexibility, instantiate the [ItemTypeConfig] manually.
 */
object ItemTypeConfigCreator {

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
        return ItemTypeConfig.newInstance(RecyclerItemViewBuilderBinder.newInstance(), config)
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
        return ItemTypeConfig.newInstance(ViewBuilderBinder.newInstance(), config)
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
        return ItemTypeConfig.newInstance(XmlBuilderBinder.newInstance(xmlLayoutResId), config)
    }
}
