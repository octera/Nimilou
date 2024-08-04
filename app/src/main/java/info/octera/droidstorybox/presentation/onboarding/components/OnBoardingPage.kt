package info.octera.droidstorybox.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import info.octera.droidstorybox.R
import info.octera.droidstorybox.presentation.Dimens.MediumPadding1
import info.octera.droidstorybox.presentation.Dimens.MediumPadding2
import info.octera.droidstorybox.presentation.onboarding.Page

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = page.image),
            contentDescription = "",
            modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = modifier.height(MediumPadding1))
        Text(
            modifier = modifier.padding(horizontal = MediumPadding2),
            text = page.title,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small),
        )
        Text(
            modifier = modifier.padding(horizontal = MediumPadding2),
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium),
        )
    }
}
