package info.octera.droidstorybox.domain.usecases.app_entry

import info.octera.droidstorybox.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}
