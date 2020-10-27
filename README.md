# FlowerPotRecycler
Are you sick of writing an Adapter for every RecyclerView? Me too. Never write an adapter again &ast;.

&ast; Or at least make it less painful.

# Setup

1. (If you aren't already using JitPack) Add JitPack in your root build.gradle, at the end of repositories:

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
  
2. Add the dependency for FlowerPotRecycler:

```groovy
dependencies {
  implementation 'com.github.lukeneedham:flowerpotrecycler:7.0.0'
}
```

Latest release:
[![](https://jitpack.io/v/LukeNeedham/FlowerPotRecycler.svg)](https://jitpack.io/#LukeNeedham/FlowerPotRecycler)

# Sample

See https://github.com/LukeNeedham/FlowerPotRecycler/tree/master/sample for examples of
all the different ways this library can help you.

# Premise

This library introduces 2 main ideas:

- `ItemTypeDelegate`: A single ItemTypeDelegate is responsible for a single item type, and handles its:
    - Building - Creating the view. Called during `onCreateViewHolder`.
    - Binding - Binding an item to the view. Called during `onBindViewHolder`.
    - Matching - Determines which `ItemTypeDelegate` to use for each item.
    - Features - Extra features to use for this item type - things like item click listeners.
    We can use mix-and-match these `ItemTypeDelegate`s to create complex `Adapter`s in a semantic, type-safe, and composable way.

- `RecyclerItemView`: An interface to be implemented in a custom or compound `View`,
to mark it as the item view to use for a certain item type in a `RecyclerView`.
Each `RecyclerItemView` must implement `setItem`, which is responsible for binding the item provided.

# ItemTypeDelegate

Each `ItemTypeDelegate` is created from an `ItemTypeConfig`, which you need to create. The `ItemTypeConfig` contains a `BuilderBinder` which manages the building and binding of the item view.

All `BuilderBinder`s extend from the `BuilderBinder` class - meaning that you can easily write your own custom implementation!

That said, the library includes the most common `BuilderBinder`s:

- `RecyclerItemViewBuilderBinder`
Used for `RecyclerItemView`s. The builder function is passed as a parameter, and the binder is delegated to `RecyclerItemView.setItem`.

- `ViewBuilderBinder`
Used for generic `View`s. Both builder and binder functions are passed as parameters.

- `XmlBuilderBinder`
Used for XML layouts. The builder simply inflates the XML layout file, and the binder is passed as a parameter.

- `DeclarativeBuilderBinder`
Used for declarative layouts, like Anko DSL. The builder is the declarative layout function, passed as a parameter, and the binder is delegated to DSL `onItem` calls within your original layout.

See the sample for example usages of all of these `BuilderBinder`s.

Each `BuilderBinder` type also provides a static `BuilderBinder.create` factory with a default builder and binder. For example:

```kotlin
// BuilderBinder for a `RecyclerItemView`, built from the reflective View constructor
RecyclerItemViewBuilderBinder.create<FlowerPotModel, FlowerPotItemView>()

// BuilderBinder for a static XML layout, which does no binding
XmlBuilderBinder.create<HeaderItem>(R.layout.view_header_item)
```

# RecyclerItemView

The recommended approach is to encapsulate your item binding logic in its own custom `View` class. This class needs to implement `RecyclerItemView<ItemType>`.

Here's an example:

```kotlin
// This View handles items of type `FlowerPotModel`
class FlowerPotItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<FlowerPotModel> {

    init {
        TODO("Setup your View however you like, just like in any normal custom or compound View")
    }

    override fun setItem(position: Int, item: FlowerPotModel) {
        TODO("Write your binding logic here")
    }
}
```

The recommended approach is to use a `RecyclerItemViewBuilderBinder` with a custom `RecyclerItemView` whenever binding is necessary.
For static views, which require no binding, prefer to use `XmlBuilderBinder` or `ViewBuilderBinder`.

# Multi-type Adapter

Once you've created a `BuilderBinder` for each item type you want to handle,
you need to wrap each `BuilderBinder` in an `ItemTypeConfig`.
Then, simply pass them to a static factory in `RecyclerAdapterCreator`. For example:
```kotlin
recyclerView.adapter = RecyclerAdapterCreator.fromItemTypeConfigs(
    ItemTypeConfig.newInstance<HeaderItem, View>(
        XmlBuilderBinder.newInstance(R.layout.view_header_item)
    ),
    ItemTypeConfig.newInstance<ChoiceItem, ChoiceItemView>(
        RecyclerItemViewBuilderBinder.newInstance()
    ),
    adapterConfig = config
)
```

You can also use the alternative syntax, which saves on boilerplate by using common defaults:
```kotlin
val itemTypeConfig = ItemTypeConfigCreator.fromRecyclerItemView<ChoiceItem, ChoiceItemView> {
    // DSL for adding features
    addOnItemClickListener { item, _, _ -> onItemClick(item) }
}
```

Internally, the Adapter will use the provided `ItemTypeConfig`s to build `ItemTypeDelegate`s, which
are responsible for everything related to a single item type. The Adapter will wrap these
`ItemTypeDelegate`s into an `ItemTypeRegistry`, to which it will delegate responsibly for each item type.

# Single-type Adapter

Sometimes you only need a simple `Adapter` for handling a single type of item. In these cases, it's even easier - the library will create an `Adapter` with a single implicit `BuilderBinder`.

Use one of the static factories from `SingleTypeRecyclerAdapterCreator`.

For example:

```kotlin
// Creates an adapter containing a single default RecyclerItemViewBuilderBinder.
// This adapter will handle items of type `FlowerPotModel` with a `FlowerPotItemView`.
val recyclerAdapter = SingleTypeRecyclerAdapterCreator.fromRecyclerItemView<FlowerPotModel, FlowerPotItemView>()
```

# Config

There are 3 main types of config:

- `ItemTypeConfig` - Contains all the config for a single item type, used to build an `ItemTypeDelegate`
- `FeatureDelegateConfig` - Contains the config for `AdapterFeatureDelegate`s for each item type
- `RecyclerAdapterConfig` - Contains the config for the `Adapter` itself

## FeatureDelegateConfig

The `FeatureDelegateConfig` provides `featureDelegateCreators` - a list of functions to instantiate `AdapterFeatureDelegate`s.

An `AdapterFeatureDelegate` hooks into `Adapter` calls to provide an encapsulated and re-usable component for a certain feature.

You can easily create your own delegate by implementing `AdapterFeatureDelegate`.

A number of common feature delegates are also provided in the library, including:
- `OnItemClickDelegate` - adds an on item click listener to the Adapter
- `ItemLayoutParamsDelegate` - adds layout params to your item views. This is especially important for Adapters built from a `RecyclerItemView` class, as layout params cannot be inferred from custom `View`s.
- `SelectableItemDelegate`- a delegate that allows for up to 1 item in the Adapter to be selected at a time

The `FeatureDelegateConfig` for each item type is passed as a parameter to `ItemTypeConfig`:
```kotlin
ItemTypeConfig.newInstance(
    XmlBuilderBinder.newInstance(R.layout.view_header_item),
    FeatureConfig<HeaderItem, View>().apply {
        addItemLayoutParams(RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
    }
)
```

## RecyclerAdapterConfig

This config contains parameters used for setting up the auto-generated Adapter.

In `RecyclerAdapterConfig` are the configuration options:
- `items` - a list of items to show in the Adapter. Can always be updated later with `Adapter.submitList`. Defaults to an empty list.
- `positionDelegateCreator` - a function to instantiate the `AdapterPositionDelegate`. Defaults to a `LinearPositionDelegate`.

The `AdapterPositionDelegate` is responsible for positioning items in the Adapter. Most of the time, you won't need to worry about this. The default delegate is a `LinearPositionDelegate` (shows list items in order from first to last) using the `DefaultDiffCallback` (checks for object equality).

The other provided position delegate is the `CyclicPositionDelegate`, which makes the RecyclerView cyclic - that is, the list of items wraps-around. To allow infinite scrolling in both directions, you need to center the RecyclerView with the provided extension:
`recyclerView.scrollToCenter()`

You can also easily create your own position delegate by implementing `AdapterPositionDelegate`. To use a different diff callback, you can also pass it as the parameter to `RecyclerAdapterConfig.setLinear` or `RecyclerAdapterConfig.setCyclic`.

You can pass an optional RecyclerAdapterConfig to every `RecyclerAdapterCreator` and `SingleTypeRecyclerAdapterCreator` function.

To create a config, simply instantiate `AdapterConfig` and update the config as desired. Alternatively, you can manually implement `RecyclerAdapterConfig`.

## Config Example

There are also a number of extension functions on `RecyclerAdapterConfig` and `FeatureDelegateConfig`, provided to make configuration as easy as possible.

`SingleTypeAdapterConfig` implements both `RecyclerAdapterConfig` and `FeatureDelegateConfig`, as it provides config for both Adapter and the single item type.

```kotlin
val config = SingleTypeAdapterConfig<FlowerPotModel, FlowerPotItemView>().apply {
    items = listOf(item1, item2, item3)
    // Adds an `ItemLayoutParamsDelegate`
    addItemLayoutParams(
        // Configure your layout params however you like
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        ).apply {
            leftMargin = 10
            rightMargin = 10
        }
    )
    // Adds an `OnItemClickDelegate`
    addOnItemClickListener { item, position ->
        val context = requireContext()
        Toast.makeText(context, context.getString(item.nameResId), Toast.LENGTH_SHORT)
            .show()
    }
    // Uses the `CyclicPositionDelegate` with the `DefaultDiffCallback` to make items wrap-around
    setCyclic()
}
```

# But what if I really really really need a custom adapter?

Then FlowerPotRecycler eases the pain.

In some cases you really do need your own custom adapter - for example, if you want to make RecyclerView items selectable.

You can see an example in `CustomAdapter` in the samples.

To minimise the boilerplate in these cases, extend from `DelegatedRecyclerAdapter`.

You can of course also override the standard RecyclerView methods, like `onBindViewHolder`, to supplement or replace the provided behaviour.

Within `DelegatedRecyclerAdapter` you can also override the `positionDelegate`.

# Updating RecyclerView Data

You may wish to update your RecyclerView items. For this you can use `submitList(...)` on your adapter.

Updates are calculated asynchronously using DiffUtil, allowing changes to be animated. You can also pass a callback to `submitList`, which will be called when the asynchronous diff process completes.

By default, the diff is done using a default `DiffCallback`, which just checks for object equality. To use a different diff callback, pass it as the parameter to `RecyclerAdapterConfig.setLinear` or `RecyclerAdapterConfig.setCyclic`.

# Help! My RecyclerView isn't showing anything!

1 - Make sure you've set a `LayoutManager` ;-)

2 - Make sure you've supplied data to your adapter (via `submitList` if no initial data is supplied in the config)

3 - If using a compound view, make sure you set `LayoutParams` (see `ItemLayoutParamsDelegate`)
