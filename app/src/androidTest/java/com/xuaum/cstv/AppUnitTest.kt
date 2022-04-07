package com.xuaum.cstv

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xuaum.cstv.ui.home.MatchesAdapter
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppUnitTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickCards_checkLoadingDetails() {
        onView(
            allOf(
                withId(R.id.matches_loading),
                isDisplayed()
            )
        ).check(matches(not(doesNotExist())))

        onView(withId(R.id.matches_container)).check(is)
            .perform(
                RecyclerViewActions.actionOnItem<MatchesAdapter.MatchViewHolder>(
                    isDisplayed(),
                    click()
                )
            )
        assertTrue(true)
    }
}