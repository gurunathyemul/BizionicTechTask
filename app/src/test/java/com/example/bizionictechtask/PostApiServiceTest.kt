package com.example.bizionictechtask

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.remote.PostApiServiceTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ApiServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: PostApiServiceTest

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.start()

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getPosts returns expected data`() = runBlocking {
        val mockResponse = """
            [
                {"id":1, "title":"Post 1", "body":"This is post 1"},
                {"id":2, "title":"Post 2", "body":"This is post 2"}
            ]
        """
        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val posts = apiService.getListPosts()

        assertEquals(2, posts.size)
        assertEquals("Post 1", posts[0].title)
        assertEquals("This is post 1", posts[0].body)
    }
}
    }
