package info.octera.droidstorybox.data.repository

import info.octera.droidstorybox.data.pack_reader.json_story.PackWithJsonStoryReader
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.repository.PackRepository
import java.io.File
import javax.inject.Inject

class PackRepositoryImpl @Inject constructor() : PackRepository {

    private fun detectPackReader(file: File): PackWithJsonStoryReader {
        return PackWithJsonStoryReader()
    }

    override fun readPackMetaData(file: File): PackMetadata {
        val reader = detectPackReader(file)
        return reader.readPackMetaData(file)
    }
}