package com.lukeneedham.flowerpotrecyclersample.ui.feature.multi.number

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.ui.feature.multi.IntItemView
import com.lukeneedham.flowerpotrecyclersample.ui.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_recyclerview_layout.*

class NumberAdapterFragment : Fragment(R.layout.fragment_recyclerview_layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The config is of the shared super-type
        // We want to show ints and doubles, so the shared-super class is Number
        val numberAdapterConfig = AdapterConfig<Number, View>().apply {
            items = listOf(1, 2, 3.0)
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { item, position ->
                showSnackbar(item.toString())
            }
        }

        // This BuilderBinder says: when an item of type Int is encountered,
        // delegate its building and binding to IntItemView
        val intBuilderBinder =
            RecyclerItemViewBuilderBinder.create<Int, IntItemView>()
        val doubleBuilderBinder =
            RecyclerItemViewBuilderBinder.create<Double, DoubleItemView>()

        // Multi-type adapter from type registry
        // Config optional
        val numberRegistry = BuilderBinderRegistry<Number, View>(
            listOf(intBuilderBinder, doubleBuilderBinder)
        )
        val numberAdapter =
            RecyclerAdapterBuilder.fromBuilderBinderRegistry(numberRegistry, numberAdapterConfig)

        // Alternatively, a function using varargs
        // Config optional
        val numberAdapterAlternative = RecyclerAdapterBuilder.fromBuilderBinders(
            intBuilderBinder,
            doubleBuilderBinder,
            config = numberAdapterConfig
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = numberAdapter
        }
    }
}
