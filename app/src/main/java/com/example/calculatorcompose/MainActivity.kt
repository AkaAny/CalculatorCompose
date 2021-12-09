package com.example.calculatorcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.calculatorcompose.ui.theme.Blue
import com.example.calculatorcompose.ui.theme.CalculatorComposeTheme
import com.example.calculatorcompose.ui.theme.Typography
import kotlinx.coroutines.launch

class MainActivity() : ComponentActivity() {
    private var viewModel: CalculatorViewModel = CalculatorViewModel();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorComposeTheme {
                Calculator()
            }
        }
    }

    lateinit var scaffoldState:ScaffoldState;

    //@Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Calculator() {
        scaffoldState= rememberScaffoldState()
        Scaffold(scaffoldState = scaffoldState) {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                Column {
                    OutlinedTextField(
                        value = viewModel.inputState,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Calculator") },
                        textStyle = TextStyle(fontSize = 30.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3F)
                    )
                    Spacer(Modifier.height(3.dp))
                    InputGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.7F)
                    )
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }


    @Composable
    fun InputGrid(modifier: Modifier) {
        Column(modifier = modifier) {
            val evenWeightModifier = Modifier
                .fillMaxSize()
                .weight(1.0F)
            //first row action bar
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.1F)
            ) {
                ClearButton(evenWeightModifier)
                ActionButton("(", evenWeightModifier)
                ActionButton(")", evenWeightModifier)
                BackspaceButton(evenWeightModifier)
            }
            Row(
                Modifier
                    .fillMaxSize()
                    .weight(0.9F)
            ) {
                NumPad(modifier = Modifier.weight(0.75F))
                ActionPad(modifier = Modifier.weight(0.25F))
            }
        }
    }

    @Composable
    fun NumPad(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            val evenWeightModifier = Modifier
                .fillMaxSize()
                .weight(1F)
            var currentNumber = 1
            for (rowIndex in 1..3) {
                Row(modifier = evenWeightModifier) {
                    for (colIndex in 1..3) {
                        NumberButton(currentNumber, evenWeightModifier)
                        currentNumber += 1
                    }
                }
            }
            Row(
                modifier = evenWeightModifier
            ) {
                ActionButton(".", modifier = evenWeightModifier)
                Spacer(modifier = evenWeightModifier)
                NumberButton(0, modifier = evenWeightModifier)
            }
        }
    }

    @Composable
    fun ActionPad(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            val maxWidthModifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .weight(0.2F)
            ActionButton("+", modifier = maxWidthModifier)
            ActionButton("-", modifier = maxWidthModifier)
            ActionButton("*", modifier = maxWidthModifier)
            SumButton(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .weight(0.4F)
            )
        }
    }

    //@Preview(showBackground = true)
    @Composable
    fun NumberButton(number: Number, modifier: Modifier) {
        Button(
            {
                viewModel.input(number.toString())
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Blue
            ),
            modifier = modifier.padding(1.dp)
        ) {
            Text(number.toString())
        }
    }

    @Composable
    fun ActionButton(text: String, modifier: Modifier = Modifier) {
        Button({
            viewModel.input(text)
        }, modifier = modifier) {
            Text(text)
        }
    }

    @Composable
    fun SumButton(modifier: Modifier) {
        val scope = rememberCoroutineScope()
        Button(
            {
                try {
                    viewModel.action()
                } catch (e: Throwable) {
                    val msg = e.message.toString()
                    Log.d("SumAction",msg);
                    val context=this
                    //scope.launch {
                        e.printStackTrace()
                        //scaffoldState.snackbarHostState.showSnackbar(msg)
                        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
                    //}
                }
            }, modifier = modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text("=")
        }
    }

    @Composable
    fun ButtonWithMargin(
        text: String,
        onClickAction: () -> Unit,
        backgroundColor: Color = MaterialTheme.colors.primary,
        contentColor: Color = MaterialTheme.colors.onPrimary
    ) {
        val buttonColors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
        Button(
            onClick = onClickAction, content = {
                Text(text = text)
            }, colors = buttonColors,
            modifier = Modifier.padding(1.dp)
        )
    }

    @Composable
    fun ClearButton(modifier: Modifier) {
        Button(
            {
                viewModel.clear()
            }, modifier = modifier
        ) {
            Text(text = "C")
        }
    }

    @Composable
    fun BackspaceButton(modifier: Modifier) {
        Button(onClick = {
            viewModel.backspace()
        }, content = {
            val painter = painterResource(id = android.R.drawable.ic_input_delete)
            //Image(painter = painter,contentDescription = null,contentScale = ContentScale.Inside)
            //fit with action button
            //val imageModifier = Modifier.height(Typography.body1.fontSize.value.sp.dp)
            Icon(painter = painter, contentDescription = null,
                //modifier = imageModifier
            )
        }, modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Blue
            )
        )
    }
}