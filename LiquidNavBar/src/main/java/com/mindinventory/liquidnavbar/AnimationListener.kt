package com.mindinventory.liquidnavbar

interface AnimationListener {
    fun onValueChange(value:Float,position:Int)
    fun onValueDown(value:Float,position:Int)
    fun onValueSelected(position:Int)
    fun onNavigationItemSelected(indexOfItemSelected: Int)
}