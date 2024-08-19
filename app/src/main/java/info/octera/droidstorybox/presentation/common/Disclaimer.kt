package info.octera.droidstorybox.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import info.octera.droidstorybox.R

@Composable
fun Disclaimer() {
    Text(
        text = stringResource(R.string.disclaimer_title),
        style = MaterialTheme.typography.bodyMedium.copy(),
        modifier = Modifier.padding(4.dp, 8.dp)
    )
    Text(
        text = stringResource(R.string.disclaimer_content),
        style = MaterialTheme.typography.bodySmall.copy(),
    )
}