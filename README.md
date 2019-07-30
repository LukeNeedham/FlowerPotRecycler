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
  implementation 'com.github.lukeneedham:flowerpotrecycler:1.0.0'
}
```

# To Use:
Use one of the 3 `RecyclerView.withItems(...)` extension methods.

# Sample
FlowerPotRecyclerSample - https://github.com/LukeNeedham/FlowerPotRecyclerSample

# withItems - XML
Create an XML layout containing a RecyclerView as normal. Once the layout has been inflated, use `myRecyclerView.withItems(...)`.

`fun <ItemType> RecyclerView.withItems(items: List<ItemType>, @LayoutRes layoutResId: Int, binder: (ItemType, View) -> Unit)`

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
            withItems(recyclerData, R.layout.pot_recycler_item_view) { position, item, itemView ->
                itemView.potImageView.setImageResource(item.imageResId)
                itemView.potNameTextView.setText(item.nameResId)
            }
        }
    }
```

# withItems - Programmatic (Anko DSL)

FlowerPotRecycler provides a binding DSL with `onItem(...)`.
This allows you to bind the recycler item to the view, from within the Anko DSL itself.

`fun <ItemType> RecyclerView.withItems(items: List<ItemType>, builder: DataBindingDsl<ItemType>.(ViewGroup) -> View)`

In a Fragment, this might look like:
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
                    withItems(recyclerData) { parent ->
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

# withItems - Generic

There is also a more generic option, if you want to supply your own custom BuilderBinder.

`fun <ItemType> RecyclerView.withItems(items: List<ItemType>, builderBinder: BuilderBinder<ItemType>)`

# Advanced options

To hold a reference to an AutoAdapter, use one of the `RecyclerAdapterBuilder.withItems` functions, and set the RecyclerView adapter manually. 

It is also sometimes useful to provide LayoutParams to the item view of the RecyclerView. This can be done by setting `itemViewLayoutParams`. For example:

```
val adapter = RecyclerAdapterBuilder.withItems(...)
adapter.itemViewLayoutParams =
  RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
    leftMargin = 10
    rightMargin = 10
}
```

You may also wish to update your RecyclerView items. For this you can use `submitList(...)`. Updates are calculated using DiffUtil, allowing changes to be animated.

# Limitations:

FlowerPotRecycler cannot currently handle multiple view types.
