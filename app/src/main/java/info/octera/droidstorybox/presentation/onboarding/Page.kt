package info.octera.droidstorybox.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.stringResource
import info.octera.droidstorybox.R

data class Page(
    val title: Int,
    val description: Int,
    @DrawableRes val image: Int,
)

val pages =
    listOf(
        Page(
            title = R.string.onboarding_page1_title,
            description = R.string.onboarding_page1_desc,
            image = R.drawable.icon_splash,
        ),
        Page(
            title = R.string.onboarding_page2_title,
            description = R.string.onboarding_page2_desc,
            image = R.drawable.onboarding2,
        ),
        Page(
            title = R.string.onboarding_page3_title,
            description = R.string.onboarding_page3_desc,
            image = R.drawable.onboarding3,
        ),
    )
