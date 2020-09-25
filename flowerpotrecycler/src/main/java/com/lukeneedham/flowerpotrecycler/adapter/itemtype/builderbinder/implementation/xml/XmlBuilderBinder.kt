package com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.Binder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder

@Suppress("unused")
class XmlBuilderBinder<ItemType>(
    @LayoutRes val xmlLayoutResId: Int,
    private val binder: (itemView: View, position: Int, item: ItemType) -> Unit
) : BuilderBinder<ItemType, View> {

    override fun build(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(xmlLayoutResId, parent, false)
    }

    override fun bind(itemView: View, position: Int, item: ItemType) {
        binder(itemView, position, item)
    }

    companion object {
        inline fun <reified ItemType> newInstance(
            @LayoutRes xmlLayoutResId: Int,
            noinline binder: Binder<ItemType, View> = createEmptyBinder()
        ): XmlBuilderBinder<ItemType> =
            XmlBuilderBinder(xmlLayoutResId, binder)
    }
}
