package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.xml

import android.view.View
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemMatcher

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
            XmlLabmdaBuilderBinder(
                xmlLayoutResId,
                ClassMatcher(ItemType::class),
                binder
            )
    }
}
