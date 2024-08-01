package info.octera.droidstorybox.data.remote.pack_source

import info.octera.droidstorybox.data.remote.pack_source.dto.RemotePackDto
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.model.Thumbs
import khttp.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json


class PackSourceApi {

    suspend fun fetchPacks(url: String): List<RemotePack> {
        return withContext(Dispatchers.IO) {
            val response = get(url = url)
            parseResult(response.text)
        }
    }

    private fun parseResult(source: String) : List<RemotePack> {
        return Json.decodeFromString<List<RemotePackDto>>(source)
            .map { RemotePack(
                age = it.age,
                title = it.title,
                description = it.description,
                download = it.download,
                awards = it.awards,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                thumbs = Thumbs(small = it.thumbs.small, medium = it.thumbs.medium)
            ) }
    }

}