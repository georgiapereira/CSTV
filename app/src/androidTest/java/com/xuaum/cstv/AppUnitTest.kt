package com.xuaum.cstv

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.xuaum.cstv.ui.home.MatchesAdapter
import com.xuaum.cstv.util.CustomViewActions.waitForItems
import com.xuaum.cstv.util.CustomViewActions.waitForViewGone
import com.xuaum.cstv.util.CustomViewMatchers.hasAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class AppUnitTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickCards_checkDetailsLoading() {

        repeat(20) { iteration ->

            onView(withId(R.id.matches_loading))
                .perform(waitForViewGone())

            onView(withId(R.id.matches_container))
                .check(matches(hasAdapter()))
                .perform(
                    waitForItems(),
                    RecyclerViewActions.actionOnItemAtPosition<MatchesAdapter.MatchViewHolder>(
                        iteration,
                        click()
                    )
                )

            onView(withId(R.id.teams_loading))
                .perform(waitForViewGone())

            onView(withId(R.id.team1_players_container))
                .check(matches(hasAdapter()))

            onView(withId(R.id.back_button))
                .perform(click())
        }
    }
}