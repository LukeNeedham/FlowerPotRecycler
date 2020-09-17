package com.lukeneedham.flowerpotrecyclersample.ui.feature.anko

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukeneedham.flowerpotrecycler.SingleTypeRecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.adapter.config.SingleTypeAdapterConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import com.lukeneedham.flowerpotrecyclersample.ui.util.showSnackbar
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

class AnkoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return UI {
            linearLayout {
                recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    val config = SingleTypeAdapterConfig<FlowerPotModel, View>().apply {
                        items = FlowerPotDatabase.getAllEntries()
                        addOnItemClickListener { item, _, _ ->
                            showSnackbar(item.nameResId)
                        }
                    }
                    adapter = SingleTypeRecyclerAdapterBuilder.fromDeclarativeDsl(config) {
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
                                    textColor = Color.BLACK
                                    gravity = Gravity.CENTER_VERTICAL
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
