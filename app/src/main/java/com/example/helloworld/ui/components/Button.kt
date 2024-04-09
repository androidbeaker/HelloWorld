package com.example.helloworld.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.helloworld.R
import com.example.helloworld.ui.theme.ColorThemePreview
import com.example.helloworld.ui.theme.HelloWorldTheme
import androidx.compose.material3.Button as M3Button

@Composable
fun Button(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.shape,
  colors: ButtonColors = ButtonDefaults.buttonColors(),
  elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
  border: BorderStroke? = null,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.() -> Unit
) =
  M3Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = colors,
    elevation = elevation,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )

@Composable
fun PrimaryButton(
  onClick: () -> Unit,
  content: @Composable RowScope.() -> Unit
) =
  Button(
    onClick = onClick,
    colors = primaryButtonColors(),
    content = content
  )

@Composable
private fun primaryButtonColors (): ButtonColors = ButtonDefaults.buttonColors(
  containerColor = MaterialTheme.colorScheme.primaryContainer,
  contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
  disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
  disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
)

/**
 * DestructiveButton is designed for strong purpose; Delete, Cancel, Abort, and similar type actions.
 * Note: Use sparingly
 */
@Composable
fun DestructiveButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.shape,
  elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
  border: BorderStroke? = null,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.() -> Unit
) =
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = destructiveButtonColors(),
    elevation = elevation,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )

@Composable
private fun destructiveButtonColors (): ButtonColors = ButtonDefaults.buttonColors(
  containerColor = MaterialTheme.colorScheme.errorContainer,
  contentColor = MaterialTheme.colorScheme.onErrorContainer,
  disabledContainerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
  disabledContentColor = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.5f)
)

@Composable
fun ElevatedButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.elevatedShape,
  colors: ButtonColors = ButtonDefaults.elevatedButtonColors(),
  elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(),
  border: BorderStroke? = null,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.() -> Unit
) =
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = colors,
    elevation = elevation,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )

@Composable
fun FilledTonalButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.filledTonalShape,
  colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
  elevation: ButtonElevation? = ButtonDefaults.filledTonalButtonElevation(),
  border: BorderStroke? = null,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.() -> Unit
) =
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = colors,
    elevation = elevation,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )

@Composable
fun OutlinedButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.outlinedShape,
  colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
  elevation: ButtonElevation? = null,
  border: BorderStroke? = ButtonDefaults.outlinedButtonBorder,
  contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.() -> Unit
) =
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = colors,
    elevation = elevation,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )

@Composable
fun SecondaryButton(
  onClick: () -> Unit,
  content: @Composable RowScope.() -> Unit
) = OutlinedButton(
  onClick = onClick,
  colors = secondaryButtonColors(),
  content = content
)

@Composable
private fun secondaryButtonColors (): ButtonColors = ButtonDefaults.outlinedButtonColors(
  containerColor = MaterialTheme.colorScheme.secondaryContainer,
  contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
  disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
  disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f)
)

@Composable
fun TextButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  shape: Shape = ButtonDefaults.textShape,
  colors: ButtonColors = ButtonDefaults.textButtonColors(),
  elevation: ButtonElevation? = null,
  border: BorderStroke? = null,
  contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  content: @Composable RowScope.() -> Unit
) =
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = shape,
    colors = colors,
    elevation = elevation,
    border = border,
    contentPadding = contentPadding,
    interactionSource = interactionSource,
    content = content
  )

/**
 * By default, you may use this [LogoButton] without providing anything but an onClick() and you will enjoy
 * a nice HEB logo button. If you would like to reuse this component to load a different image/logo, ensure you
 * set the necessary parameters to fit the shape and desired size of your new image asset.
 *
 * @param onClick action to be performed on click action
 * @param modifier
 * @param shape
 * @param contentScale
 * @param logo
 * @param contentDescription
 * @param enabled
 * @param interactionSource
 */
@Composable
fun LogoButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp, bottomStart = 28.dp),
  contentScale: ContentScale = ContentScale.Inside,
  @DrawableRes logo: Int = R.drawable.logo,
  @StringRes contentDescription: Int = R.string.logo_content_desc,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) =
  Box(
    modifier = modifier
      .clip(shape = shape)
      .clickable(
        onClick = onClick,
        enabled = enabled,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple(
          bounded = true,
        )
      )
  ) {
    Image(
      painter = painterResource(id = logo),
      contentDescription = stringResource(id = contentDescription),
      contentScale = contentScale
    )
  }

@ColorThemePreview
@Composable
private fun ButtonsPreview() {
  HelloWorldTheme {
    Surface {
      Column {
        LogoButton(
          onClick = { /*TODO*/ },
        )
        Button(onClick = { /*TODO*/ }) {
          Text(text = "Button")
        }
        PrimaryButton(onClick = { /*TODO*/ }) {
          Text(text = "Primary")
        }
        SecondaryButton(onClick = { /*TODO*/ }) {
          Text(text = "Secondary")
        }
        DestructiveButton(onClick = { /*TODO*/ }) {
          Text(text = "Destroy")
        }
        ElevatedButton(onClick = { /*TODO*/ }) {
          Text(text = "ElevatedButton")
        }
        FilledTonalButton(onClick = { /*TODO*/ }) {
          Text(text = "FilledTonalButton")
        }
        OutlinedButton(onClick = { /*TODO*/ }) {
          Text(text = "OutlinedButton")
        }
        TextButton(onClick = { /*TODO*/ }) {
          Text(text = "TextButton")
        }
      }
    }
  }
}
