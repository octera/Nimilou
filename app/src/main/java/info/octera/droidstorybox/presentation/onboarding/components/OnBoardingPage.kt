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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
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
                .padding(24.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = modifier.height(MediumPadding1))
        Text(
            modifier = modifier.padding(horizontal = MediumPadding2),
            text = stringResource(page.title),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small),
        )
        Text(
            modifier = modifier.padding(horizontal = MediumPadding2),
            text = stringResource(page.description),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium),
        )
        page.links?.let {
            stringArrayResource(id = page.links).forEach {
                Text(
                    buildAnnotatedString {
                        withLink(
                            LinkAnnotation.Url(
                                it,
                                TextLinkStyles(style = SpanStyle(color = Color.Blue))
                            )
                        ) {
                            append(it)
                        }
                    }
                )

            }
        }
    }
}
