package giniApps.calculator.controller

fun interface MyUnary<T> {
    fun myApply(var1: T): T
}