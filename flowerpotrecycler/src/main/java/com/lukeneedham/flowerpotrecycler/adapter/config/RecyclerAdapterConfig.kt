package com.lukeneedham.flowerpotrecycler.adapter.config

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate

/**
 * Config for the [ConfigurableRecyclerAdapter].
 * Allows for the customisation of initial [items],
 * and the [positionDelegateCreator] for creating the [AdapterPositionDelegate] used by the Adapter
 */
interface RecyclerAdapterConfig<ItemType : Any, ItemViewType : View> {
    /** A list of items to show as the initial contents of the RecyclerView */
    var items: List<ItemType>
    
    /**
     * A function to create the [AdapterPositionDelegate],
     * or null to use the default as defined in [ConfigurableRecyclerAdapter]
     */
    var positionDelegateCreator:
                (adapter: RecyclerView.Adapter<*>) -> AdapterPositionDelegate<ItemType>?
}
