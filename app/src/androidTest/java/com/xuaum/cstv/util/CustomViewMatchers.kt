package com.xuaum.cstv.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

object CustomViewMatchers {
    fun hasAdapter(): BoundedMatcher<View, RecyclerView> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("has adapter attached")
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                return item?.adapter != null
            }

        }
    }
}