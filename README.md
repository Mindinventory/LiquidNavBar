# LiquidNavBar

* LiquidNavBar Provides a bottom navigation view with liquid animation.

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

* Step 1. Add LiquidTabBar in to your activity_main.xml:

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
| app:backgroundTintNavigation | LiquidTabBar background color |


### Requirements

* minSdkVersion >= 21
* Androidx

## LICENSE!
Copyright (c) 2021 MindInventory

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

# Let us know!
We’d be really happy if you send us links to your projects where you use our component. Just send an email to sales@mindinventory.com And do let us know if you have any questions or suggestion regarding our work.



