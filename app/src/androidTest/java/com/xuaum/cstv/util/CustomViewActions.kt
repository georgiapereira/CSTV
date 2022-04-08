package com.xuaum.cstv.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

object CustomViewActions {
    fun waitForItems(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return Matchers.allOf(
                    ViewMatchers.isAssignableFrom(RecyclerView::class.java),
                    ViewMatchers.isDisplayed()
                )
            }

            override fun getDescription(): String {
                return "Wait for items of View to appear."
            }

            override fun perform(uiController: UiController, view: View?) {
                val recyclerView: RecyclerView = view as RecyclerView

                while (recyclerView.adapter?.itemCount == 0) {
                    uiController.loopMainThreadForAtLeast(200)
                }

            }
        }
    }

    fun waitForViewGone(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return ViewMatchers.isAssignableFrom(View::class.java)
            }

            override fun getDescription(): String {
                return "Wait for View to not be visible."
            }

            override fun perform(uiController: UiController, view: View?) {
                while (view?.visibility == View.VISIBLE) {
                    uiController.loopMainThreadForAtLeast(200)
                }

            }
        }
    }
}