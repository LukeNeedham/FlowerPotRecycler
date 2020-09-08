package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.xml

import android.view.View
import androidx.annotation.LayoutRes
import kotlin.reflect.KClass

class XMLBuilderBinderLabmda<ItemType : Any>(
    @LayoutRes override val xmlLayoutResId: Int,
    override val itemTypeClass: KClass<ItemType>,
    private val binder: (itemView: View, position: Int, item: ItemType) -> Unit
) : XMLBuilderBinder<ItemType>() {

    override fun bind(itemView: View, position: Int, item: ItemType) {
        binder(itemView, position, item)
    }

    companion object {
        inline fun <reified ItemType : Any> fromType(
            @LayoutRes xmlLayoutResId: Int,
            noinline binder: (itemView: View, position: Int, item: ItemType) -> Unit
        ): XMLBuilderBinderLabmda<ItemType> =
            XMLBuilderBinderLabmda(
                xmlLayoutResId,
                ItemType::class,
                binder
            )
    }
}
