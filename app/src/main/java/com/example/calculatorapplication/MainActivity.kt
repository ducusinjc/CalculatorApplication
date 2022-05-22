package com.example.calculatorapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var output: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        output = findViewById(R.id.output)
    }

    fun onDigit(view: View){
        output?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View){
        output?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            output?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        output?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                output?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var value = output?.text.toString()
            var prefix = ""
            var result : Double = 0.0
            try{
                if(value.startsWith("-")){
                    prefix = "-"
                    value = value.substring(1)
                }
                if(value.contains("-")){
                    val splitValue = value.split("-")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    result = firstValue.toDouble() - secondValue.toDouble()
                }else if(value.contains("+")){
                    val splitValue = value.split("+")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    result = firstValue.toDouble() + secondValue.toDouble()
                }else if(value.contains("/")){
                    val splitValue = value.split("/")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    result = firstValue.toDouble() / secondValue.toDouble()
                }else if(value.contains("*")){
                    val splitValue = value.split("*")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    result = firstValue.toDouble() * secondValue.toDouble()
                }
                output?.text = removeZeroAfterDot(result.toString())
                if((output?.text)?.contains(".") == true){
                    lastDot = true
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.endsWith(".0"))
            value = result.substring(0,result.length - 2)
        return value
    }
}