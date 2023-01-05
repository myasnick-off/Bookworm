package com.dev.miasnikoff.bookworm.utils.rx

import io.reactivex.Observable

object HomeTask {

    /** Observable который эмитит число 5 n раз */
    fun createRepeatedObservable(n: Int): Observable<Int> =
        Observable.fromIterable(List(n) { 5 })

    /**
     * 1. Создайте Observable, который будет эмитить числа от 1 до 10
     * 2. Преобразуйте Observable так, чтобы он эмитил только четные числа
     * 3. Преобразуйте Observable так, чтобы он эмитил только числа, которые делятся на 3
     * 4. Преобразуйте Observable так, чтобы он эмитил только числа, которые делятся на 3 и 5
     */
    fun rangeObservable(startNum: Int, endNum: Int): Observable<Int> =
        Observable.range(startNum, endNum - startNum + 1)

    fun evenNumbersObservable(startNum: Int, endNum: Int): Observable<Int> {
        return rangeObservable(startNum, endNum)
            .filter { number -> number % 2 == 0 }
    }

    fun multipleOfThreeObservable(startNum: Int, endNum: Int): Observable<Int> {
        return rangeObservable(startNum, endNum)
            .filter { number -> number % 3 == 0 }
    }

    fun multipleOfThreeAndFiveObservable(startNum: Int, endNum: Int): Observable<Int> {
        return rangeObservable(startNum, endNum)
            .filter { number -> number % 3 == 0 || number % 5 == 0 }
    }

    /**
     * 1. Создайте два Observable из соответсвующих последовательностей
     * 2. Объедините два Observable в один
     * 3. Преобразуйте Observable так, чтобы он эмитил только уникальные элементы
     * 4. Преобразуйте Observable так, чтобы он эмитил только хэш коды элементов
     */
    fun mergeObservable(
        items1: List<String>,
        items2: List<String>
    ): Observable<String> {
        val observable1 = Observable.fromIterable(items1)
        val observable2 = Observable.fromIterable(items2)
        return Observable.merge(observable1, observable2)
    }

    fun uniqueItemsObservable(
        items1: List<String>,
        items2: List<String>
    ): Observable<String> {
        return mergeObservable(items1, items2)
            .distinct()
    }

    fun hashCodesObservable(
        items1: List<String>,
        items2: List<String>
    ): Observable<Int> {
        return mergeObservable(items1, items2)
            .map { item -> item.hashCode() }
    }
}