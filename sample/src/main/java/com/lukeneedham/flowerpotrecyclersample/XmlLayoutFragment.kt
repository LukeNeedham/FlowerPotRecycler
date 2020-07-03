package com.lukeneedham.flowerpotrecyclersample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.LinearAdapterConfig
import com.lukeneedham.flowerpotrecycler.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.extensions.addOnItemClickListener
import kotlinx.android.synthetic.main.fragment_xml_layout.*
import kotlinx.android.synthetic.main.recycler_item_flower_pot.view.*

class XmlLayoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = LinearAdapterConfig<FlowerPotModel>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
            addOnItemClickListener { item, position ->
                val context = requireContext()
                Toast.makeText(context, context.getString(item.nameResId), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val recyclerAdapter = RecyclerAdapterBuilder.fromXml(
            R.layout.recycler_item_flower_pot,
            config
        ) { position, item, itemView ->
            itemView.potImageView.setImageResource(item.imageResId)
            itemView.potNameTextView.setText(item.nameResId)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}