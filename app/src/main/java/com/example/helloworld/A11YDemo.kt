package com.example.helloworld

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardWithCloseButton() {
  Card {
    Row {
      Icon(
        imageVector = Icons.Default.Home,
        contentDescription = null, //null to completely ignore with Talkback
        modifier = Modifier
          .padding(12.dp)
          .size(24.dp)
      )
      Text(
        modifier = Modifier.weight(1f),
        fontSize = 15.sp, //SP vs DP - it makes a difference
        text = "This is the message or content of the Card and will take the majority of the body space of the card and provide the most context."
      )
      Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "Close this card", //describe the purpose of this icon/image
        modifier = Modifier
          .clickable {/*do something*/ } //this clickable modifier can do a lot of things!
          .padding(12.dp)
          .size(24.dp)
      )
    }
  }
}

@Composable
fun ClickableRow() {
  Row(
    modifier = Modifier
      .clickable(
        onClickLabel = "Do this thing",
        onClick = {/* do something */ }
      )
      .padding(vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(text = "This is some text")
  }
  HorizontalDivider()
}

@Composable
fun ComplexRow() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(all = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(modifier = Modifier.semantics(mergeDescendants = true) {}) {
      Text(text = "Column 1 Title")
      Text(text = "Column 1 Subtitle1")
      Text(text = "Column 1 Subtitle2")
    }
    Column(modifier = Modifier.semantics(mergeDescendants = true) {}) {
      Text(text = "Column 2 Title")
      Text(text = "Column 2 Subtitle1")
      Text(text = "Column 2 Subtitle2")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = "Column 3 Title")
      Icon(
        imageVector = Icons.Default.Menu,
        contentDescription = "Wifi will be available",
      )
      Icon(
        imageVector = Icons.Default.Home,
        contentDescription = "Equipped with a private balcony",
      )
    }
  }
  HorizontalDivider()
}

@Composable
fun RowsWithTextAndCheckbox() {
  Column {
    val isSelected = remember { mutableStateOf(value = false) }
    //region Row1
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = "This is some text linked with a checkbox"
      )
      Checkbox(
//        modifier = Modifier.padding(12.dp), //add padding to increase the touch area
        checked = isSelected.value,
        onCheckedChange = { isSelected.value = it },
      )
    }
    HorizontalDivider()
    //endregion
    //region Row2
    Row(
      //Entire Row is clickable to toggle its checkbox
      modifier = Modifier
//        .padding(12.dp) //padding added to the border of the entire row
        .toggleable(
          value = isSelected.value,
          onValueChange = { isSelected.value = it },
          role = Role.Checkbox
        ),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = "This is some text linked with a checkbox"
      )
//      Spacer(modifier = Modifier.width(12.dp)) //add spacer to create gap between text and checkbox (one of many options)
      Checkbox(
        modifier = Modifier.padding(12.dp), //add padding to the checkbox to avoid affecting the entire row and eliminate the spacer above
        checked = isSelected.value,
        onCheckedChange = null,
      )
    }
    HorizontalDivider()
    //endregion
    //region Row3
    Row(
      //Row is clickable with a different action than the toggle of its checkbox
      modifier = Modifier
        .clickable(
          onClickLabel = "do something, perhaps navigate to another screen",
          onClick = { },
        ),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = "This is some text linked with a checkbox"
      )
      Checkbox(
//        modifier = Modifier.padding(12.dp),
        checked = isSelected.value,
        onCheckedChange = { isSelected.value = it },
      )
    }
    HorizontalDivider()
    //endregion
    //region Row4
    Row(
      //Row is clickable with a different action than the toggle of its checkbox but the checkbox is now controlled by a custom action
      modifier = Modifier
        .clickable(
          onClickLabel = "do something, perhaps navigate to another screen",
          onClick = { },
        )
        .semantics {
          customActions = listOf(
            CustomAccessibilityAction(
              label = "navigate to this thing",
              action = { true },
            ),
            CustomAccessibilityAction(
              label = "check the box for this row",
              action = { isSelected.value = !isSelected.value; true },
            )
          )
        },
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = "This is some text linked with a checkbox"
      )
      Checkbox(
        modifier = Modifier
          .padding(12.dp)
          .clearAndSetSemantics { },
        checked = isSelected.value,
        onCheckedChange = { isSelected.value = it },
      )
    }
    HorizontalDivider()
    //endregion
  }
}

@Composable
fun RowsWithRadioButtons() {
  Column(
    modifier = Modifier
      .selectableGroup(),
  ) {
    val isSelected = remember { mutableIntStateOf(value = -1) }
    //region Row1
    Row(
      modifier = Modifier
        .selectable(
          selected = isSelected.intValue == 1,
          enabled = true,
          role = Role.RadioButton,
          onClick = { isSelected.intValue = 1 },
        )
        .testTag(tag = "radioRow1"),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = "This is some text linked with a checkbox"
      )
      RadioButton(
        modifier = Modifier
          .padding(12.dp)
          .clearAndSetSemantics { },
        selected = isSelected.intValue == 1,
        onClick = { isSelected.intValue = 1 },
      )
    }
    HorizontalDivider()
    //endregion
    //region Row2
    Row(
      modifier = Modifier
        .selectable(
          selected = isSelected.intValue == 2,
          enabled = true,
          role = Role.RadioButton,
          onClick = { isSelected.intValue = 2 },
        )
        .testTag(tag = "radioRow1"),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = "This is some text linked with a checkbox"
      )
      RadioButton(
        modifier = Modifier
          .padding(12.dp)
          .clearAndSetSemantics { },
        selected = isSelected.intValue == 2,
        onClick = { isSelected.intValue = 2 },
      )
    }
    HorizontalDivider()
    //endregion
  }
}

@Composable
fun CanvasWithDescription() {
  Canvas(
    modifier = Modifier
      .fillMaxWidth()
      .semantics { stateDescription = "drawing of something worthy to describe" }
      .size(80.dp)
  ) {/*something on the canvas*/ }
  HorizontalDivider()
}

@Composable
fun ClickableCanvas() {
  Canvas(
    modifier = Modifier
      .fillMaxWidth()
      .size(80.dp)
      .clickable(onClick = {}, onClickLabel = "do something special")
      .semantics {
        stateDescription = "drawing of something worthy to describe, and the drawing is clickable"
        onClick(
          label = "click on the drawing and it will do something",
          action = {/*do something*/ true }
        )
      },
  ) {/* something on the canvas*/ }
  HorizontalDivider()
}

@Composable
fun ClockFaceDemo() {
  // TraversalGroup and TraversalIndex example
  Box(
    modifier = Modifier
      .width(300.dp)
      .height(300.dp)
      .background(color = Color.Cyan)
      .semantics { isTraversalGroup = true },
  ) {
    repeat(12) { hour ->
      val angle = 360f / 12f * hour
      Text(
        text = if (hour == 0) "12" else "$hour",
        modifier = Modifier
          .align(Alignment.Center)
          .rotate(angle) // rotate your number by 'angle' and now 'up' is towards the numbers final position
          .offset(0.dp, (-100).dp) //positive y is down on the screen. -100 goes "up" in the direction of angle
          .semantics { this.traversalIndex = hour.toFloat() },
      )

    }
  }
}

@Composable
fun LotsOfText() {
  Column(Modifier.padding(all = 12.dp)) {
    Text(
      modifier = Modifier.semantics { heading() },
      text = "Section 1"
    )
    Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse gravida urna ac libero aliquam ultricies. Duis eu tortor egestas, volutpat ipsum eu, facilisis lorem. Sed congue aliquam neque ut molestie. Nulla congue urna vestibulum, fringilla leo sed, laoreet enim. Nullam mauris arcu, faucibus vel turpis vitae, rhoncus ultricies ante. Nullam ipsum leo, facilisis posuere egestas non, placerat a augue. Quisque maximus, metus quis ornare tempor, leo tellus laoreet dolor, quis ultrices sem lectus a augue. Phasellus aliquam eleifend ipsum, nec finibus elit molestie nec. Nulla vel egestas ex.")
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      modifier = Modifier.semantics { heading() },
      text = "Section 2"
    )
    Text(text = "Nullam in ex tempor, blandit lacus vitae, ultrices mi. Aliquam at purus consequat, feugiat purus luctus, fringilla magna. Etiam porttitor massa et ultrices bibendum. Duis vel lobortis dolor, a volutpat leo. Quisque dignissim hendrerit turpis, vitae ullamcorper tortor faucibus ut. Praesent in ex vitae quam mollis ullamcorper. Sed urna risus, dapibus ac diam vel, tempor hendrerit tellus.")
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      modifier = Modifier.semantics { heading() },
      text = "Section 3"
    )
    Text(text = "Pellentesque vestibulum vestibulum tellus, consequat faucibus justo posuere ut. Aliquam at erat eget magna pretium auctor. Etiam ultrices, lorem non rhoncus bibendum, diam urna tincidunt nisl, id placerat nibh libero eget nisl. Maecenas quis ex eget neque dapibus posuere imperdiet in nunc. Phasellus pellentesque quam dui, vel cursus est blandit vitae. Vivamus efficitur pulvinar tellus, quis ornare nunc. Aliquam vel odio quis turpis convallis feugiat. Nunc quis dignissim metus.")
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      modifier = Modifier.semantics { heading() },
      text = "Section 4"
    )
    Text(text = "Vivamus vehicula imperdiet fringilla. In vel turpis scelerisque, dignissim turpis a, consequat lorem. Nulla viverra in eros ac molestie. Phasellus pellentesque, dui vel euismod dignissim, orci magna tempus massa, eleifend vulputate lacus nibh quis magna. Pellentesque id lacinia sapien. Praesent pellentesque neque eu nisl cursus, accumsan accumsan arcu sodales. Aenean tempor et sem sit amet blandit. Donec molestie magna tortor, bibendum condimentum diam auctor id. Cras maximus erat ut gravida venenatis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin in nibh enim. Nulla ultricies efficitur leo, eget dignissim ante rutrum et. Duis accumsan est felis, eu vehicula enim molestie eu.")
    Text(text = "Pellentesque vestibulum vestibulum tellus, consequat faucibus justo posuere ut. Aliquam at erat eget magna pretium auctor. Etiam ultrices, lorem non rhoncus bibendum, diam urna tincidunt nisl, id placerat nibh libero eget nisl. Maecenas quis ex eget neque dapibus posuere imperdiet in nunc. Phasellus pellentesque quam dui, vel cursus est blandit vitae. Vivamus efficitur pulvinar tellus, quis ornare nunc. Aliquam vel odio quis turpis convallis feugiat. Nunc quis dignissim metus.")
    HorizontalDivider()
  }
}

@Composable
fun LegaleseWithMagnifier() {
  var magnifierCenter by remember { mutableStateOf(Offset.Unspecified) }
  Box(
    Modifier
      .magnifier(
        sourceCenter = { magnifierCenter },
        zoom = 3f,
        size = DpSize(height = 200.dp, width = 300.dp),
      )
      .pointerInput(Unit) {
        detectDragGestures(
          // Show the magnifier in the initial position
          onDragStart = { magnifierCenter = it },
          // Magnifier follows the pointer during a drag event
          onDrag = { _, delta ->
            magnifierCenter = magnifierCenter.plus(delta)
          },
          // Hide the magnifier when a user ends the drag movement.
          onDragEnd = { magnifierCenter = Offset.Unspecified },
          onDragCancel = { magnifierCenter = Offset.Unspecified },
        )
      }
      .padding(all = 12.dp),
  ) {
    Text(
      fontSize = 5.sp,
      text = "Accessibility is awesome, you should try it out and have fun! Tap and hold your finger on this text and then drag around the screen to magnify! But also keep in mind that some users really do need this to allow them to use a mobile device every day." +
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse gravida urna ac libero aliquam ultricies. Duis eu tortor egestas, volutpat ipsum eu, facilisis lorem. Sed congue aliquam neque ut molestie. Nulla congue urna vestibulum, fringilla leo sed, laoreet enim. Nullam mauris arcu, faucibus vel turpis vitae, rhoncus ultricies ante. Nullam ipsum leo, facilisis posuere egestas non, placerat a augue. Quisque maximus, metus quis ornare tempor, leo tellus laoreet dolor, quis ultrices sem lectus a augue. Phasellus aliquam eleifend ipsum, nec finibus elit molestie nec. Nulla vel egestas ex.",
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RowWithActionsPreview() {
//  FamilyTreeTheme {
  Surface {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Text(
              modifier = Modifier.semantics { contentDescription = "Demo page of accessibility examples" },
              text = "Demo of A11Y",
            )
          },
          navigationIcon = {
            IconButton(onClick = { }) {
              Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "open the navigation drawer",
              )
            }
          },
        )
      }
    ) {
      Column(
        modifier = Modifier
          .verticalScroll(rememberScrollState())
          .padding(it)
      ) {
        CardWithCloseButton()
        ClickableRow()
        ComplexRow()
        RowsWithTextAndCheckbox()
        RowsWithRadioButtons()
        CanvasWithDescription()
        ClickableCanvas()
        ClockFaceDemo()
        LotsOfText()
        LegaleseWithMagnifier()
      }
    }
  }
//  }
}
