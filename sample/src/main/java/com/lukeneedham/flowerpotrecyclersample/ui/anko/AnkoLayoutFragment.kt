package com.lukeneedham.flowerpotrecyclersample.ui.anko

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

class AnkoLayoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return UI {
            linearLayout {
                recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    val config = AdapterConfig<FlowerPotModel>().apply {
                        items = FlowerPotDatabase.getAllEntries()
                        addOnItemClickListener { item, position ->
                            val context = requireContext()
                            Toast.makeText(
                                context,
                                context.getString(item.nameResId),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    adapter = RecyclerAdapterBuilder.fromDeclarativeDsl(config) {
                        UI {
                            linearLayout {
                                imageView().apply {
                                    scaleType = ImageView.ScaleType.CENTER_CROP

                                    onItem {
                                        setImageResource(it.imageResId)
                                    }
                                }.lparams {
                                    margin = dip(8)
                                    leftMargin = dip(20)
                                    width = dip(100)
                                    height = dip(100)
                                }

                                textView().apply {
                                    textSize = 20f
                                    gravity = Gravity.CENTER
                                    lparams {
                                        margin = dip(8)
                                        width = matchParent
                                        height = matchParent
                                    }

                                    onItem {
                                        setText(it.nameResId)
                                    }
                                }
                            }
                        }.view
                    }
                }.lparams(matchParent, wrapContent)
            }
        }.view
    }
}
