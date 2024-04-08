package com.example.helloworld

import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
private fun MainScreen() {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Column(
      Modifier.width(IntrinsicSize.Max)
    ) {
      Greeting("Guest")
      Spacer(modifier = Modifier.height(20.dp))
      Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text("Click here!")
      }
      Spacer(modifier = Modifier.height(20.dp))
      LogoButton(onClick = { }, Modifier.fillMaxWidth())
      Spacer(modifier = Modifier.height(80.dp))
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        DestructiveButton(onClick = { /*TODO*/ }) {
          Text(text = "Finished")
        }
      }
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
