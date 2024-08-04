package info.octera.droidstorybox.presentation.common

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

data class MyDropDownMenuItem<T>(
    val label: String,
    val entity: T
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> MyExposedDropdownMenu(
    modifier: Modifier = Modifier,
    label: String,
    options: List<MyDropDownMenuItem<T>>,
    onSelectedOptionChange: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    onSelectedOptionChange(selectedOption.entity)


    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = selectedOption.label,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        if (selectedOption != option) {
                            selectedOption = option
                            onSelectedOptionChange(option.entity)
                        }
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
