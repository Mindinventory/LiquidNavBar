package com.mindinventory.liquidnavbarsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    var images: ArrayList<Int> = arrayListOf()

    abstract fun initRes(): Int
    abstract fun initView(view: View)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(initRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
//        view.startCircularReveal()

//        val hyperspaceJump: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
//        view.startAnimation(hyperspaceJump)
    }

}