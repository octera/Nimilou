package info.octera.droidstorybox.data.remote.pack_source

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import info.octera.droidstorybox.data.remote.BasicHttpSource
import info.octera.droidstorybox.data.remote.pack_source.dto.RemotePackDto
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.model.RemoteThumbs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject


class PackSourceApi @Inject constructor(
    private val basicHttpSource: BasicHttpSource
) {

    suspend fun fetchPacks(url: String): List<RemotePack> {
        return withContext(Dispatchers.IO) {
            try {
                val response = basicHttpSource.get(url)
                parseResult(response.body()!!.string())
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private fun parseResult(source: String): List<RemotePack> {
        val type = object : TypeToken<List<RemotePackDto>>() {}.type
        return parseArray<List<RemotePackDto>>(source, type)
            .map {
                RemotePack(
                    age = it.age,
                    title = it.title,
                    description = it.description,
                    download = it.download,
                    awards = it.awards,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    thumbs = RemoteThumbs(small = it.thumbs.small, medium = it.thumbs.medium)
                )
            }
    }

    private inline fun <reified T> parseArray(json: String, typeToken: Type): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }

}