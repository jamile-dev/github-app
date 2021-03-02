package dev.jamile.githubapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.jamile.githubapp.ui.home.HomeFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @Test
    fun `WHEN_user_click_in_some_list_item_THEN_navigate_to_item_details_screen`() {
        // Given
        val navController =
            TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext)
        navController.setGraph(R.navigation.nav_graph)

        val scenario = launchFragmentInContainer<HomeFragment>()

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Await Loading
        Thread.sleep(5000)

        // Perform click on the first item of the list
        onView(withId(R.id.recyclerList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        // Verify if the navigation occur correctly
        assertEquals(navController.currentDestination?.id, R.id.repoDetailFragment)

    }
}
