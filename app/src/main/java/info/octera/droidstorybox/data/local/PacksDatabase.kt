package info.octera.droidstorybox.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import info.octera.droidstorybox.domain.model.PackSource

@Database(entities = [PackSource::class], version = 2)
abstract class PacksDatabase : RoomDatabase() {
    abstract val packSourcesDao: PackSourcesDao
}