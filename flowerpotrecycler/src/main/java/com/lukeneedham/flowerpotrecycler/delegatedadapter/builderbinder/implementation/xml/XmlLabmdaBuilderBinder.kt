package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.xml

import android.view.View
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder

class XmlLabmdaBuilderBinder<ItemType : Any>(
    @LayoutRes override val xmlLayoutResId: Int,
    override val itemMatcher: ItemMatcher<ItemType>,
    private val binder: (itemView: View, position: Int, item: ItemType) -> Unit
) : XmlBuilderBinder<ItemType>() {

    override fun bind(itemView: View, position: Int, item: ItemType) {
        binder(itemView, position, item)
    }

    companion object {
        inline fun <reified ItemType : Any> fromType(
            @LayoutRes xmlLayoutResId: Int,
            noinline binder: (itemView: View, position: Int, item: ItemType) -> Unit
        ): XmlLabmdaBuilderBinder<ItemType> =
            XmlLabmdaBuilderBinder(xmlLayoutResId, ClassMatcher(ItemType::class), binder)

        /** For a static view - no binding required */
        fun <ItemType : Any> fromStatic(
            @LayoutRes xmlLayoutResId: Int,
            itemMatcher: ItemMatcher<ItemType>
        ): XmlLabmdaBuilderBinder<ItemType> =
            XmlLabmdaBuilderBinder(xmlLayoutResId, itemMatcher, createEmptyBinder())

        /** For a static view - no binding required */
        inline fun <reified ItemType : Any> fromStaticClass(
            @LayoutRes xmlLayoutResId: Int
        ): XmlLabmdaBuilderBinder<ItemType> {
            return XmlLabmdaBuilderBinder(
                xmlLayoutResId,
                ClassMatcher(ItemType::class),
                createEmptyBinder()
            )
        }
    }
}
