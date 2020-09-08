package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemBuilderBinder

abstract class XMLBuilderBinder<ItemType : Any> : ItemBuilderBinder<ItemType, View>() {

    abstract val xmlLayoutResId: Int

    override fun createView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(xmlLayoutResId, parent, false)
    }
}
