package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.xml

import android.view.View
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder

@Suppress("unused")
class XmlBuilderBinder<ItemType : Any>(
    @LayoutRes override val xmlLayoutResId: Int,
    override val itemMatcher: ItemMatcher<ItemType>,
    private val binder: (itemView: View, position: Int, item: ItemType) -> Unit
) : BaseXmlBuilderBinder<ItemType>() {

    override fun bind(itemView: View, position: Int, item: ItemType) {
        binder(itemView, position, item)
    }

    companion object {
        inline fun <reified ItemType : Any> fromType(
            @LayoutRes xmlLayoutResId: Int,
            noinline binder: (itemView: View, position: Int, item: ItemType) -> Unit
        ): XmlBuilderBinder<ItemType> =
            XmlBuilderBinder(xmlLayoutResId, ClassMatcher(ItemType::class), binder)

        /** For a static view - no binding required */
        fun <ItemType : Any> fromStaticMatcher(
            @LayoutRes xmlLayoutResId: Int,
            itemMatcher: ItemMatcher<ItemType>
        ): XmlBuilderBinder<ItemType> =
            XmlBuilderBinder(xmlLayoutResId, itemMatcher, createEmptyBinder())

        /** For a static view - no binding required */
        inline fun <reified ItemType : Any> fromStatic(
            @LayoutRes xmlLayoutResId: Int
        ): XmlBuilderBinder<ItemType> {
            return fromStaticMatcher(xmlLayoutResId, ClassMatcher(ItemType::class))
        }
    }
}
