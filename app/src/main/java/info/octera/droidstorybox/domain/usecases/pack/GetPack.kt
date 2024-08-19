package info.octera.droidstorybox.domain.usecases.pack

import android.net.Uri
import androidx.core.net.toFile
import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.repository.PackRepository
import javax.inject.Inject

class GetPack @Inject constructor(
    private val packRepository: PackRepository
) {
    operator fun invoke(uri: Uri): Pack {
        return packRepository.readPack(uri.toFile())
    }
}