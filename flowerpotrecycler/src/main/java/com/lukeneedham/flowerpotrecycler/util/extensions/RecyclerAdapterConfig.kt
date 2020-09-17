@file:Suppress("unused")

package com.lukeneedham.flowerpotrecycler.util.extensions

import androidx.recyclerview.widget.DiffUtil
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation.CyclicPositionDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation.LinearPositionDelegate

/**
 * Makes the adapter cyclic / wraparound. See [CyclicPositionDelegate]
 * @param diffCallback an optional [DiffUtil.ItemCallback], used to calculate the diff when items change.
 * Defaults to [DefaultDiffCallback]
 */
fun <ItemType : Any> RecyclerAdapterConfig<ItemType, *>.setCyclic(
    diffCallback: DiffUtil.ItemCallback<ItemType> = DefaultDiffCallback()
) {
    positionDelegateCreator = { CyclicPositionDelegate(it, diffCallback) }
}

/**
 * Makes the adapter linear. See [LinearPositionDelegate]
 * @param diffCallback an optional [DiffUtil.ItemCallback], used to calculate the diff when items change.
 * Defaults to [DefaultDiffCallback]
 */
fun <ItemType : Any> RecyclerAdapterConfig<ItemType, *>.setLinear(
    diffCallback: DiffUtil.ItemCallback<ItemType> = DefaultDiffCallback()
) {
    positionDelegateCreator = { LinearPositionDelegate(it, diffCallback) }
}

/**
 * Overrides the value of [RecyclerAdapterConfig.items] to instead use [numberOfItems] dummy items.
 * The dummy items are simply [Unit].
 * As such, it is your responsibility to ensure that your adapter can handle items of type [Unit].
 */
fun RecyclerAdapterConfig<Unit, *>.useDummyItems(numberOfItems: Int) {
    items = List(numberOfItems) { Unit }
}
