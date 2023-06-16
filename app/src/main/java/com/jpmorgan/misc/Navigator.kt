package com.jpmorgan.misc

class Navigator {

    public interface FragmentSelectedListener {
        fun showFragment(tag: Fragment)
        fun popTillFragment( tag:String,  flag:Int)
        fun popTopMostFragment()
    }

   public sealed class Fragment {
        object Home: Fragment()
        object Search: Fragment()
        object Detail: Fragment()
    }
}