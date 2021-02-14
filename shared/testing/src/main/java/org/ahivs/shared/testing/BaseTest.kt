package org.ahivs.shared.testing

import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension


@ExtendWith(TaskExecutorRule::class)
abstract class BaseTest {
    @RegisterExtension
    @JvmField
    val coroutinesTestRule = CoroutinesTestRule()


    @OptIn()
    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        coroutinesTestRule.testDispatcher.runBlockingTest(block)
    }
}