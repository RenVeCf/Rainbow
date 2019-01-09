package com.ipd.rainbow.utils

import com.ipd.rainbow.widget.guideview.Component
import com.ipd.rainbow.widget.guideview.Guide
import com.ipd.rainbow.widget.guideview.GuideBuilder
import com.ipd.rainbow.widget.guideview.component.*

object GuideUtil {
    fun getAddPetGuide(targetId: Int, visibilityChangedListener: GuideBuilder.OnVisibilityChangedListener): Guide {
        val builder = GuideBuilder()
        builder.setTargetViewId(targetId)
                .setAlpha(150)
                .setHighTargetCorner(30)
                .setOverlayTarget(false)
                .setOutsideTouchable(false)
        builder.setOnVisibilityChangedListener(visibilityChangedListener)

        builder.addComponent(AddPetComponent())
        val guide = builder.createGuide()
        guide.setShouldCheckLocInWindow(true)
        return guide
    }

    fun getStoreGuide(targetId: Int, visibilityChangedListener: GuideBuilder.OnVisibilityChangedListener): Guide {
        val builder = GuideBuilder()
        builder.setTargetViewId(targetId)
                .setAlpha(150)
                .setHighTargetCorner(30)
                .setOverlayTarget(false)
                .setOutsideTouchable(false)
        builder.setOnVisibilityChangedListener(visibilityChangedListener)

        builder.addComponent(GuideStoreComponent())
        val guide = builder.createGuide()
        guide.setShouldCheckLocInWindow(true)
        return guide
    }


    fun getHomeCategoryGuide(targetId: Int, component: Component, visibilityChangedListener: GuideBuilder.OnVisibilityChangedListener): Guide {
        val builder = GuideBuilder()
        builder.setTargetViewId(targetId)
                .setAlpha(150)
                .setHighTargetCorner(30)
                .setOverlayTarget(false)
                .setOutsideTouchable(false)
        builder.setOnVisibilityChangedListener(visibilityChangedListener)

        builder.addComponent(component)
        val guide = builder.createGuide()
        guide.setShouldCheckLocInWindow(true)
        return guide
    }


    fun getMineGuide(targetId: Int, visibilityChangedListener: GuideBuilder.OnVisibilityChangedListener): Guide {
        val builder = GuideBuilder()
        builder.setTargetViewId(targetId)
                .setAlpha(150)
                .setHighTargetCorner(30)
                .setOverlayTarget(false)
                .setOutsideTouchable(false)
        builder.setOnVisibilityChangedListener(visibilityChangedListener)

        builder.addComponent(GuideMineComponent())
        val guide = builder.createGuide()
        guide.setShouldCheckLocInWindow(true)
        return guide
    }

    fun getSignGuide(targetId: Int, visibilityChangedListener: GuideBuilder.OnVisibilityChangedListener): Guide {
        val builder = GuideBuilder()
        builder.setTargetViewId(targetId)
                .setAlpha(150)
                .setOverlayTarget(false)
                .setOutsideTouchable(false)
        builder.setOnVisibilityChangedListener(visibilityChangedListener)

        builder.addComponent(GuideSignInComponent())
        val guide = builder.createGuide()
        guide.setShouldCheckLocInWindow(true)
        return guide
    }

    fun getIntegralGuide(targetId: Int, visibilityChangedListener: GuideBuilder.OnVisibilityChangedListener): Guide {
        val builder = GuideBuilder()
        builder.setTargetViewId(targetId)
                .setAlpha(150)
                .setOverlayTarget(false)
                .setOutsideTouchable(false)
        builder.setOnVisibilityChangedListener(visibilityChangedListener)

        builder.addComponent(GuideIntegralComponent())
        val guide = builder.createGuide()
        guide.setShouldCheckLocInWindow(true)
        return guide
    }

}