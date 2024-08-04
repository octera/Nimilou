package info.octera.droidstorybox.data.file

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileSource @Inject constructor(
    @ApplicationContext private val appContext: Context
)
{
    fun getOutputStreamToFile(location: String, fileName: String): FileOutputStream {
        val destinationFile = File(buildDir(location), fileName)
        return destinationFile.outputStream()

    }

    fun listFiles(location: String): List<File> {
        return buildDir(location).listFiles()?.toList() ?: emptyList()
    }

    private fun buildDir(location: String): File {
        val dir = File(appContext.filesDir, location)
        if (!dir.isDirectory) {
            dir.mkdirs()
        }
        return dir

    }
}