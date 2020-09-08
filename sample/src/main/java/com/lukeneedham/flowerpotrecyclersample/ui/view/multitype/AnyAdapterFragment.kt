package com.lukeneedham.flowerpotrecyclersample.ui.view.multitype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view.ViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import com.lukeneedham.flowerpotrecyclersample.ui.view.FlowerPotItemView
import kotlinx.android.synthetic.main.fragment_xml_layout.*

class AnyAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Takes config in the same way as single-type adapter

        // The config is of the shared super-type
        // We want to show ints and Strings, so the shared-super class is Any
        val adapterConfig = AdapterConfig<Any>().apply {
            val flowerPotItems = FlowerPotDatabase.getAllEntries()
            val intItems = listOf(1, 2, 3, 4, 5)
            val staticItems = listOf(StaticA, StaticB)
            // Only add items of types which have a registered BuilderBinder
            // Otherwise, the adapter will throw an error
            items = (flowerPotItems + intItems + staticItems).shuffled()
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { item, position ->
                val context = requireContext()
                Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        // This BuilderBinder says: when an item of type FlowerPotModel is encountered,
        // delegate its building and binding to FlowerPotItemView
        val flowerPotBuilderBinder =
            RecyclerItemViewBuilderBinder.fromItemType<FlowerPotModel, FlowerPotItemView>()

        val intBuilderBinder =
            RecyclerItemViewBuilderBinder.fromItemType<Int, IntItemView>()

        // StaticA is a singleton.
        // For every StaticA in the list of items submitted to the adapter,
        // a StaticAItemView will be shown in the corresponding position
        // In a real use-case, this might be a Header view
        val staticABuilderBinder =
            ViewBuilderBinder.fromStaticType<StaticA, StaticAItemView>()

        val staticBBuilderBinder =
            ViewBuilderBinder.fromStaticType<StaticB, StaticBItemView>()

        // Multi-type adapter from type registry
        // Config optional
        val builderBinderRegistry: BuilderBinderRegistry<Any> =
            BuilderBinderRegistry.from(
                flowerPotBuilderBinder,
                intBuilderBinder,
                staticABuilderBinder,
                staticBBuilderBinder
            )
        val recyclerAdapter =
            RecyclerAdapterBuilder.fromBuilderBinderRegistry(builderBinderRegistry, adapterConfig)

        // Alternatively, a function using varargs
        // Config optional
        val adapterAlternative = RecyclerAdapterBuilder.fromBuilderBinders(
            flowerPotBuilderBinder,
            intBuilderBinder,
            staticABuilderBinder,
            staticBBuilderBinder,
            config = adapterConfig
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}
