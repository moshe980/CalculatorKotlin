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
    private var stack: Stack<String> = Stack()
        set(value) {
            stack.clear()
            stack.addAll(value)

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
        var x = 0.0
        lateinit var operator: String

        if (stack.size > 3)
            priorityCalculate()

        if (stack.size == 1) {
            if (lastOperator != "") {
                saveVariable(lastOperator)
                saveVariable(lastVar.toString())
            } else {
                operator = stack.pop()
            }
        }

        while (stack.size > 1 || stack.size == 0) {
            if (stack.size >= 2) {
                x = stack.pop().toDouble()
                operator = stack.pop()

                if (isFirstCalc) {
                    lastVar = x
                    lastOperator = operator
                    isFirstCalc = false
                }
            }

            when (val operation = operations[operator]) {
                is MathOperation.Binary -> {
                    val y: Double = stack.pop().toDouble()
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

    private fun priorityCalculate() {
        val tmpStack: Stack<String> = Stack()
        var x: Double
        var y: Double
        var result: String

        tmpStack.addAll(stack)

        stack.forEach {
            if (it.equals(OperationsEnum.MULTIPLY.value) || it.equals(OperationsEnum.DIVIDE.value)
                || it.equals(OperationsEnum.POW.value)
            ) {
                x = tmpStack.removeAt(tmpStack.indexOf(it) + 1).toDouble()
                y = tmpStack.removeAt(tmpStack.indexOf(it) - 1).toDouble()
                val operation = operations[it] as MathOperation.Binary
                result = operation.op.myApply(y, x).toString()

                tmpStack.insertElementAt(result, tmpStack.indexOf(it))
                tmpStack.removeAt(tmpStack.indexOf(it))

            }
        }

        stack = tmpStack

    }

    fun getResult(): String = stack.removeLast()
    fun saveVariable(x: String) {
        stack.push(x)
    }


    fun clearMemory() {
        stack.clear()
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


