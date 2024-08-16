package info.octera.droidstorybox.presentation.onboarding

import androidx.annotation.DrawableRes
import info.octera.droidstorybox.R

data class Page(
    val title: Int,
    val description: Int,
    val links: Int?,
    @DrawableRes val image: Int,
)

val pages =
    listOf(
        Page(
            title = R.string.onboarding_page1_title,
            description = R.string.onboarding_page1_desc,
            links = null,
            image = R.drawable.icon_splash,
        ),
        Page(
            title = R.string.onboarding_page2_title,
            description = R.string.onboarding_page2_desc,
            links = R.array.onboarding_page2_links,
            image = R.drawable.onboarding2,
        ),
        Page(
            title = R.string.onboarding_page3_title,
            description = R.string.onboarding_page3_desc,
            links = null,
            image = R.drawable.onboarding3,
        ),
        Page(
            title = R.string.disclaimer_title,
            description = R.string.disclaimer_content,
            links = null,
            image = R.drawable.icon_splash,
        ),
    )
