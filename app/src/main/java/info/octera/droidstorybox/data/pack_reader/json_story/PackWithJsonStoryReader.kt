package info.octera.droidstorybox.data.pack_reader.json_story

import android.net.Uri
import androidx.core.net.toUri
import com.google.gson.GsonBuilder
import info.octera.droidstorybox.data.pack_reader.json_story.model.Story
import info.octera.droidstorybox.data.pack_reader.json_story.model.StoryActionNode
import info.octera.droidstorybox.data.pack_reader.json_story.model.StoryControlSettings
import info.octera.droidstorybox.data.pack_reader.json_story.model.StoryTransition
import info.octera.droidstorybox.data.pack_reader.json_story.model.StoryStageNode
import info.octera.droidstorybox.data.pack_reader.json_story.model.StoryStageType
import info.octera.droidstorybox.domain.model.pack.Action
import info.octera.droidstorybox.domain.model.pack.ControlSettings
import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.model.pack.Stage
import info.octera.droidstorybox.domain.model.pack.StageType
import info.octera.droidstorybox.domain.model.pack.Transition
import java.io.File
import java.io.InputStreamReader
import java.util.zip.ZipFile


class PackWithJsonStoryReader {
    private val storyRootFileName = "story.json"
    private val thumbnailFileName = "thumbnail.png"

    fun readPackMetaData(file: File): PackMetadata {
        ZipFile(file).use { zip ->
            val story = getStoryFile(zip)

            val thumbnail = getFileAsByteArray(zip, thumbnailFileName)

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

    fun readPack(file: File): Pack {
        ZipFile(file).use { zip ->
            val story = getStoryFile(zip)

            val thumbnail = getFileAsByteArray(zip, thumbnailFileName)

            val metaData = PackMetadata(
                title = story.title,
                description = story.description,
                nightModeAvailable = story.nightModeAvailable,
                format = story.format,
                version = story.version,
                age = getAgeFromFilename(file.name),
                uri = file.toUri(),
                thumbsnail = thumbnail
            )

            return Pack(
                metadata = metaData,
                stages = story.stageNodes.map{convertToStage(file, it)},
                actions = story.actionNodes.map(::convertToAction),
            )
        }
    }

    private fun getStoryFile(zip: ZipFile): Story {
        val storyEntry = zip.entries().asSequence().first { it.name == storyRootFileName }
        val storyReader = InputStreamReader(zip.getInputStream(storyEntry))
        return GsonBuilder().create().fromJson(storyReader, Story::class.java)
    }
    private fun getFileAsByteArray(zip: ZipFile, fileName: String) : ByteArray {
        val thumbnailEntry = zip.entries().asSequence().first { it.name == fileName }
        return zip.getInputStream(thumbnailEntry).readBytes()
    }

    private fun convertToStage(file:File, storyStageNode: StoryStageNode): Stage {
        return Stage(
            uuid = storyStageNode.uuid,
            type = mapStageType(storyStageNode.type),
            name = storyStageNode.name,
            image = storyStageNode.image,
            audio = Uri.parse(file.toString() + "##" + storyStageNode.audio),
            okTransition = mapTransition(storyStageNode.okTransition),
            homeTransition = mapTransition(storyStageNode.homeTransition),
            controlSettings = mapControlSettings(storyStageNode.controlSettings),
            squareOne = storyStageNode.squareOne
        )
    }

    private fun mapControlSettings(controlSettings: StoryControlSettings) : ControlSettings {
        return ControlSettings(
            ok = controlSettings.ok,
            home = controlSettings.home,
            pause = controlSettings.pause,
            wheel = controlSettings.wheel,
            autoplay = controlSettings.autoplay
        )
    }

    private fun mapTransition(transition: StoryTransition?): Transition? {
        return transition?.let {
            Transition(
               actionNode = transition.actionNode,
                optionIndex = transition.optionIndex
            )
        }
    }

    private fun mapStageType(type: StoryStageType): StageType {
        return when (type) {
            StoryStageType.STAGE -> StageType.STAGE
            StoryStageType.STORY -> StageType.STORY
            StoryStageType.COVER -> StageType.COVER
            StoryStageType.MENU_QUESTIONSTAGE -> TODO()
            StoryStageType.MENU_OPTIONSTAGE -> TODO()
        }
    }

    private fun convertToAction(storyActionNode: StoryActionNode): Action {
        return Action(
            id = storyActionNode.id,
            name = storyActionNode.name,
            options = storyActionNode.options
        )
    }
}