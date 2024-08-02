package info.octera.droidstorybox.data.file

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class FileSource @Inject constructor(
    @ApplicationContext private val appContext: Context
)
{
    fun getOutputStreamToFile(fileName: String): FileOutputStream {
        val destinationFile = File(appContext.filesDir, fileName)
        return destinationFile.outputStream()

    }
}