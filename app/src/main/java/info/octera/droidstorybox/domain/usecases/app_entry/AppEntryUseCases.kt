package info.octera.droidstorybox.domain.usecases.app_entry

import javax.inject.Inject

data class AppEntryUseCases @Inject constructor(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
)
