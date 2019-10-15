package com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

internal abstract class XMLBuilderBinder<ItemType>(@LayoutRes private val xmlLayoutResId: Int) :
    BuilderBinder<ItemType>() {
    final override fun build(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(xmlLayoutResId, parent, false)
}