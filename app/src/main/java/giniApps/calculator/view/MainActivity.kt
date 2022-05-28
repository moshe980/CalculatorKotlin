package giniApps.calculator.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import giniApps.calculator.controller.MyCalculator
import giniApps.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!
    private var result: String
        get() = binding.textView.text.toString()
        set(value) {
            binding.textView.text = value
        }
    private val myCalc = MyCalculator()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Set action for all digit buttons:
        binding.digitsGroup.referencedIds.map<Button>(::findViewById)
            .forEach { btn ->
                btn.setOnClickListener {
                    with(it as Button) {
                        digitAction(text.toString())
                    }
                }
            }

        //Set action for all binary operators buttons:
        binding.binaryOpsGroup.referencedIds.map<Button>(::findViewById)
            .forEach { btn ->
                btn.setOnClickListener {
                    with(it as Button) {
                        binaryOpsAction(text.toString())
                    }
                }
            }

        //Set action for all unary operators buttons:
        binding.unaryOpsGroup.referencedIds.map<Button>(::findViewById)
            .forEach { btn ->
                btn.setOnClickListener {
                    with(it as Button) {
                        unaryOpsAction(text.toString())
                    }
                }
            }

        //Set action for all const buttons:
        binding.constGroup?.referencedIds?.map<Button>(::findViewById)
            ?.forEach { btn ->
                btn.setOnClickListener {
                    with(it as Button) {
                        constOpsAction(text.toString())
                    }
                }
            }
        //Set action for dot button:
        binding.dotBtn.setOnClickListener {
            dotAction()
        }

        //Set action for equality button:
        binding.equalityBtn.setOnClickListener {
            equalityAction()
        }

        //Set action for C button:
        binding.clearBtn.setOnClickListener {
            clearAction()
        }

        //Set action for delete button:
        binding.deleteBtn?.setOnClickListener {
            deleteAction()
        }


    }

    private fun display(text: String) {
        if (isDisplayEmpty()) {
            result = if (text == "Infinity")
                "Divide zero!"
            else {
                //Check if the result is integer
                if (text.toDouble().rem(1).equals(0.0)) {
                    text.toDouble().toInt().toString()
                } else text
            }

        } else {
            result += text

        }
    }

    private fun clearDisplay() {
        result = "0"
    }

    private fun dotAction() {
        if (isContainDot()) return

        if (isDisplayEmpty()) {

            display("0.")

        } else {
            display(".")
        }
    }

    private fun clearAction() {
        clearDisplay()
        myCalc.clearMemory()
    }

    private fun equalityAction() {
        myCalc.saveVariable(result)
        myCalc.calculate()
        clearDisplay()

        val endResult = myCalc.getResult()

        if (endResult == "Infinity")
            display("Divide zero!")
        else {
            display(endResult)
        }
    }


    private fun digitAction(btnName: String) {
        display(btnName)
    }

    private fun binaryOpsAction(btnName: String) {
        myCalc.saveVariable(result)
        myCalc.saveVariable(btnName)
        clearDisplay()
    }

    private fun unaryOpsAction(btnName: String) {
        myCalc.saveVariable(result)
        myCalc.saveVariable(btnName)
        myCalc.calculate()
        clearDisplay()
        display(myCalc.getResult())
    }

    private fun constOpsAction(btnName: String) {
        myCalc.saveVariable(btnName)
        myCalc.calculate()
        clearDisplay()
        display(myCalc.getResult())
    }

    private fun deleteAction() {
        if (result.isNotEmpty()) {
            result = result.substring(0, result.length - 1)
        }

        if (result.isEmpty())
            restartDisplay()
    }

    private fun isContainDot(): Boolean = result.contains(".")
    private fun isDisplayEmpty(): Boolean = result == "0"
    private fun restartDisplay() {
        result = "0"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("tmp", binding.textView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.textView.text = savedInstanceState.getString("tmp").toString()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}





