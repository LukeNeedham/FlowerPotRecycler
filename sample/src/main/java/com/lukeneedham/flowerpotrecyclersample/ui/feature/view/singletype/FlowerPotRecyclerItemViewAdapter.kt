package com.lukeneedham.flowerpotrecyclersample.ui.feature.view.singletype

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.ItemLayoutParamsDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.OnItemClickDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation.SelectableItemDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import com.lukeneedham.flowerpotrecyclersample.ui.feature.view.FlowerPotItemView

/**
 * A demo of extending [DelegatedRecyclerAdapter] to add your own functionality.
 * In this case, adding [selectableItemDelegate].
 *
 * You can also override [DefaultDelegatedRecyclerAdapter], which comes with defaults,
 * so you don't need to override [featureDelegates] and [positionDelegate]
 */
class FlowerPotRecyclerItemViewAdapter(
    onItemClick: (FlowerPotModel) -> Unit
) : DelegatedRecyclerAdapter<FlowerPotModel>() {
    private val selectableItemDelegate = SelectableItemDelegate(this)

    override val builderBinderRegistry = BuilderBinderRegistry.from(
        RecyclerItemViewBuilderBinder.fromItemType<FlowerPotModel, FlowerPotItemView>()
    )

    override val featureDelegates: List<AdapterFeatureDelegate<FlowerPotModel>> = listOf(
        ItemLayoutParamsDelegate(
            RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        ),
        OnItemClickDelegate { item, position -> onItemClick(item) },
        selectableItemDelegate
    )

    override val positionDelegate: AdapterPositionDelegate<FlowerPotModel> =
        LinearPositionDelegate(this, DefaultDiffCallback())

    override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
        return super.onFailedToRecycleView(holder)
        // Since we've subclassed RecyclerView we can override all its methods
        // Maybe we want to do something here, for example
    }

    fun selectItem(item: FlowerPotModel) {
        selectableItemDelegate.selectItem(item)
    }
}
