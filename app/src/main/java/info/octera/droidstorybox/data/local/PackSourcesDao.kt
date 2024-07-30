package info.octera.droidstorybox.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.octera.droidstorybox.domain.model.PackSource
import kotlinx.coroutines.flow.Flow

@Dao
interface PackSourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(packSource: PackSource)

    @Delete
    suspend fun delete(packSource: PackSource)

    @Query("SELECT * FROM PackSource")
    fun getPackSource(): Flow<List<PackSource>>

}