package giniApps.calculator.controller

fun interface MyBinary<T> {
    fun myApply(var1: T, var2: T): T
}