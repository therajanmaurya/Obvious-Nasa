package com.nasa.obvious.ui

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.nasa.obvious.R
import com.nasa.obvious.models.Nasa
import com.nasa.obvious.util.RecyclerViewMatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@HiltAndroidTest
@LargeTest
@RunWith(AndroidJUnit4::class)
class ImageDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: ImageViewModel
    var images = MutableLiveData<Pair<List<Nasa>?, Throwable?>?>()
    val showProgress = MutableLiveData<Boolean>()

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = Mockito.mock(ImageViewModel::class.java)
        Mockito.`when`(viewModel.images).thenReturn(images)
        Mockito.`when`(viewModel.showProgress).thenReturn(showProgress)
    }

    @Test
    fun when_loading_nasa_images_and_progress_bar_visible() {
        images.postValue(Pair(null, null))
        showProgress.postValue(true)

        launchActivity()

        onView(listMatcher().atPosition(0)).perform(click())
        onView(ViewMatchers.withId(R.id.ivNasaPoster)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.tvDate)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.tvDescription)).check(matches(isDisplayed()))
    }

    private fun launchActivity(): ActivityScenario<ImageGridActivity>? {
        val activityScenario = ActivityScenario.launch(ImageGridActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.rvNasa) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.rvNasa)
    }
}