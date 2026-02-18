package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf(0) }
    var result by remember { mutableStateOf("Результат") }

    val operations = listOf("+", "-", "×", "÷")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Калькулятор",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp, top = 32.dp)
        )

        OutlinedTextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Первое число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Выберите операцию:", modifier = Modifier.align(Alignment.Start))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            operations.forEachIndexed { index, operation ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = (selectedOperation == index),
                        onClick = { selectedOperation = index }
                    )
                    Text(operation, fontSize = 20.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Второе число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                result = calculate(number1, number2, selectedOperation)
            },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
        ) {
            Text("=", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Text(
                text = result,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun calculate(num1Str: String, num2Str: String, operationIndex: Int): String {
    if (num1Str.isEmpty() || num2Str.isEmpty()) {
        return "Заполните оба поля"
    }

    return try {
        val num1 = num1Str.toDouble()
        val num2 = num2Str.toDouble()

        val result = when (operationIndex) {
            0 -> num1 + num2
            1 -> num1 - num2
            2 -> num1 * num2
            3 -> {
                if (num2 == 0.0) return "Ошибка: деление на ноль"
                num1 / num2
            }
            else -> 0.0
        }

        val operation = when (operationIndex) {
            0 -> "+"
            1 -> "-"
            2 -> "×"
            3 -> "÷"
            else -> "?"
        }

        "$num1 $operation $num2 = $result"
    } catch (e: NumberFormatException) {
        "Ошибка: введите числа"
    }
}