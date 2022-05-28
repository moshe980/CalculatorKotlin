package giniApps.calculator

import giniApps.calculator.controller.MyCalculator
import org.junit.After
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorTestKit {
    val myCalculator = MyCalculator()

    @After
    fun tearDown() {
        myCalculator.clearMemory()
    }

    @Test
    fun addition_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable("+")
        myCalculator.saveVariable("3")

        myCalculator.calculate()

        assertEquals("5.0", myCalculator.getResult())
    }

    @Test
    fun sub_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable("-")
        myCalculator.saveVariable("3")

        myCalculator.calculate()

        assertEquals("-1.0", myCalculator.getResult())
    }

    @Test
    fun mul_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable("×")
        myCalculator.saveVariable("3")

        myCalculator.calculate()

        assertEquals("6.0", myCalculator.getResult())
    }

    @Test
    fun complex_isCorrect() {
        myCalculator.saveVariable("2")
        myCalculator.saveVariable("×")
        myCalculator.saveVariable("3")
        myCalculator.saveVariable("+")
        myCalculator.saveVariable("3")
        myCalculator.saveVariable("×")
        myCalculator.saveVariable("4")

        myCalculator.calculate()

        assertEquals("18.0", myCalculator.getResult())
    }


    @Test
    fun tan_isCorrect() {
        myCalculator.saveVariable("tan")
        myCalculator.saveVariable("180")

        myCalculator.calculate()

        assertEquals("0.0", myCalculator.getResult())
    }
}