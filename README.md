# Liquid Navbar

* Liquid Navbar Provides a bottom navigation view with liquid animation.

  ![ezgif.com-gif-maker__3_](/media/features.gif)

# Usage

### Dependencies

* Step 1. Add the JitPack repository to your build file

  Add it in your root build.gradle at the end of repositories:

    ```groovy
	    allprojects {
		    repositories {
			    maven { url 'https://jitpack.io' }
		    }
	    }
    ``` 
* Step 2. Add the dependency
    
    Add it in your app module build.gradle:

    ```groovy
        dependencies {
            
            
        }
    ``` 

### Implementation

* Step 1. Add Liquid Navbar in to your activity_main.xml:

```xml

<com.mindinventory.liquidnavbar.ui.LiquidNavBar android:id="@+id/bottomNavigationView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:backgroundTintNavigation="@color/colorPrimary"
        app:layout_constraintBottom_toBottomOf="parent"
        app:menu="@menu/menu_bottom_navigation" />
```

* Step 2. Provide fragment's parent view and implement animation listener

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

* Step 3. Implement navigation listener

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



