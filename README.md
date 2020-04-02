# FlowerPotRecycler
Are you sick of writing an Adapters for every RecyclerView? Me too. Never write an adapter again &ast;.

&ast; Or at least make it less painful.

# To Setup:

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
  implementation 'com.github.lukeneedham:flowerpotrecycler:5.5.3'
}
```

Latest release:
[![](https://jitpack.io/v/LukeNeedham/FlowerPotRecyclerDSL.svg)](https://jitpack.io/#LukeNeedham/FlowerPotRecyclerDSL)

# To Use:
Create a RecyclerView as normal, then either use:
    - One of the `RecyclerView.setupWith...` extension functions (if you don't need a reference to the adapter)
    - One of the static factories from `RecyclerAdapterBuilder`, and set it as the RecyclerView adapter (if you need a reference to the adapter)

# Sample
FlowerPotRecyclerSample - https://github.com/LukeNeedham/FlowerPotRecyclerSample

# Dedicated Item View:

The recommended approach is to encapsulate your RecyclerView Item binding logic in its own custom View class. This class needs to implement `SimpleRecyclerItemView<MyItemType>`.

Here's an example:

```kotlin
class MyRecyclerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), SimpleRecyclerItemView<MyItemType> {

    init {
        // Setup your View however you like, just like in any normal custom or compound View
    }

    override fun setItem(position: Int, item: MyItemType) {
        // Write your binding logic here
    }
}
```

Then, setup your adapter to use this View class:

If you don't need to a hold a reference to the adapter (for example, if the data will never change), use one of the `RecyclerView.setupWithView` extension functions.
For example:

`recyclerView.setupWithView(listOfItems) { MyRecyclerItemView(it) }`

If you want a reference to the adapter (for example, so you can use `adapter.submitList` to update data), use one of the `RecyclerAdapterBuilder.fromView` functions.
For example:

```kotlin
val adapter = RecyclerAdapterBuilder.fromView { MyRecyclerItemView(it) }
recyclerView.adapter = adapter
```

That's it! However, you may also need to manually set LayoutParams when you create your item View - see: [Custom Item LayoutParams](#Custom-Item-LayoutParams)

For a full example, see:
https://github.com/LukeNeedham/FlowerPotRecyclerDSL-Sample/blob/master/app/src/main/java/com/lukeneedham/flowerpotrecyclersample/ViewClassFragment.kt

# Alternative Adapter Creation Methods

Along with the recommended custom View approach, FlowerPotRecycler provides a number of alternative methods to auto-generate adapters:

<details>
  <summary>From XML resource</summary>
  
  This allows you to create an adapter which will display each item by inflating the provided layout resource, and bind it using the provided binding function.
  
  For example:
  
  ```kotlin
  recyclerView.setupWithXml(listOfItems, R.layout.pot_recycler_item_view) { position, item, itemView ->
      // This is your binding logic
      itemView.potImageView.setImageResource(item.imageResId)
      itemView.potNameTextView.setText(item.nameResId)
  }
  ```
  
  To hold a reference to the adapter, there is the functionally identical `RecyclerAdapterBuilder.fromXml` function.
  
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
  
  To hold a reference to the adapter, there is the functionally identical `RecyclerAdapterBuilder.fromDeclarativeDsl` function.
  
  For a full example, see:
  https://github.com/LukeNeedham/FlowerPotRecyclerDSL-Sample/blob/master/app/src/main/java/com/lukeneedham/flowerpotrecyclersample/AnkoLayoutFragment.kt
  
</details>

<details>
  <summary>Completely custom</summary>
  
  There is also a more generic option, if you want to supply your own custom BuilderBinder. This allows you to specify custom functions for creating your item views, and binding items to them.
  
  `fun <ItemType> RecyclerView.setupWithBuilderBinder(items: List<ItemType>, builderBinder: BuilderBinder<ItemType>)`
  
  (But at this point you might be better off actually writing an adapter yourself)
  
</details>

# But what if I really really really need a custom adapter?

Then FlowerPotRecycler eases the pain.

In some cases you really do need your own custom adapter - for example, if you want to make RecyclerView items selectable.

To minimise the boilerplate needed in your adapters in these cases, extend from `SimpleRecyclerAdapter`. For example:

```kotlin
class MyAdapter : SimpleRecyclerAdapter<MyItemType, MyRecyclerItemView>() {
    override fun createItemView(context: Context): MyRecyclerItemView {
        // Create your item view here
    }
}
```

That's it! You can then add whatever else you want in your adapter.

You can of course also override the standard RecyclerView methods, like `onBindViewHolder`, to supplement or replace the behaviour provided by `SimpleRecyclerAdapter`.

Within `SimpleRecyclerAdapter` you can also access and change configurations like:
- `positionDelegate` (responsible for positioning items and performing diffs when items change)
- `itemViewLayoutParams` (provides default layout params to the item views)

# Other cool features:

<details>
  <summary>Cyclic / Infinite Scrolling</summary>
  
You can easily make a RecyclerView 'cyclic' (also called wrap-around / endless / infinite). This means that after the last item in the items list, the entire list repeats again.

```kotlin
val adapter = RecyclerAdapterBuilder.fromView { MyRecyclerItemView(it) }
adapter.isCyclic = true
```

# Bi-directional Infinite Scrolling

If you want your RecyclerView to be cyclic in both directions (so that scrolling backwards also repeats the list), you need to manually set your RecyclerView position to the middle of the list:

`recyclerView.layoutManager.scrollToPosition(recyclerView.adapter.itemCount / 2)`

Or use the extension function provided in this library:

`recyclerView.scrollToCenter()`
  
</details>

<details>
  <summary>Updating RecyclerView Data</summary>
  
You may also wish to update your RecyclerView items.

For this you can create an adapter using one of the `RecyclerAdapterBuilder` functions, and then use `submitList(...)` on the returned adapter.

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
  
</details>

<details>
  <summary>Custom Item LayoutParams</summary>
  

  
</details>

# Custom Item LayoutParams

It is also sometimes useful to provide LayoutParams to the item view of the RecyclerView.

This is especially true when using a custom View:
Otherwise, the width and height of the view may not be set correctly, as they cannot be inferred from an XML layout. This issue is discussed here: https://stackoverflow.com/a/48123513

LayoutParams for item views can be set on the adapter itself via `itemViewLayoutParams`. For example:

```kotlin
val adapter = RecyclerAdapterBuilder.setupWith...
adapter.itemViewLayoutParams =
  RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
    leftMargin = 10
    rightMargin = 10
}
```

These should be `RecyclerView.LayoutParams`.

It can also of course be done explicitly if you provide a function for constructing your item views:

```kotlin
val adapter = RecyclerAdapterBuilder.fromView {
    MyRecyclerItemView(it).apply {
        layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }
}
```

# Help! My RecyclerView isn't showing anything!

1 - Make sure you've set a `LayoutManager` ;-)

2 - Make sure you've supplied data to your adapter (via `submitList` if no initial data is supplied)

3 - If using a compound view, make sure you set LayoutParams: [Custom Item LayoutParams](#Custom-Item-LayoutParams)

# Limitations:

FlowerPotRecycler cannot currently handle multiple view types. This is currently in progress.