package com.example.calculatorcompose

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

class Dp2Sp {

}

inline val TextUnit.dp: Dp get() = Dp(value = this.value);