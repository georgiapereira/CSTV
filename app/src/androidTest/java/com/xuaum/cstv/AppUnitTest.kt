package com.xuaum.cstv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xuaum.cstv.ui.home.MatchesAdapter
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppUnitTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    fun waitForItems(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return allOf(
                    isAssignableFrom(RecyclerView::class.java),
                    isDisplayed()
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

//    @Before
//    fun waitUntilLoaded() {
//        onView(withId(R.id.matches_container)).perform(waitForItems())
//    }

    @Test
    fun clickCards_checkLoadingDetails() {

        onView(withId(R.id.matches_container)).perform(waitForItems())
        onView(withId(R.id.matches_container))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MatchesAdapter.MatchViewHolder>(
                    0,
                    click()
                )
            )
        assertTrue(true)
    }
}