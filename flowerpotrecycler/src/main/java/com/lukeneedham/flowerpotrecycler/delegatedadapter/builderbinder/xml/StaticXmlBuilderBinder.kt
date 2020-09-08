package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.xml

import android.view.View
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemMatcher

/** Inflates a static XNL layout file - so no binding is done */
class StaticXmlBuilderBinder<ItemType : Any>(
    @LayoutRes override val xmlLayoutResId: Int,
    override val itemMatcher: ItemMatcher<ItemType>
) : XmlBuilderBinder<ItemType>() {

    override fun bind(itemView: View, position: Int, item: ItemType) {
        // NOOP - no binding needs to be done, because this view is static
    }

    companion object {
        inline fun <reified ItemType : Any> fromType(@LayoutRes xmlLayoutResId: Int):
                StaticXmlBuilderBinder<ItemType> =
            StaticXmlBuilderBinder(xmlLayoutResId, ClassMatcher(ItemType::class))
    }
}
