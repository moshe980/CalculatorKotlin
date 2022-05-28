package giniApps.calculator

import giniApps.calculator.controller.MyCalculator
import giniApps.calculator.controller.OperationsEnum
import org.junit.After
import org.junit.Test

import org.junit.Assert.*

class CalculatorTestKit {
    private val myCalculator = MyCalculator()

    @After
    fun tearDown() {
        myCalculator.clearMemory()
    }

    @Test
    fun addition_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable(OperationsEnum.ADD.value)
        myCalculator.saveVariable("3")

        myCalculator.calculate()

        assertEquals("5.0", myCalculator.getResult())
    }

    @Test
    fun sub_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable(OperationsEnum.SUB.value)
        myCalculator.saveVariable("3")

        myCalculator.calculate()

        assertEquals("-1.0", myCalculator.getResult())
    }

    @Test
    fun mul_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable(OperationsEnum.MULTIPLY.value)
        myCalculator.saveVariable("3")

        myCalculator.calculate()

        assertEquals("6.0", myCalculator.getResult())
    }

    @Test
    fun complex_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable(OperationsEnum.MULTIPLY.value)
        myCalculator.saveVariable("3")
        myCalculator.saveVariable(OperationsEnum.ADD.value)
        myCalculator.saveVariable("3")
        myCalculator.saveVariable(OperationsEnum.MULTIPLY.value)
        myCalculator.saveVariable("4")

        myCalculator.calculate()

        assertEquals("18.0", myCalculator.getResult())
    }


    @Test
    fun tan_isCorrect() {
        myCalculator.saveVariable(OperationsEnum.TAN.value)
        myCalculator.saveVariable("180")

        myCalculator.calculate()

        assertEquals("0.0", myCalculator.getResult())
    }

    @Test
    fun sqrt_isCorrect() {
        myCalculator.saveVariable(OperationsEnum.SQRT.value)
        myCalculator.saveVariable("9")

        myCalculator.calculate()

        assertEquals("3.0", myCalculator.getResult())
    }
}