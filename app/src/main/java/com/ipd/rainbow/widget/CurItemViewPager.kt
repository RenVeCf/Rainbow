package com.ipd.rainbow.widget

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

abstract class CurFragmentPagerAdapter : FragmentPagerAdapter {

    constructor(fm: FragmentManager?) : super(fm)

    var mCurrentFragment: Fragment? = null


    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
        mCurrentFragment = `object` as Fragment
        super.setPrimaryItem(container, position, `object`)
    }

}