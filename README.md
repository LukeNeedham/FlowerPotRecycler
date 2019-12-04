# FlowerPotRecycler
Are you sick of writing Adapters for every RecyclerView, in every Android project? Me too. Never write an adapter again.

# To Setup:

0. (If you aren't already using JitPack) Add JitPack in your root build.gradle, at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
  
1. Add the dependency for FlowerPotRecycler:

```
dependencies {
  implementation 'com.github.lukeneedham:flowerpotrecycler:4.0.0'
}
```

Latest release:
[![](https://jitpack.io/v/LukeNeedham/FlowerPotRecyclerDSL.svg)](https://jitpack.io/#LukeNeedham/FlowerPotRecyclerDSL)

# To Use:
Create a RecyclerView as normal, then use one of the `RecyclerView.setupWith...` extension functions.

# Sample
FlowerPotRecyclerSample - https://github.com/LukeNeedham/FlowerPotRecyclerSample

# setupWithXml

`fun <ItemType> RecyclerView.setupWithXml(items: List<ItemType>, @LayoutRes layoutResId: Int, binder: (ItemType, View) -> Unit)`

For example, in a Fragment this might look like:

```
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       
        val recyclerData = listOf(
          Pot(R.string.good_flower_pot, R.drawable.good_flower_pot),
          Pot(R.string.bad_flower_pot, R.drawable.bad_flower_pot),
          Pot(R.string.ugly_flower_pot, R.drawable.ugly_flower_pot)
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setupWithXml(recyclerData, R.layout.pot_recycler_item_view) { position, item, itemView ->
                itemView.potImageView.setImageResource(item.imageResId)
                itemView.potNameTextView.setText(item.nameResId)
            }
        }
    }
```

For a full example, see:
https://github.com/LukeNeedham/FlowerPotRecyclerDSL-Sample/blob/master/app/src/main/java/com/lukeneedham/flowerpotrecyclersample/XmlLayoutFragment.kt

# setupWithDeclarativeDsl

FlowerPotRecycler provides a binding DSL, with the function `onItem(...)`.
This allows you to add a callback to bind the item to the view. Useful when using a declarative UI, like Anko or Compose.

`fun <ItemType> RecyclerView.setupWithDeclarativeDsl(items: List<ItemType>, builder: DataBindingDsl<ItemType>.(ViewGroup) -> View)`

If using Anko in a Fragment, this might look like:
```
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val recyclerData = listOf(
          Pot(R.string.good_flower_pot, R.drawable.good_flower_pot),
          Pot(R.string.bad_flower_pot, R.drawable.bad_flower_pot),
          Pot(R.string.ugly_flower_pot, R.drawable.ugly_flower_pot)
        )

        return UI {
            linearLayout {
                recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    setupWithDeclarativeDsl(recyclerData) { parent ->
                        UI {
                            linearLayout {
                                imageView().apply {
                                    scaleType = ImageView.ScaleType.CENTER_CROP

                                    onItem {
                                        setImageResource(it.imageResId)
                                    }
                                }

                                textView().apply {
                                    textSize = 20f

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
```

For a full example, see:
https://github.com/LukeNeedham/FlowerPotRecyclerDSL-Sample/blob/master/app/src/main/java/com/lukeneedham/flowerpotrecyclersample/AnkoLayoutFragment.kt

# setupWithView

You may wish to contain your binding logic within its own View class. This helps keep your code clean, and facilitates re-use.

To create an adapter from this View class, simply call:

`fun <ItemType, ItemViewType> setupWithView(items: List<ItemType>) where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType>`

where `ItemViewType` is the type of your View class.

```
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerData = listOf(
          Pot(R.string.good_flower_pot, R.drawable.good_flower_pot),
          Pot(R.string.bad_flower_pot, R.drawable.bad_flower_pot),
          Pot(R.string.ugly_flower_pot, R.drawable.ugly_flower_pot)
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setupWithView<Pot, FlowerPotItemView>(recyclerData)
        }
    }
    
    class FlowerPotItemView @JvmOverloads constructor(
      context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr), SimpleRecyclerItemView<FlowerPotModel> {

      init {
          LayoutInflater.from(context).inflate(R.layout.recycler_item_flower_pot, this)
      }

      override fun setItem(position: Int, item: FlowerPotModel, itemView: View) {
          potImageView.setImageResource(item.imageResId)
          potNameTextView.setText(item.nameResId)
      }
    }

```

There is also a Java-accessible version:
```
RecyclerAdapterBuilder.setupWithView(
    items, // List<ItemType>
    itemViewClass // Class<ItemViewType>
)
```

These functions will create an instance of your View class for you, using the View(context: Context) constructor.

If you want to instantiate the View yourself, use:
```
RecyclerView.setupWithView(
    items: List<ItemType>,
    createView: (Context) -> ItemViewType
)
```

For a full example, see:
https://github.com/LukeNeedham/FlowerPotRecyclerDSL-Sample/blob/master/app/src/main/java/com/lukeneedham/flowerpotrecyclersample/ViewClassFragment.kt

# setupWithBuilderBinder

There is also a more generic option, if you want to supply your own custom BuilderBinder.

`fun <ItemType> RecyclerView.setupWithBuilderBinder(items: List<ItemType>, builderBinder: BuilderBinder<ItemType>)`

# Advanced options

To hold a reference to an Adapter, use one of the `RecyclerAdapterBuilder.from...` functions, and set the RecyclerView adapter manually.

1- It is also sometimes useful to provide LayoutParams to the item view of the RecyclerView. This can be done by setting `itemViewLayoutParams`. For example:

```
val adapter = RecyclerAdapterBuilder.setupWith...
adapter.itemViewLayoutParams =
  RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
    leftMargin = 10
    rightMargin = 10
}
```

2- You can easily make a RecyclerView 'cyclic' (also sometimes called wrap-around / endless / infinite). This means that after the last item in the items list, the entire list repeats again.

```
val adapter = RecyclerAdapterBuilder.setupWith...
adapter.isCyclic = true
```
If you want your RecyclerView to be cyclic in both directions (so that scrolling backwards also repeats the list), you need to manually set your RecyclerView position to the middle of the list:
`recyclerView.layoutManager.scrollToPosition(recyclerView.adapter.itemCount / 2)`
Or use the extension function provided in this library:
`recyclerView.scrollToCenter()`

3 -You may also wish to update your RecyclerView items. For this you can use `submitList(...)`. Updates are calculated using DiffUtil, allowing changes to be animated.

# Limitations:

FlowerPotRecycler cannot currently handle multiple view types.
