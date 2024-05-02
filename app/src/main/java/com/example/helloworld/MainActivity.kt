package com.example.helloworld

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.components.Button
import com.example.helloworld.ui.components.DestructiveButton
import com.example.helloworld.ui.components.LogoButton
import com.example.helloworld.ui.theme.ColorThemePreview
import com.example.helloworld.ui.theme.HelloWorldTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      HelloWorldTheme {
        Surface(
          modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        ) {
          MainScreen()
        }
      }
    }
  }
}

const val DEFAULT_NAME_TEXT = "Guest"
const val CLICK_HERE_BUTTON_TEXT = "Click here!"
const val FINISHED_BUTTON_TEXT = "Finished"

/**
 * [MainScreen] is a fun, interactive demo of Buttons and show/hide of a [Greeting].
 */
@Composable
fun MainScreen(name: String = DEFAULT_NAME_TEXT) {
  var showGreeting by rememberSaveable { mutableStateOf(false) }
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column(
      Modifier.width(IntrinsicSize.Max)
    ) {
      if (showGreeting) {
        Greeting(name = name)
        Spacer(modifier = Modifier.height(20.dp))
      }
      Button(
        onClick = { showGreeting = true },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(CLICK_HERE_BUTTON_TEXT)
      }
      Spacer(modifier = Modifier.height(20.dp))
      LogoButton(onClick = { }, Modifier.fillMaxWidth())
      Spacer(modifier = Modifier.height(80.dp))
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        DestructiveButton(onClick = { showGreeting = false }) {
          Text(text = FINISHED_BUTTON_TEXT)
        }
      }
      data class Product(
        val name: String,
        val relatedProducts: List<Product>,
      )
      val product = Product("Tortilla Chips",
        listOf(
          Product("Salsa", listOf(
            Product("Tacos", listOf(
              Product("Tortilla", listOf()))))),
          Product("Guac", listOf(
            Product("Pita Chips", listOf()))),
        )
      )
      val queue = ArrayDeque<Product>()
      queue.add(product)
      val listOfRelatedProducts = remember { mutableStateListOf<String>() }
      while (queue.isNotEmpty()) {
        val queueProduct = queue.removeFirst()
        listOfRelatedProducts.add(queueProduct.name)
        if(queueProduct.relatedProducts.isNotEmpty()) {
          queueProduct.relatedProducts.forEach { queue.add(it) }
        }
      }
      Log.d("All Related Products", listOfRelatedProducts.toList().toString())
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Surface(
    color = MaterialTheme.colorScheme.primaryContainer,
  ) {
    Text(
      text = "Hello $name!",
      color = MaterialTheme.colorScheme.onPrimaryContainer,
      modifier = modifier
        .padding(24.dp)
        .fillMaxWidth(),
      textAlign = TextAlign.Center
    )
  }
}

@ColorThemePreview
@Composable
private fun MainScreenPreview() {
  HelloWorldTheme {
    Surface {
      MainScreen()
    }
  }
}

@ColorThemePreview
@Composable
private fun GreetingPreview() {
  HelloWorldTheme {
    Surface {
      Greeting("Brian")
    }
  }
}
