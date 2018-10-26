package com.ipd.taxiu.ui.fragment.topic

import android.os.Bundle

class MineTopicListFragment : TopicListFragment() {
    companion object {
        fun newInstance(): MineTopicListFragment {
            val topicListFragment = MineTopicListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", 5)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun needLazyLoad(): Boolean = false


}