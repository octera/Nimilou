package info.octera.droidstorybox.domain.repository

import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import java.io.File

interface PackRepository {
    fun readPackMetaData(file: File): PackMetadata
    fun readPack(file: File): Pack
}