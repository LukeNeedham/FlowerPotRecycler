# FlowerPotRecycler
Are you sick of writing an Adapter for every RecyclerView? Me too. Never write an adapter again &ast;.

&ast; Or at least make it less painful.

# To Setup

0. (If you aren't already using JitPack) Add JitPack in your root build.gradle, at the end of repositories:

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
  
1. Add the dependency for FlowerPotRecycler:

```groovy
dependencies {
  implementation 'com.github.lukeneedham:flowerpotrecycler:6.0.2'
}
```

Latest release:
[![](https://jitpack.io/v/LukeNeedham/FlowerPotRecycler.svg)](https://jitpack.io/#LukeNeedham/FlowerPotRecycler)

# To Use:
Create a RecyclerView as normal, then create an adapter with one of the static factories from `RecyclerAdapterBuilder`

# Sample
See https://github.com/LukeNeedham/FlowerPotRecycler/tree/master/sample

# Dedicated Item View

The recommended approach is to encapsulate your RecyclerView Item binding logic in its own custom View class. This class needs to implement `RecyclerItemView<MyItemType>`.

Here's an example:

```kotlin
class MyRecyclerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<MyItemType> {

    init {
        // Setup your View however you like, just like in any normal custom or compound View
    }

    override fun setItem(position: Int, item: MyItemType) {
        // Write your binding logic here
    }
}
```

Then, setup your adapter to use this View class:

```kotlin
val adapter = RecyclerAdapterBuilder.fromView<MyItemType, MyRecyclerItemView>()
recyclerView.adapter = adapter
```

That's it! For more advanced options, read on!

# Config

You can pass an optional RecyclerAdapterConfig to every `RecyclerAdapterBuilder` function.

This config contains parameters used for setting up the auto-generated Adapter.

To create a config, most of the time you can simply create an instance of `AdapterConfig`, which contains a useful default configuration.
There is also the option to implement `RecyclerAdapterConfig` yourself.

In `AdapterConfig` are the configuration options:
- `items` - a list of items to show in the Adapter. Can always be updated with `Adapter.submitList`. Defaults to an empty list.
- `featureDelegateCreators` - a list of functions to instantiate `AdapterFeatureDelegate`s. More on them later. Defaults to an empty list (no feature delegates)
- `positionDelegateCreator` - a function to instantiate the `AdapterPositionDelegate`. More on that later. Defaults to instantiate `null` (uses the default position delegate)

# AdapterFeatureDelegate

Each `AdapterFeatureDelegate` hooks into Adapter calls to provide an encapsulated and re-usable component for a certain feature.

You can easily create your own delegate by implementing `AdapterFeatureDelegate`.

A number of common delegates are also provided in the library. They are:
- `OnItemClickDelegate` - adds an on item click listener to the Adapter
- `ItemLayoutParamsDelegate` - adds layout params to your item views. This is especially important for Adapters built from a `RecyclerItemView` class, as layout params cannot be inferred from custom Views.
- `SelectableItemDelegate`- a delegate that allows for up to 1 item in the Adapter to be selected at a time

# AdapterPositionDelegate

The `AdapterPositionDelegate` is responsible for positioning items in the Adapter. 
Most of the time, you won't need to worry about this - in the majority of cases, the default `AdapterPositionDelegate` is what you want.

The default delegate is a `LinearPositionDelegate` (shows list items in order from first to last) using the `DefaultDiffCallback` (checks for object equality).

The other provided position delegate is the `CyclicPositionDelegate`, which makes the RecyclerView cyclic - that is, the list of items wraps-around.
To allow the user to scroll infinitely in both directions, you need to first center the RecyclerView. You can use the extension provided in this library:
`recyclerView.scrollToCenter()`

You can also easily create your own position delegate by implementing `AdapterPositionDelegate`.

If you want to use a different diff callback, or a different position delegate, set the `positionDelegateCreator` in `RecyclerAdapterConfig`.

# Config example

There are also a number of utility extensions on `RecyclerAdapterConfig`, provided to make configuration as easy as possible. 
They can be seen in `flowerpotrecycler/util/RecyclerAdapterConfig.kt`.
Some of them are shown off in the following example.

```kotlin
val config = AdapterConfig<FlowerPotModel>().apply {
    items = listOf(item1, item2, item3)
    // Extension function to add an `ItemLayoutParamsDelegate`
    addItemLayoutParams(
        // Configure your layout params however you like
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        ).apply {
            leftMargin = 10
            rightMargin = 10
        }   
    )
    // Extension function to add an `OnItemClickDelegate`
    addOnItemClickListener { item, position ->
        val context = requireContext()
        Toast.makeText(context, context.getString(item.nameResId), Toast.LENGTH_SHORT)
            .show()
    }
    // Extension function to use the `CyclicPositionDelegate` with the `DefaultDiffCallback`
    // This makes the items wrap-around
    setCyclic()
}

val recyclerAdapter = RecyclerAdapterBuilder.fromView<FlowerPotModel, FlowerPotItemView>(config)

recyclerView.adapter = recyclerAdapter
recyclerView.layoutManager = LinearLayoutManager(context)
```

# Alternative Adapter Creation Methods

Along with the recommended custom View approach, FlowerPotRecycler provides a number of alternative methods to auto-generate adapters:

<details>
  <summary>From XML resource</summary>
  
  This allows you to create an adapter which will display each item by inflating the provided layout resource, and bind it using the provided binding function.
  
  For example:
  
  ```kotlin
  val adapter = RecyclerAdapterBuilder.fromXml(R.layout.pot_recycler_item_view) { position, item, itemView ->
      // This is your binding logic
      itemView.potImageView.setImageResource(item.imageResId)
      itemView.potNameTextView.setText(item.nameResId)
  }
  ```
  
  For a full example, see:
  https://github.com/LukeNeedham/FlowerPotRecyclerDSL-Sample/blob/master/app/src/main/java/com/lukeneedham/flowerpotrecyclersample/XmlLayoutFragment.kt
  
</details>

<details>
  <summary>With declarative DSL (Anko, Compose)</summary>
  
  FlowerPotRecycler provides a binding DSL, with the function `onItem(...)`.
  This allows you to add a callback to bind the item to the view. Useful when using a declarative UI, like Anko or Compose.
  
  `fun <ItemType> RecyclerView.setupWithDeclarativeDsl(items: List<ItemType>, builder: DataBindingDsl<ItemType>.(ViewGroup) -> View)`
  
  If using Anko in a Fragment, this might look like:
  ```kotlin
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
                      adapter = RecyclerAdapterBuilder.fromDeclarativeDsl(recyclerData) { parent ->
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
  
</details>

# But what if I really really really need a custom adapter?

Then FlowerPotRecycler eases the pain.

In some cases you really do need your own custom adapter - for example, if you want to make RecyclerView items selectable.

To minimise the boilerplate needed in your adapters in these cases, extend from `DelegatedRecyclerAdapter`, or `DefaultDelegatedRecyclerAdapter` to get useful defaults.

See `CustomRecyclerAdapter` for a full example.

You can of course also override the standard RecyclerView methods, like `onBindViewHolder`, to supplement or replace the provided behaviour.

Within `DelegatedRecyclerAdapter` you can also access and change configurations like:
- `featureDelegates` (list of delegates providing extra composable functionality)
- `positionDelegate` (responsible for arranging items in the Adapter)

# Updating RecyclerView Data
  
You may wish to update your RecyclerView items.

For this you can use `submitList(...)` on your adapter.

Updates are calculated asynchronously using DiffUtil, allowing changes to be animated.

You can also pass a callback to `submitList`, which will be called when the asynchronous diff process completes. This can be useful when you need to position the recyclerview at a certain item, for example:

```kotlin
val adapter = RecyclerAdapterBuilder.fromView { MyRecyclerItemView(it) }

// When data is recieved from DB / API / whenever it's ready:
adapter.submitList(newData) {
    // Scroll to the end of the RecyclerView - this can only be done when the diff is done and items are laid out
    recyclerView.scrollToPosition(newData.lastIndex)
}
```

By default, the diff is done using a default `DiffCallback`, which just checks for object equality. To provide a custom `DiffCallback`, provide a `positionDelegateCreator` in `RecyclerAdapterConfig`  

# Help! My RecyclerView isn't showing anything!

1 - Make sure you've set a `LayoutManager` ;-)

2 - Make sure you've supplied data to your adapter (via `submitList` if no initial data is supplied)

3 - If using a compound view, make sure you set LayoutParams (see `ItemLayoutParamsDelegate`)

# Limitations

FlowerPotRecycler cannot currently handle multiple view types. This is currently in progress.
