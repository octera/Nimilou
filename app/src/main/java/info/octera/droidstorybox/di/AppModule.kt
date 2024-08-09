package info.octera.droidstorybox.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.octera.droidstorybox.data.local.PackSourcesDao
import info.octera.droidstorybox.data.local.PacksDatabase
import info.octera.droidstorybox.data.manager.LocalUserManagerImpl
import info.octera.droidstorybox.data.mediaplayer.ExoMediaPlayerManager
import info.octera.droidstorybox.data.mediaplayer.MediaPlayerManager
import info.octera.droidstorybox.data.remote.BasicHttpSource
import info.octera.droidstorybox.data.repository.PackRepositoryImpl
import info.octera.droidstorybox.data.repository.PackSourcesRepositoryImpl
import info.octera.droidstorybox.data.repository.PacksRepositoryImpl
import info.octera.droidstorybox.domain.manager.LocalUserManager
import info.octera.droidstorybox.domain.repository.PackRepository
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import info.octera.droidstorybox.domain.repository.PacksRepository
import info.octera.droidstorybox.util.Constants.NEW_DATABASE
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMediaPlayerManager(application: Application): MediaPlayerManager {
        val player = ExoPlayer
            .Builder(application)
            .build()
        return ExoMediaPlayerManager(application, player)
    }

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun providePackDatabase(application: Application): PacksDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = PacksDatabase::class.java,
            name = NEW_DATABASE,
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideBasicHttp(): BasicHttpSource {
        return Retrofit.Builder()
            .baseUrl("https://dummy")
            .build()
            .create(BasicHttpSource::class.java)
    }

    @Provides
    @Singleton
    fun providePackSourcesRepository(impl: PackSourcesRepositoryImpl): PackSourcesRepository {
        return impl
    }

    @Provides
    @Singleton
    fun providePacksRepository(impl: PacksRepositoryImpl): PacksRepository {
        return impl
    }

    @Provides
    @Singleton
    fun providePackRepository(impl: PackRepositoryImpl): PackRepository {
        return impl
    }

    @Provides
    @Singleton
    fun providePackSourcesDao(
        packsDatabase: PacksDatabase
    ): PackSourcesDao {
        return packsDatabase.packSourcesDao
    }
}
