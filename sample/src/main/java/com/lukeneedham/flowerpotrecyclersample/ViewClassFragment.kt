package com.lukeneedham.flowerpotrecyclersample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.addOnItemClickListener
import kotlinx.android.synthetic.main.fragment_xml_layout.*

class ViewClassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = AdapterConfig<FlowerPotModel>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { item, position ->
                val context = requireContext()
                Toast.makeText(context, context.getString(item.nameResId), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // Config optional
        val recyclerAdapter =
            RecyclerAdapterBuilder.fromView<FlowerPotModel, FlowerPotItemView>(config)

        // Alternatively, we could instantiate the view ourselves. Config optional
        val recyclerAdapterFromViewCreator =
            RecyclerAdapterBuilder.fromViewCreator { FlowerPotItemView(it) }

        // Alternatively, we could also specify the view class manually
        // This is useful when calling from Java. Config optional
        val recyclerAdapterFromViewClass =
            RecyclerAdapterBuilder.fromViewClass(FlowerPotItemView::class.java)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}