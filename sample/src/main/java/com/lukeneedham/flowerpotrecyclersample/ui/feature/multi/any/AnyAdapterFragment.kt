package com.lukeneedham.flowerpotrecyclersample.ui.feature.multi.any

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterCreator
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view.ViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.ItemTypeConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import com.lukeneedham.flowerpotrecyclersample.ui.feature.FlowerPotItemView
import com.lukeneedham.flowerpotrecyclersample.ui.feature.multi.IntItemView
import com.lukeneedham.flowerpotrecyclersample.ui.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class AnyAdapterFragment : Fragment(R.layout.fragment_recyclerview_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Takes config in the same way as single-type adapter

        // The config is of the shared super-type
        // We want to show ints and Strings, so the shared-super class is Any
        val adapterConfig = AdapterConfig<Any, View>().apply {
            val flowerPotItems = FlowerPotDatabase.getAllEntries()
            val intItems = listOf(1, 2, 3, 4, 5)
            val staticItems = listOf(StaticA, StaticB)
            // Only add items of types which have a registered BuilderBinder
            // Otherwise, the adapter will throw an error
            items = (flowerPotItems + intItems + staticItems).shuffled()
        }

        val itemLayoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

        // When an item of type FlowerPotModel is encountered,
        // delegate its building and binding to FlowerPotItemView, with the additional features
        val flowerPotFeatures = FeatureConfig<FlowerPotModel, FlowerPotItemView>().apply {
            addItemLayoutParams(itemLayoutParams)
            addOnItemClickListener { item, _, _ ->
                showSnackbar("Flower Pot: " + getString(item.nameResId))
            }
        }
        val flowerPotConfig = ItemTypeConfig
            .newInstance(RecyclerItemViewBuilderBinder.newInstance(), flowerPotFeatures)

        val intConfig = ItemTypeConfig.newInstance(
            RecyclerItemViewBuilderBinder.newInstance(),
            FeatureConfig<Int, IntItemView>().apply {
                addItemLayoutParams(itemLayoutParams)
                addOnItemClickListener { item, _, _ -> showSnackbar("Int: $item") }
            }
        )

        // StaticA is a singleton.
        // For every StaticA in the list of items submitted to the adapter,
        // a StaticAItemView will be shown in the corresponding position
        // In a real use-case, this might be a Header view
        val staticAConfig = ItemTypeConfig.newInstance(
            ViewBuilderBinder.newInstance(),
            FeatureConfig<StaticA, StaticAItemView>().apply {
                addItemLayoutParams(itemLayoutParams)
            }
        )

        // Static B is similar to Static A
        val staticBConfig = ItemTypeConfig.newInstance(
            ViewBuilderBinder.newInstance(),
            FeatureConfig<StaticB, StaticBItemView>().apply {
                addItemLayoutParams(itemLayoutParams)
            }
        )

        // adapterConfig optional
        val recyclerAdapter = RecyclerAdapterCreator.fromItemTypeConfigs(
            flowerPotConfig,
            intConfig,
            staticAConfig,
            staticBConfig,
            adapterConfig = adapterConfig
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}
