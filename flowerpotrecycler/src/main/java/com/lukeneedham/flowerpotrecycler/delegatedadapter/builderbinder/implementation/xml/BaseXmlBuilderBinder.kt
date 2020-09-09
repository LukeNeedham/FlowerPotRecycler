package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder

/** Builds the item view by inflating [xmlLayoutResId]. Binding logic is provided by the subclass */
abstract class BaseXmlBuilderBinder<ItemType : Any> : BuilderBinder<ItemType, View>() {

    abstract val xmlLayoutResId: Int

    override fun build(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(xmlLayoutResId, parent, false)
    }
}
