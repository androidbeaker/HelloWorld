package com.example.helloworld.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.ColorThemePreview
import com.example.helloworld.ui.theme.HelloWorldTheme
import com.example.helloworld.ui.util.toast


data class DropdownMenuItemData(
  val labelText: String,
  val onClick: () -> Unit,
  val callDismissOnClick: Boolean = false,
)

data class FilterChipBottomSheetMenuItem(
  val menuRow: @Composable () -> Unit,
  val onClick: () -> Unit,
  val callDismissOnClick: Boolean = false,
)

sealed class FilterChipData {
  abstract val key: String
  abstract val label: String

  data class DropDownChip (
    override val key: String,
    override val label: String = key,
    val dropDownMenuItems: List<DropdownMenuItemData>? = null,
  ) : FilterChipData()

  data class ModalBottomSheetChip (
    override val key: String,
    override val label: String = key,
    val modalBottomSheetMenuItems: List<FilterChipBottomSheetMenuItem>? = null,
  ) : FilterChipData()
}

private sealed class FilterChipValueData {
  abstract val label: String

  data class DropDownChipValueData(
    override val label: String,
    val dropDownMenuItems: List<DropdownMenuItemData>? = null,
  ) : FilterChipValueData()

  data class ModalBottomSheetChipValueData(
    override val label: String,
    val modalBottomSheetMenuItems: List<FilterChipBottomSheetMenuItem>? = null,
  ) : FilterChipValueData()

  fun isMenuItemsListEmptyOrNull() =
    when(this) {
      is DropDownChipValueData -> {
        dropDownMenuItems.isNullOrEmpty()
      }
      is ModalBottomSheetChipValueData -> {
        modalBottomSheetMenuItems.isNullOrEmpty()
      }
    }
}

private fun getValueData(filterChipData: FilterChipData): FilterChipValueData {
  return when(filterChipData) {
    is FilterChipData.DropDownChip -> {
      FilterChipValueData.DropDownChipValueData(label = filterChipData.label, dropDownMenuItems = filterChipData.dropDownMenuItems)
    }
    is FilterChipData.ModalBottomSheetChip -> {
      FilterChipValueData.ModalBottomSheetChipValueData(label = filterChipData.label, modalBottomSheetMenuItems = filterChipData.modalBottomSheetMenuItems)
    }
  }
}

/**
 * KEEP THIS PRIVATE ALWAYS
 */
@Composable
private fun BaseFilterChip(
  modifier: Modifier = Modifier,
  selected: Boolean,
  onClick: () -> Unit,
  chipValueData: FilterChipValueData,
  enabled: Boolean = true,
  leadingIcon: ImageVector? = null,
  shape: Shape = MaterialTheme.shapes.large,
) {
  var showFilterChipMenu by remember { mutableStateOf(false) }

  FilterChip(
    modifier = modifier.requiredWidthIn(min = 48.dp),// must be min 48.dp or chips will begin spacing differently from each other
    selected = selected,
    onClick = {
      if (!chipValueData.isMenuItemsListEmptyOrNull()) {
        showFilterChipMenu = true
      } else onClick()
    },
    label = {
      Text(
        // GOOGLE BUG: need to set a required width for the label if width with label text and leading & trailing icons would be
        // less than that needed to fill the FilterChip min width of 48.dp (required to maintain consistent spacing of chips).
        // This is required because label is set to ```horizontalArrangement = Arrangement.Start``` instead of Center
        modifier = Modifier.then(
          if ((leadingIcon == null || !selected) && chipValueData.isMenuItemsListEmptyOrNull()) Modifier.requiredWidthIn(min = 16.dp)
          else Modifier
        ),
        textAlign = TextAlign.Center,
        text = chipValueData.label,
      )
    },
    enabled = enabled,
    leadingIcon =
      if (leadingIcon != null && selected) {
        @Composable {
          Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            modifier = Modifier
              .size(FilterChipDefaults.IconSize),
          )
        }
      } else null,
    trailingIcon = if (!chipValueData.isMenuItemsListEmptyOrNull()) {
      @Composable {
        val icon = @Composable {
          Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier
              .size(FilterChipDefaults.IconSize)
          )
        }
        when(chipValueData) {
          is FilterChipValueData.DropDownChipValueData -> {
            FilterChipDropDownMenuBox(
              expanded = showFilterChipMenu,
              onDismissRequest = { showFilterChipMenu = false },
              actions = chipValueData.dropDownMenuItems,
              onFilterChipClick = onClick,
            ) {
              icon.invoke()
            }
          }
          is FilterChipValueData.ModalBottomSheetChipValueData -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            if(showFilterChipMenu) {
              ModalBottomSheet(
                onDismissRequest = {
                  showFilterChipMenu = false
                },
                sheetState = sheetState,
                content = {
                  FilterChipBottomSheetContent(
                    filterChipBottomSheetMenuItems = chipValueData.modalBottomSheetMenuItems,
                    chipOnClick = onClick,
                    dismissBottomSheet = { showFilterChipMenu = false },
                  )
                },
              )
            }

            Box {
              icon.invoke()
            }
          }
        }
      }
    } else null,
    shape = shape,
  )
}

/**
 * Typically this should never be used directly, consider using FilterChipGroupHorizontalRow or FilterChipGroupFlowRow
 */
@JvmName("DropDownBasicFilterChip")
@Composable
fun BasicDropDownFilterChip(
  modifier: Modifier = Modifier,
  selected: Boolean = false,
  onClickFilterChip: () -> Unit,
  label: String,
  enabled: Boolean = true,
  dropDownMenuItems: List<DropdownMenuItemData>? = null,
  shape: Shape = MaterialTheme.shapes.large,
) {
  BaseFilterChip(
    modifier = modifier,
    selected = selected,
    onClick = onClickFilterChip,
    chipValueData = FilterChipValueData.DropDownChipValueData(label = label, dropDownMenuItems = dropDownMenuItems),
    enabled = enabled,
    shape = shape,
  )
}

/**
 * Typically this should never be used directly, consider using FilterChipGroupHorizontalRow or FilterChipGroupFlowRow
 */
@JvmName("BottomSheetBasicFilterChip")
@Composable
fun BasicFilterChip(
  modifier: Modifier = Modifier,
  selected: Boolean = false,
  onClickFilterChip: () -> Unit,
  label: String,
  enabled: Boolean = true,
  bottomSheetMenuItems: List<FilterChipBottomSheetMenuItem>? = null,
  shape: Shape = MaterialTheme.shapes.large,
) {
  BaseFilterChip(
    modifier = modifier,
    selected = selected,
    onClick = onClickFilterChip,
    chipValueData = FilterChipValueData.ModalBottomSheetChipValueData(label = label, modalBottomSheetMenuItems = bottomSheetMenuItems),
    enabled = enabled,
    shape = shape,
  )
}

/**
 * Typically this should never be used directly, consider using FilterChipGroupHorizontalRow or FilterChipGroupFlowRow
 */
@JvmName("DropDownCheckedFilterChip")
@Composable
fun CheckedFilterChip(
  selected: Boolean,
  onClickFilterChip: () -> Unit,
  label: String,
  enabled: Boolean = true,
  dropDownMenuItems: List<DropdownMenuItemData>? = null,
  shape: Shape = MaterialTheme.shapes.large,
) {
  BaseFilterChip(
    selected = selected,
    onClick = onClickFilterChip,
    chipValueData = FilterChipValueData.DropDownChipValueData(label = label, dropDownMenuItems = dropDownMenuItems),
    enabled = enabled,
    leadingIcon = Icons.Filled.Check,
    shape = shape,
  )
}

/**
 * Typically this should never be used directly, consider using FilterChipGroupHorizontalRow or FilterChipGroupFlowRow
 */
@JvmName("BottomSheetCheckedFilterChip")
@Composable
fun CheckedFilterChip(
  selected: Boolean,
  onClickFilterChip: () -> Unit,
  label: String,
  enabled: Boolean = true,
  bottomSheetMenuItems: List<FilterChipBottomSheetMenuItem>? = null,
  shape: Shape = MaterialTheme.shapes.large,
) {
  BaseFilterChip(
    selected = selected,
    onClick = onClickFilterChip,
    chipValueData = FilterChipValueData.ModalBottomSheetChipValueData(label = label, modalBottomSheetMenuItems = bottomSheetMenuItems),
    enabled = enabled,
    leadingIcon = Icons.Filled.Check,
    shape = shape,
  )
}

//region HorizontalRow
/**
 * Multi-select
 *
 * @param selectedFilterItems List<[FilterChipData]>? - determines which (if any) filter chips are marked selected. keys are all that are important, label and dropDownMenuItems are not compared.
 * @param groupChipsDataList List<[FilterChipData]> - all key values should be unique.
 *  if there are duplicates of keys, the last duplicate will replace the first and all others will be ignored.
 *  for example:
 *  ```
 *    val groupOfChipsData = listOf(
 *     FilterChipData(key = stringResource(R.string.label_name), label = "Bad Name"),//will be replaced by duplicate found later in list
 *     FilterChipData(key = stringResource(R.string.label_sex), label = stringResource(R.string.label_sex)),
 *     FilterChipData(key = stringResource(R.string.label_birth), label = stringResource(R.string.label_birth)),
 *     FilterChipData(key = stringResource(R.string.label_name), label = stringResource(R.string.label_name)),
 *   )
 *  ```
 *  would result in 3 filter chips (in this order): Name, Sex, Birth
 */
@Composable
fun FilterChipGroupHorizontalRow(
  modifier: Modifier = Modifier,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(20.dp),
  contentPadding: PaddingValues? = null,
  selectedFilterItems: List<FilterChipData>?,
  groupChipsDataList: List<FilterChipData>,
  onChipClicked: (selectedFilterChipData: FilterChipData) -> Unit,
  showChecked: Boolean = false,
  chipShape: Shape = MaterialTheme.shapes.large,
) {
  val chipsDataMap = groupChipsDataList.associate { chipData -> chipData.key to getValueData(chipData) }
  LazyRow(
    modifier = modifier,
    horizontalArrangement = horizontalArrangement,
    contentPadding = contentPadding ?: PaddingValues(0.dp)
  ) {
    items(chipsDataMap.keys.toList()) { itemKey ->
      chipsDataMap[itemKey]?.let { groupItemValue ->
        if (showChecked) {
          when(groupItemValue) {
            is FilterChipValueData.DropDownChipValueData -> {
              CheckedFilterChip(
                selected = selectedFilterItems?.let { itemList -> itemKey in itemList.map { it.key } } ?: false,
                onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == itemKey }) },
                label = groupItemValue.label,
                dropDownMenuItems = groupItemValue.dropDownMenuItems,
                shape = chipShape,
              )
            }
            is FilterChipValueData.ModalBottomSheetChipValueData -> {
              CheckedFilterChip(
                selected = selectedFilterItems?.let { itemList -> itemKey in itemList.map { it.key } } ?: false,
                onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == itemKey }) },
                label = groupItemValue.label,
                bottomSheetMenuItems = groupItemValue.modalBottomSheetMenuItems,
                shape = chipShape,
              )
            }
          }
        } else {
          when(groupItemValue) {
            is FilterChipValueData.DropDownChipValueData -> {
              BasicDropDownFilterChip(
                selected = selectedFilterItems?.let { itemList -> itemKey in itemList.map { it.key } } ?: false,
                onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == itemKey }) },
                label = groupItemValue.label,
                dropDownMenuItems = groupItemValue.dropDownMenuItems,
                shape = chipShape,
              )
            }
            is FilterChipValueData.ModalBottomSheetChipValueData -> {
              BasicFilterChip(
                selected = selectedFilterItems?.let { itemList -> itemKey in itemList.map { it.key } } ?: false,
                onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == itemKey }) },
                label = groupItemValue.label,
                bottomSheetMenuItems = groupItemValue.modalBottomSheetMenuItems,
                shape = chipShape,
              )
            }
          }
        }
      }
    }
  }
}

/**
 * Single-select
 *
 * @param selectedFilterItem [FilterChipData]? - determines which (if any) filter chips are marked selected. keys are all that are important, label and dropDownMenuItems are not compared.
 * @param groupChipsDataList List<[FilterChipData]> - all key values should be unique.
 *  if there are duplicates of keys, the last duplicate will replace the first and all others will be ignored.
 */
@Composable
fun FilterChipGroupHorizontalRow(
  modifier: Modifier = Modifier,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(20.dp),
  contentPadding: PaddingValues? = null,
  selectedFilterItem: FilterChipData?,
  groupChipsDataList: List<FilterChipData>,
  onChipClicked: (selectedFilterChipData: FilterChipData) -> Unit,
  showChecked: Boolean = false,
  chipShape: Shape = MaterialTheme.shapes.large,
) {
  FilterChipGroupHorizontalRow(
    modifier = modifier,
    horizontalArrangement = horizontalArrangement,
    contentPadding = contentPadding,
    selectedFilterItems = selectedFilterItem?.let { listOf(it) },
    groupChipsDataList = groupChipsDataList,
    onChipClicked = onChipClicked,
    showChecked = showChecked,
    chipShape = chipShape,
  )
}
//endregion

//region FlowRow
//TODO Future Enhancement - add enclosing expand/collapse container (scrollable?) linked to params for
// * number of max visible rows when collapsed
// * max visible when expanded?
/**
 * Multi-select
 *
 * @param selectedFilterItems List<[FilterChipData]>? - determines which (if any) filter chips are marked selected. keys are all that are important, label and dropDownMenuItems are not compared.
 * @param groupChipsDataList List<[FilterChipData]> - all key values should be unique.
 *  if there are duplicates of keys, the last duplicate will replace the first and all others will be ignored.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterChipGroupFlowRow(
  modifier: Modifier = Modifier,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(20.dp),
  selectedFilterItems: List<FilterChipData>?,
  groupChipsDataList: List<FilterChipData>,
  onChipClicked: (selectedFilterChipData: FilterChipData) -> Unit,
  showChecked: Boolean = false,
  chipShape: Shape = MaterialTheme.shapes.large,
) {
  val chipsDataMap = groupChipsDataList.associate { chipData -> chipData.key to getValueData(chipData) }
  FlowRow(
    modifier = modifier,
    horizontalArrangement = horizontalArrangement,
  ) {
    chipsDataMap.forEach { item: Map.Entry<String, FilterChipValueData> ->
      if (showChecked) {
        when(item.value) {
          is FilterChipValueData.DropDownChipValueData -> {
            CheckedFilterChip(
              selected = selectedFilterItems?.let { itemList -> item.key in itemList.map { it.key } } ?: false,
              onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == item.key }) },
              label = item.value.label,
              dropDownMenuItems = (item.value as FilterChipValueData.DropDownChipValueData).dropDownMenuItems,
              shape = chipShape,
            )
          }
          is FilterChipValueData.ModalBottomSheetChipValueData -> {
            CheckedFilterChip(
              selected = selectedFilterItems?.let { itemList -> item.key in itemList.map { it.key } } ?: false,
              onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == item.key }) },
              label = item.value.label,
              bottomSheetMenuItems = (item.value as FilterChipValueData.ModalBottomSheetChipValueData).modalBottomSheetMenuItems,
              shape = chipShape,
            )
          }
        }
      } else {
        when(item.value) {
          is FilterChipValueData.DropDownChipValueData -> {
            BasicDropDownFilterChip(
              selected = selectedFilterItems?.let { itemList -> item.key in itemList.map { it.key } } ?: false,
              onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == item.key }) },
              label = item.value.label,
              dropDownMenuItems = (item.value as FilterChipValueData.DropDownChipValueData).dropDownMenuItems,
              shape = chipShape,
            )
          }
          is FilterChipValueData.ModalBottomSheetChipValueData -> {
            BasicFilterChip(
              selected = selectedFilterItems?.let { itemList -> item.key in itemList.map { it.key } } ?: false,
              onClickFilterChip = { onChipClicked(groupChipsDataList.last { it.key == item.key }) },
              label = item.value.label,
              bottomSheetMenuItems = (item.value as FilterChipValueData.ModalBottomSheetChipValueData).modalBottomSheetMenuItems,
              shape = chipShape,
            )
          }
        }
      }
    }
  }
}

/**
 * Single-select
 *
 * @param selectedFilterItem [FilterChipData]? - determines which (if any) filter chips are marked selected. keys are all that are important, label and dropDownMenuItems are not compared.
 * @param groupChipsDataList List<[FilterChipData]> - all key values should be unique.
 *  if there are duplicates of keys, the last duplicate will replace the first and all others will be ignored.
 */
@Composable
fun FilterChipGroupFlowRow(
  modifier: Modifier = Modifier,
  horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(20.dp),
  selectedFilterItem: FilterChipData?,
  groupChipsDataList: List<FilterChipData>,
  onChipClicked: (selectedFilterChipData: FilterChipData) -> Unit,
  showChecked: Boolean = false,
  chipShape: Shape = MaterialTheme.shapes.large,
) {
  FilterChipGroupFlowRow(
    modifier = modifier,
    horizontalArrangement = horizontalArrangement,
    selectedFilterItems = selectedFilterItem?.let { listOf(it) },
    groupChipsDataList = groupChipsDataList,
    onChipClicked = onChipClicked,
    showChecked = showChecked,
    chipShape = chipShape,
  )
}
//endregion

@Composable
private fun FilterChipDropDownMenuBox(
  expanded: Boolean = false,
  onDismissRequest: () -> Unit,
  actions: List<DropdownMenuItemData>?,
  onFilterChipClick: () -> Unit,
  content: @Composable () -> Unit,
) {
  actions?.let {
    Box {
      content()
      DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
      ) {
        actions.forEach {
          DropdownMenuItem(
            text = { Text(it.labelText) },
            onClick = {
              it.onClick()
              onFilterChipClick()
              if (it.callDismissOnClick) onDismissRequest()
            },
          )
        }
      }
    }
  }
}

@Composable
private fun FilterChipBottomSheetContent(
  filterChipBottomSheetMenuItems: List<FilterChipBottomSheetMenuItem>?,
  chipOnClick: () -> Unit,
  dismissBottomSheet: () -> Unit,
) {
  filterChipBottomSheetMenuItems?.forEach {
    val onClick = {
      it.onClick()
      chipOnClick()
      if (it.callDismissOnClick) {
        dismissBottomSheet()
      }
    }
    BottomSheetMenuListItem(onClick, it.menuRow)
  }
  val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
  Spacer(modifier = Modifier.height(bottomPadding))
}

@Composable
fun BottomSheetMenuListItem(
  onClick: () -> Unit,
  menuRow: @Composable () -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(
        onClick = onClick
      )
      .padding(20.dp)
  ) {
    menuRow.invoke()
  }
  HorizontalDivider()
}

@ColorThemePreview
@Composable
private fun FilterChipBottomSheetContentPreview() {
  val context = LocalContext.current
  HelloWorldTheme {
    Surface {
      Column {
        FilterChipBottomSheetContent(
          filterChipBottomSheetMenuItems = previewBottomSheetMenuItemsList(context),
          chipOnClick = {},
          dismissBottomSheet = {}
        )
      }
    }
  }
}

private fun previewBottomSheetMenuItemsList(context: Context) =
  listOf(
    FilterChipBottomSheetMenuItem(
      menuRow = {
        Text("Option 1")
      },
      onClick = { context.toast("Option C - 1 selected")},
      callDismissOnClick = true,
    ),
    FilterChipBottomSheetMenuItem(
      menuRow = {
        Text("Option 2")
      },
      onClick = { context.toast("Option C - 2 selected")},
      callDismissOnClick = true,
    ),
    FilterChipBottomSheetMenuItem(
      menuRow = {
        Text("Option 3")
      },
      onClick = { context.toast("Option C - 3 selected")},
      callDismissOnClick = true,
    ),
  )

private fun previewChipsBottomSheetDataList(context: Context) =
  listOf(
    FilterChipData.ModalBottomSheetChip(
      key = "A",
      label = "A",
      modalBottomSheetMenuItems = null,
    ),
    FilterChipData.ModalBottomSheetChip(
      key = "B",
      label = "B",
      modalBottomSheetMenuItems = null,
    ),
    FilterChipData.ModalBottomSheetChip(
      key = "C",
      label = "C",
      modalBottomSheetMenuItems = previewBottomSheetMenuItemsList(context),
    ),
  )

private fun previewDropDownMenuItemsList(context: Context) =
  listOf(
    DropdownMenuItemData(
      labelText = "Option 1",
      onClick = { context.toast("Option 1 selected")},
      callDismissOnClick = true,
    ),
    DropdownMenuItemData(
      labelText = "Option 2",
      onClick = { context.toast("Option 2 selected")},
      callDismissOnClick = true,
    ),
    DropdownMenuItemData(
      labelText = "Option 3",
      onClick = { context.toast("Option 3 selected")},
      callDismissOnClick = true,
    ),
  )

private fun previewChipsDropDownDataList(context: Context) =
  listOf(
    FilterChipData.DropDownChip(
      key = "A",
      label = "A",
      dropDownMenuItems = null,
    ),
    FilterChipData.DropDownChip(
      key = "B",
      label = "B",
      dropDownMenuItems = null,
    ),
    FilterChipData.DropDownChip(
      key = "C",
      label = "C",
      dropDownMenuItems = previewDropDownMenuItemsList(context),
    ),
  )

@ColorThemePreview
@Composable
private fun FilterChipsWithMenusPreview() {
  val context = LocalContext.current

  HelloWorldTheme {
    Surface {
      Scaffold(
        topBar = { Text(text = "Testing") },
      ) { paddingValues ->
        Box(
          Modifier.padding(paddingValues = paddingValues)
        ) {
          Column {
            var selectedItems1 by remember { mutableStateOf(FilterChipData.DropDownChip(key = "A")) }
            FilterChipGroupHorizontalRow(
              contentPadding = PaddingValues(start = 20.dp),
              selectedFilterItem = selectedItems1,
              groupChipsDataList = previewChipsDropDownDataList(context),
              onChipClicked = { selectedItems1 = it as FilterChipData.DropDownChip },
            )
            var selectedItems2 by remember { mutableStateOf(FilterChipData.ModalBottomSheetChip(key = "A")) }
            FilterChipGroupHorizontalRow(
              contentPadding = PaddingValues(start = 20.dp),
              selectedFilterItem = selectedItems2,
              groupChipsDataList = previewChipsBottomSheetDataList(context),
              onChipClicked = { selectedItems2 = it as FilterChipData.ModalBottomSheetChip },
            )
          }
        }
      }
    }
  }
}

@ColorThemePreview
@Composable
private fun FilterChipGroupFlowRowBottomSheetMenuPreview() {
  val groupOfChipsData = listOf(
    FilterChipData.ModalBottomSheetChip(key = "1", label = "A"),
    FilterChipData.ModalBottomSheetChip(key = "2", label = "B"),
    FilterChipData.ModalBottomSheetChip(key = "3", label = "C"),
    FilterChipData.ModalBottomSheetChip(key = "4", label = "D"),
    FilterChipData.ModalBottomSheetChip(key = "5", label = "E"),
    FilterChipData.ModalBottomSheetChip(key = "6", label = "F"),
    FilterChipData.ModalBottomSheetChip(key = "7", label = "G"),
    FilterChipData.ModalBottomSheetChip(key = "8", label = "H"),
  )

  HelloWorldTheme {
    Surface {
      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Checked (Single-select): ")
          FilterChipGroupFlowRow(
            selectedFilterItem = groupOfChipsData[2],
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
            showChecked = true,
          )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Checked (Multi-select): ")
          FilterChipGroupFlowRow(
            selectedFilterItems = listOf(groupOfChipsData[0], groupOfChipsData[2]),
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
            showChecked = true,
          )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Basic (Single-select): ")
          FilterChipGroupFlowRow(
            selectedFilterItem = groupOfChipsData[2],
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
          )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Basic (Multi-select): ")
          FilterChipGroupFlowRow(
            selectedFilterItems = listOf(groupOfChipsData[0], groupOfChipsData[2]),
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
          )
        }
      }
    }
  }
}

@ColorThemePreview
@Composable
private fun FilterChipGroupHorizontalRowBottomSheetMenuPreview() {
  val groupOfChipsData = listOf(
    FilterChipData.ModalBottomSheetChip(key = "1", label = "A"),
    FilterChipData.ModalBottomSheetChip(key = "2", label = "B_to be replaced"),//will be replaced by duplicate found later in list
    FilterChipData.ModalBottomSheetChip(key = "3", label = "C"),
    FilterChipData.ModalBottomSheetChip(key = "4", label = "D"),
    FilterChipData.ModalBottomSheetChip(key = "2", label = "B"),
    FilterChipData.ModalBottomSheetChip(key = "5", label = "E"),
    FilterChipData.ModalBottomSheetChip(key = "6", label = "F"),
    FilterChipData.ModalBottomSheetChip(key = "7", label = "G"),
    FilterChipData.ModalBottomSheetChip(key = "8", label = "H"),
  )

  HelloWorldTheme {
    Surface {
      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Checked (Single-select): ")
          FilterChipGroupHorizontalRow(
            selectedFilterItem = groupOfChipsData[2],
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
            showChecked = true,
          )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Checked (Multi-select): ")
          FilterChipGroupHorizontalRow(
            selectedFilterItems = listOf(groupOfChipsData[0], groupOfChipsData[2]),
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
            showChecked = true,
          )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Basic (Single-select): ")
          FilterChipGroupHorizontalRow(
            selectedFilterItem = groupOfChipsData[2],
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
          )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Basic (Multi-select): ")
          FilterChipGroupHorizontalRow(
            selectedFilterItems = listOf(groupOfChipsData[0], groupOfChipsData[2]),
            groupChipsDataList = groupOfChipsData,
            onChipClicked = {},
          )
        }
      }
    }
  }
}
