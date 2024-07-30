package info.octera.droidstorybox.domain.usecases.app_entry

import info.octera.droidstorybox.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke()  {
        localUserManager.saveAppEntry()
    }
}
