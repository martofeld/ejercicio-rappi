package com.mfeldsztejn.rappitest.utils

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DateUtilsTest {

    @ParameterizedTest
    @MethodSource("dates")
    fun `Is one month old`(testValue: TestValue) {
        Assertions.assertThat(DateUtils.isOneMonthOld(testValue.value)).isEqualTo(testValue.expected)
    }

    @SuppressWarnings("unused")
    fun dates() = listOf(
            TestValue(Date().time, false),
            TestValue(Date().time - TimeUnit.DAYS.toMillis(15), false),
            TestValue(Date().time - TimeUnit.DAYS.toMillis(30), false),
            TestValue(Date().time - TimeUnit.DAYS.toMillis(31), true),
            TestValue(Date().time - TimeUnit.DAYS.toMillis(32), true),
            TestValue(Date().time - TimeUnit.DAYS.toMillis(45), true),
            TestValue(Date().time - TimeUnit.DAYS.toMillis(365), true)
    )

    data class TestValue(val value: Long, val expected: Boolean)
}