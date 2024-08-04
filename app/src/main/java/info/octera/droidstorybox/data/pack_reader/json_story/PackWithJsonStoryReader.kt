package info.octera.droidstorybox.data.pack_reader.json_story

import androidx.core.net.toUri
import com.google.gson.GsonBuilder
import info.octera.droidstorybox.data.pack_reader.json_story.model.Story
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import java.io.File
import java.io.InputStreamReader
import java.util.zip.ZipFile


class PackWithJsonStoryReader {
    private val storyRootFileName = "story.json"
    private val thumbnailFileName = "thumbnail.png"

    fun readPackMetaData(file: File): PackMetadata {
        ZipFile(file).use { zip ->
            val storyEntry = zip.entries().asSequence().first { it.name == storyRootFileName }
            val storyReader = InputStreamReader(zip.getInputStream(storyEntry))
            val story = GsonBuilder().create().fromJson(storyReader, Story::class.java)

            val thumbnailEntry = zip.entries().asSequence().first { it.name == thumbnailFileName }
            val thumbnail = zip.getInputStream(thumbnailEntry).readBytes()

            return PackMetadata(
                title = story.title,
                description = story.description,
                nightModeAvailable = story.nightModeAvailable,
                format = story.format,
                version = story.version,
                age = getAgeFromFilename(file.name),
                uri = file.toUri(),
                thumbsnail = thumbnail
            )
        }
    }

    private fun getAgeFromFilename(fileName: String): Int {
        val match = Regex("(\\d*).*").find(fileName)
        if (match != null) {
            val (age) = match.destructured
            return age.toInt()
        }
        return 0
    }
}