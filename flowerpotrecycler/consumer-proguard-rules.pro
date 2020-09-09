# For reflective view construction to work with proguard, we need to keep the constructors we use

-keepclassmembers class * implements com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView {
    public <init>(android.content.Context);
}

-keepclassmembers public class * extends android.view.View {
    public <init>(android.content.Context);
}
