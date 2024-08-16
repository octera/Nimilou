package info.octera.droidstorybox.presentation.settings_appinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.octera.droidstorybox.BuildConfig
import info.octera.droidstorybox.R
import info.octera.droidstorybox.presentation.common.Disclaimer

@Preview
@Composable
fun AppInfoScreen() {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        modifier = Modifier,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_splash),
                contentDescription = "",
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                contentScale = ContentScale.Fit,
            )
            Text(
                "Nimilou",
                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
                )
            Text(
                "Build with <3 in OpenSource",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                "" + BuildConfig.VERSION_CODE + " - " + BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                uriHandler.openUri("https://github.com/octera/DroidStoryBox")
            }) {
                Text(text = "Github")
            }
            Button(onClick = {
                uriHandler.openUri("https://github.com/octera/DroidStoryBox/issues/new")
            }) {
                Text(text = "Report Issue")
            }
            Button(onClick = {
                uriHandler.openUri("https://buymeacoffee.com/octera")
            }) {
                Text(text = "Buy Me a coffee")
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Disclaimer()
            }

        }
    }
}