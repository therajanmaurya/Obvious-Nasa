package com.nasa.obvious.ui

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.nasa.obvious.R
import com.nasa.obvious.util.FakeNasaSource
import com.nasa.obvious.models.Nasa
import com.nasa.obvious.util.RecyclerViewMatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@HiltAndroidTest
@LargeTest
@RunWith(AndroidJUnit4::class)
class ImageGridFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: ImageViewModel
    var images = MutableLiveData<Pair<List<Nasa>?, Throwable?>?>()
    val showProgress = MutableLiveData<Boolean>()

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = mock(ImageViewModel::class.java)
        `when`(viewModel.images).thenReturn(images)
        `when`(viewModel.showProgress).thenReturn(showProgress)
    }

    @Test
    fun when_loading_nasa_images_and_progress_bar_visible() {
        images.postValue(Pair(null, null))
        showProgress.postValue(true)

        launchActivity()

        onView(withId(R.id.progressView)).check(matches(isDisplayed()))
        onView(withId(R.id.rvNasa)).check(matches(not(isDisplayed())))
    }

    @Test
    fun when_loading_done_and_response_empty() {
        images.postValue(Pair(arrayListOf(FakeNasaSource.createNasa()), null))
        showProgress.postValue(false)

        launchActivity()

        onView(withId(R.id.progressView)).check(matches(CoreMatchers.not(isDisplayed())))
        onView(withId(R.id.rvNasa)).check(matches(CoreMatchers.not(isDisplayed())))
        onView(withId(R.id.llNoImageOrFailed)).check(matches(isDisplayed()))
        onView(ViewMatchers.withText("Nothing to see")).check(matches(isDisplayed()))
    }

    @Test
    fun when_loading_done_and_nasa_images_visible_on_screen() {
        images.postValue(Pair(arrayListOf(FakeNasaSource.createNasa()), null))
        showProgress.postValue(false)

        launchActivity()

        onView(withId(R.id.progressView)).check(matches(CoreMatchers.not(isDisplayed())))
        onView(withId(R.id.rvNasa)).check(matches(isDisplayed()))
        onView(listMatcher().atPosition(0)).check(matches(isDisplayed()))
    }

    private fun launchActivity(): ActivityScenario<ImageGridActivity>? {
        val activityScenario = launch(ImageGridActivity::class.java)
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