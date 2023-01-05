package com.dev.miasnikoff.bookworm

import com.dev.miasnikoff.bookworm.utils.rx.HomeTask
import io.reactivex.observers.TestObserver
import org.junit.Test

class HomeTaskTest {

    @Test
    fun testCreatingRepeatedObservable() {
        val observable = HomeTask.createRepeatedObservable(7)
        val observer = TestObserver<Int>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValueCount(7)
        observer.assertValues(5, 5, 5, 5, 5, 5, 5)
    }

    @Test
    fun testCreatingRangeObservable() {
        val observable = HomeTask.rangeObservable(1, 4)
        val observer = TestObserver<Int>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues(1, 2, 3, 4)
    }

    @Test
    fun testCreatingEvenNumbersObservable() {
        val observable = HomeTask.evenNumbersObservable(1, 4)
        val observer = TestObserver<Int>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues(2, 4)
    }

    @Test
    fun testCreatingMultipleOfThreeObservable() {
        val observable = HomeTask.multipleOfThreeObservable(1, 10)
        val observer = TestObserver<Int>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues(3, 6, 9)
    }

    @Test
    fun testCreatingMultipleOfThreeAndFiveObservable() {
        val observable = HomeTask.multipleOfThreeAndFiveObservable(1, 10)
        val observer = TestObserver<Int>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues(3, 5, 6, 9, 10)
    }

    @Test
    fun testMergeObservable() {
        val items1 = listOf("one", "two", "three", "four")
        val items2 = listOf("one", "three", "five", "seven")
        val observable = HomeTask.mergeObservable(items1, items2)
        val observer = TestObserver<String>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues("one", "two", "three", "four", "one", "three", "five", "seven")
    }

    @Test
    fun testUniqueItemsObservable() {
        val items1 = listOf("one", "two", "three", "four")
        val items2 = listOf("one", "three", "five", "seven")
        val observable = HomeTask.uniqueItemsObservable(items1, items2)
        val observer = TestObserver<String>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues("one", "two", "three", "four", "five", "seven")
    }

    @Test
    fun testHashCodesObservable() {
        val items1 = listOf("one", "two")
        val items2 = listOf("one", "five")
        val observable = HomeTask.hashCodesObservable(items1, items2)
        val observer = TestObserver<Int>()
        observable.subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValues(
            items1[0].hashCode(),
            items1[1].hashCode(),
            items2[0].hashCode(),
            items2[1].hashCode()
        )
    }
}