# Liquid Navbar

* Liquid Navbar Provides a bottom navigation view with liquid animation.

  ![ezgif.com-gif-maker__3_](/media/feature.gif)

# Usage

### Dependencies

* Step 1. Add the JitPack repository to your build file

  Add it in your root build.gradle at the end of repositories:

    ```groovy
	    allprojects {
		    repositories {
                ...
  
			    maven { url 'https://jitpack.io' }
  
                ...
		    }
	    }
    ``` 
* Step 2. Add the dependency

  Add it in your app module build.gradle:

    ```groovy
        dependencies {
            ...
  
            implementation 'com.github.Mindinventory:LiquidNavBar:0.0.1'
  
            ...
        }
    ``` 

### Implementation

* Step 1. Create Menu file("menu_bottom_navigation") with menu items.

    ```xml

<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_camera"
            android:icon="@drawable/home"
            android:title="@string/feed" />
        <item
            android:id="@+id/nav_gallery"
            android:icon="@drawable/ic_gallery"
            android:title="@string/gallery" />
        <item
            android:id="@+id/nav_slideshow"
            android:icon="@drawable/ic_favorite"
            android:title="@string/favorite" />
        <item
            android:id="@+id/nav_manage"
            android:icon="@drawable/ic_setting"
            android:title="@string/settings" />
    </group>

</menu>
```

* Step 2. Add Liquid Navbar in to your activity_main.xml:

```xml

<com.mindinventory.liquidnavbar.ui.LiquidNavBar android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent" android:layout_height="match_parent"
        app:backgroundTintNavigation="@color/colorPrimary"
        app:layout_constraintBottom_toBottomOf="parent" app:menu="@menu/menu_bottom_navigation" />
```

* Step 3. Provide fragment's parent view and implement animation listener

```kotlin 
  bottomNavigationView.setAnimationListener(container, object : ViewAnimationListener {
  override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?, fragment: Fragment?) {
                fragment?.let { changeFragment(it) }
            }


            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
 ```         

* Step 4. Implement navigation listener

```kotlin 
  bottomNavigationView.setNavigationListener(object :
            LiquidNavBar.OnNavigationItemSelectListener {
            override fun onNavigationItemSelected(indexOfItemSelected: Int): Boolean {
                return true
            }

        })
 ```         

| Attributes | Usage |
| ------ | ------ |
| app:backgroundTintNavigation | Liquid Navbar background color |

### Requirements

* minSdkVersion >= 21
* Androidx

## LICENSE!
LiquidNavBar is [MIT-licensed](/LICENSE) 

# Let us know!

We’d be really happy if you send us links to your projects where you use our component. Just send an
email to sales@mindinventory.com And do let us know if you have any questions or suggestion
regarding our work.



