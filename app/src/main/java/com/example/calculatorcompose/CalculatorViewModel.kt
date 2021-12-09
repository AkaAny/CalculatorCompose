package com.example.calculatorcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import bind.Bind
import bind.CalculatorBridge

class CalculatorViewModel :ViewModel(){
    private val mInput=StringBuilder("0")

    var inputState by mutableStateOf<String>("0")
    //private set

    private val bridge:CalculatorBridge by lazy{
        Bind.init()
    }

    fun input(s:String){
        if(mInput.length==1 && mInput.toString()=="0"){
            mInput.clear()
        }
        mInput.append(s)
        inputState=mInput.toString();
    }

    fun action(){
        val text=mInput.toString();
        if(text==""){
            return;
        }
        try{
        val result= bridge.runString(text);
        clear()
        mInput.append(result)
        inputState=result;
        }catch (e:Throwable){
            e.printStackTrace()
        }
    }

    fun clear(){
        mInput.clear();
        mInput.append("0")
        inputState=mInput.toString()
    }

    fun backspace(){
        val lastIndex=mInput.lastIndex;
        if(lastIndex<0 || mInput.toString()=="0"){
            return;
        }
        if(mInput.length==1 && mInput.toString()!="0"){
            mInput.setCharAt(0,'0')
            inputState=mInput.toString()
            return;
        }
        mInput.deleteAt(mInput.lastIndex)
        inputState=mInput.toString()
    }
}