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
            withItems(recyclerData, R.layout.pot_recycler_item_view) { item, itemView ->
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
        return UI {
            linearLayout {
                recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    withItems(FlowerPotDatabase.getAllEntries()) { parent ->
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

# Limitations:

FlowerPotRecycler cannot currently handle multiple view types, or updating data.
