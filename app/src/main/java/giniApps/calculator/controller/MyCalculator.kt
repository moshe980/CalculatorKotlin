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
    private var statesStack: Stack<String> = Stack()
        set(value) {
            statesStack.clear()
            statesStack.addAll(value)

        }
    private var lastOperator: String = ""
    private var lastVar: Double = 0.0
    private var isFirstCalc: Boolean = true


    init {

        operations[OperationsEnum.ADD.value] = MathOperation.Binary { x, y -> x + y }
        operations[OperationsEnum.SUB.value] = MathOperation.Binary { x, y -> x - y }
        operations[OperationsEnum.MULTIPLY.value] = MathOperation.Binary { x, y -> x * y }
        operations[OperationsEnum.DIVIDE.value] = MathOperation.Binary { x, y -> x / y }
        operations[OperationsEnum.PRESENT.value] = MathOperation.Unary { x -> x / 100 }
        operations[OperationsEnum.PLUS_MINUS.value] = MathOperation.Unary { x -> x * (-1) }
        operations[OperationsEnum.PI.value] = MathOperation.Const { PI }
        operations[OperationsEnum.E.value] = MathOperation.Const { Math.E }
        operations[OperationsEnum.TAN.value] =
            MathOperation.Unary { x -> tan(Math.toRadians(x)).roundToInt().toDouble() }
        operations[OperationsEnum.SIN.value] =
            MathOperation.Unary { x -> sin(Math.toRadians(x)).roundToInt().toDouble() }
        operations[OperationsEnum.COS.value] =
            MathOperation.Unary { x -> cos(Math.toRadians(x)).roundToInt().toDouble() }
        operations[OperationsEnum.LOG.value] = MathOperation.Unary { x -> log(x, 10.0) }
        operations[OperationsEnum.LN.value] = MathOperation.Unary { x -> ln(x) }
        operations[OperationsEnum.SQRT.value] = MathOperation.Unary { x -> sqrt(x) }
        operations[OperationsEnum.POW_2.value] = MathOperation.Unary { x -> x.pow(2.0) }
        operations[OperationsEnum.FACTORIAL.value] = MathOperation.Unary { x -> factorial(x) }
        operations[OperationsEnum.ABS.value] = MathOperation.Unary { x -> abs(x) }
        operations[OperationsEnum.POW.value] = MathOperation.Binary { x, y -> x.pow(y) }


    }

    fun calculate() {
        if (statesStack.size > 3)
            operationSort()

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

    private fun operationSort() {
        val tmpStack: Stack<String> = Stack()
        var x: Double
        var y: Double
        var z: String

        tmpStack.addAll(statesStack)

        statesStack.forEach {
            if (it.equals("×") || it.equals("÷") || it.equals("÷") || it.equals("x^×")) {
                x = tmpStack.removeAt(tmpStack.indexOf(it) + 1).toDouble()
                y = tmpStack.removeAt(tmpStack.indexOf(it) - 1).toDouble()
                val operation = operations[it] as MathOperation.Binary
                z = operation.op.myApply(y, x).toString()

                tmpStack.insertElementAt(z, tmpStack.indexOf(it))
                tmpStack.removeAt(tmpStack.indexOf(it))

            }
        }

        statesStack = tmpStack

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


