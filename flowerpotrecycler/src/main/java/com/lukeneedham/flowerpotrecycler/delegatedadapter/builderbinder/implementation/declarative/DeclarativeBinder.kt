package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.declarative

import android.view.View
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import java.util.*

class DeclarativeBinder<ItemType> {
    private var currentViewId: UUID? = null

    private val viewIdToBindCallbacks = mutableMapOf<UUID, List<(ItemType) -> Unit>>()

    fun addBindCallback(callback: (ItemType) -> Unit) {
        val currentViewId = requireCurrentViewId()
        val existingCallbacks = viewIdToBindCallbacks[currentViewId]?.toMutableList()
            ?: mutableListOf()
        existingCallbacks.add(callback)
        viewIdToBindCallbacks[currentViewId] = existingCallbacks
    }

    /** Must be called immediately before the view is created */
    fun onPreViewBuild() {
        currentViewId = UUID.randomUUID()
    }

    /** Must be called immediately after the view is created */
    fun onPostViewBuild(view: View) {
        view.tag = requireCurrentViewId()
    }

    fun bind(itemView: View, item: ItemType) {
        val itemViewId = itemView.tag
        val callbacks = viewIdToBindCallbacks[itemViewId] ?: emptyList()
        callbacks.forEach {
            it(item)
        }
    }

    private fun requireCurrentViewId(): UUID =
        currentViewId ?: throw FlowerPotRecyclerException("currentViewId must be set")
}
