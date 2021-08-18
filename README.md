# Liquid Navbar

* Liquid Navbar Provides a bottom navigation view with liquid animation.

  ![ezgif.com-gif-maker__3_](/media/LiquidNavbarGif.gif)

# Usage

### Dependencies

* Step 1. Add the JitPack repository to your build file

  Add it in your root build.gradle at the end of repositories:

    ```groovy
	    allprojects {
		    repositories {
			    ...
			    maven { url 'https://jitpack.io' }
		    }
	    }
    ``` 
* Step 2. Add the dependency
    
    Add it in your app module build.gradle:
    
    ```groovy
        dependencies {
            ...
            
        }
    ``` 

### Implementation

* Step 1. Add Liquid Navbar in to your activity_main.xml:

```xml

<com.mindinventory.liquidnavbar.LiquidNavBar 
        android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:backgroundTintNavigation="@color/colorPrimary"
        app:layout_constraintBottom_toBottomOf="parent"
        app:menu="@menu/menu_bottom_navigation" />
```
* Step 2. Add this Line to your fragment for circular reveal animation in fragment transition.

    > view.startCircularReveal()

```kotlin 
   .....

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            view.startCircularReveal()

        }

    .....
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
We’d be really happy if you send us links to your projects where you use our component. Just send an email to sales@mindinventory.com And do let us know if you have any questions or suggestion regarding our work.



