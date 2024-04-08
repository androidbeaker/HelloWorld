package com.example.helloworld.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers


@Preview(
  name = "Light Theme",
  group = "color themes",
  uiMode = UI_MODE_NIGHT_NO
)
@Preview(
  name = "Dark Theme",
  group = "color themes",
  uiMode = UI_MODE_NIGHT_YES
)
@Preview(
  name = "Dynamic Dark Theme",
  group = "color themes",
  uiMode = UI_MODE_NIGHT_YES,
  wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
annotation class ColorThemePreview
