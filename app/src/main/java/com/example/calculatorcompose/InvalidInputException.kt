package com.example.calculatorcompose

import java.lang.IllegalArgumentException

class InvalidInputException(msg:String,index:Number):IllegalArgumentException(msg) {

}