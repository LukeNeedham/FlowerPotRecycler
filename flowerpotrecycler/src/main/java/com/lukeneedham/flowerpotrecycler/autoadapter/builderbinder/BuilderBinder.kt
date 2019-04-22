package com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder

import android.view.View
import android.view.ViewGroup

/**
 * RecyclerView Item Builder + Binder
 *
 * Subclass this class to control the View representing each item in the RecyclerView (via [build]),
 * and to update the View when an item is bound to it (via [bind])
 *
 * If you are building the layout programmatically, (for example, via Anko DSL Layout), you may prefer to use
 * [DataBindingBuilderBinder][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.databinding.DataBindingBuilderBinder]
 *
 * If the layout is supplied by XML, you may prefer to use [XMLBuilderBinder]
 */
abstract class BuilderBinder<ItemType> {

    /**
     * Build the View of a single item
     * @param parent The parent of the View being built
     * @return A View representing a single item of the RecyclerView
     */
    abstract fun build(parent: ViewGroup): View

    /**
     * Bind the built View to its item
     * @param item The item to bind
     * @param itemView The View to bind the item to
     */
    abstract fun bind(item: ItemType, itemView: View)
}