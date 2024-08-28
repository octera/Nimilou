package info.octera.droidstorybox.data.remote.pack_source

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import info.octera.droidstorybox.data.remote.BasicHttpSource
import info.octera.droidstorybox.data.remote.pack_source.dto.RemotePackDto
import info.octera.droidstorybox.data.remote.pack_source.dto.TelmiResponse
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.model.RemoteThumbs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject
import kotlin.reflect.typeOf


class PackSourceApi @Inject constructor(
    private val basicHttpSource: BasicHttpSource
) {

    suspend fun fetchPacks(url: String): List<RemotePack> {
        return withContext(Dispatchers.IO) {
            try {
                val response = basicHttpSource.get(url)
                parseResult(response.body()!!.string())
            } catch (e: Exception) {
                Log.e("PACKSOURCEAPI", "Error during deserialize", e)
                emptyList()
            }
        }
    }

    private fun parseResult(source: String): List<RemotePack> {
        val gson = GsonBuilder().create()
        return gson.fromJson(source, TelmiResponse::class.java)
            .data
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
}