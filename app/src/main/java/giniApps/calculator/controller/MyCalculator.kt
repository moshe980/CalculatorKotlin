package giniApps.calculator.controller

import java.util.*
import kotlin.math.*

sealed class MathOperation {

    class Binary(val op: MyBinary<Double>) : MathOperation()
    class Unary(val op: MyUnary<Double>) : MathOperation()
    class Const(val op: MyConst<Double>) : MathOperation()

}

class MyCalculator {
    private val operations: MutableMap<String, MathOperation> = mutableMapOf()
    private val statesStack: Stack<String> = Stack()
    private var lastOperator: String = ""
    private var lastVar: Double = 0.0
    private var isFirstCalc: Boolean = true


    init {

        operations["+"] = MathOperation.Binary { x, y -> x + y }
        operations["-"] = MathOperation.Binary { x, y -> x - y }
        operations["×"] = MathOperation.Binary { x, y -> x * y }
        operations["÷"] = MathOperation.Binary { x, y -> x / y }
        operations["%"] = MathOperation.Unary { x -> x / 100 }
        operations["+/-"] = MathOperation.Unary { x -> x * (-1) }
        operations["Π"] = MathOperation.Const { PI }
        operations["e"] = MathOperation.Const { Math.E }
        operations["tan"] =
            MathOperation.Unary { x -> tan(Math.toRadians(x)).roundToInt().toDouble() }
        operations["sin"] =
            MathOperation.Unary { x -> sin(Math.toRadians(x)).roundToInt().toDouble() }
        operations["cos"] =
            MathOperation.Unary { x -> cos(Math.toRadians(x)).roundToInt().toDouble() }
        operations["log"] = MathOperation.Unary { x -> log(x, 10.0) }
        operations["ln"] = MathOperation.Unary { x -> ln(x) }
        operations["√x"] = MathOperation.Unary { x -> sqrt(x) }
        operations["x²"] = MathOperation.Unary { x -> x.pow(2.0) }
        operations["x!"] = MathOperation.Unary { x -> factorial(x) }
        operations["|x|"] = MathOperation.Unary { x -> abs(x) }
        operations["x^×"] = MathOperation.Binary { x, y -> x.pow(y) }


    }

    fun calculate() {
        var x = 0.0
        lateinit var operator: String

        if (statesStack.size == 1) {
            if (lastOperator != "") {
                saveVariable(lastOperator)
                saveVariable(lastVar.toString())

            } else {
                operator = statesStack.pop()
            }

        }

        while (statesStack.size > 1 || statesStack.size == 0) {
            if (statesStack.size >= 2) {
                x = statesStack.pop().toDouble()
                operator = statesStack.pop()

                if (isFirstCalc) {
                    lastVar = x
                    lastOperator = operator
                    isFirstCalc = false
                }
            }

            when (val operation = operations[operator]) {
                is MathOperation.Binary -> {
                    val y: Double = statesStack.pop().toDouble()
                    saveVariable(operation.op.myApply(y, x).toString())
                }
                is MathOperation.Unary -> {
                    saveVariable(operation.op.myApply(x).toString())
                }

                is MathOperation.Const -> {
                    saveVariable(operation.op.myApply().toString())
                }
                else -> {}
            }
        }

    }

    fun getResult(): String = statesStack.removeLast()
    fun saveVariable(x: String) {
        statesStack.push(x)
    }


    fun clearMemory() {
        statesStack.clear()
        isFirstCalc = true
    }

    private fun factorial(x: Double): Double {
        var factorial: Long = 1
        for (i in 1..x.toInt()) {
            // factorial = factorial * i;
            factorial *= i.toLong()
        }

        return factorial.toDouble()
    }

}


