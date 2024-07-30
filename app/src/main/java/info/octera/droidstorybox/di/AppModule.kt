package info.octera.droidstorybox.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.octera.droidstorybox.data.local.NewsDao
import info.octera.droidstorybox.data.local.NewsDatabase
import info.octera.droidstorybox.data.local.NewsTypeConverter
import info.octera.droidstorybox.data.manager.LocalUserManagerImpl
import info.octera.droidstorybox.data.remote.NewsApi
import info.octera.droidstorybox.data.repository.NewsRepositoryImpl
import info.octera.droidstorybox.domain.manager.LocalUserManager
import info.octera.droidstorybox.domain.repository.NewsRepository
import info.octera.droidstorybox.domain.usecases.app_entry.AppEntryUseCases
import info.octera.droidstorybox.domain.usecases.app_entry.ReadAppEntry
import info.octera.droidstorybox.domain.usecases.app_entry.SaveAppEntry
import info.octera.droidstorybox.domain.usecases.news.DeleteArticle
import info.octera.droidstorybox.domain.usecases.news.GetNews
import info.octera.droidstorybox.domain.usecases.news.NewsUseCases
import info.octera.droidstorybox.domain.usecases.news.SearchNews
import info.octera.droidstorybox.domain.usecases.news.SelectArticle
import info.octera.droidstorybox.domain.usecases.news.SelectArticles
import info.octera.droidstorybox.domain.usecases.news.UpsertArticle
import info.octera.droidstorybox.util.Constants.BASE_URL
import info.octera.droidstorybox.util.Constants.NEW_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.octera.droidstorybox.data.local.PackSourcesDao
import info.octera.droidstorybox.data.repository.PackSourcesRepositoryImpl
import info.octera.droidstorybox.domain.repository.PackSourcesRepository
import info.octera.droidstorybox.domain.usecases.pack_sources.DeletePackSource
import info.octera.droidstorybox.domain.usecases.pack_sources.GetPackSources
import info.octera.droidstorybox.domain.usecases.pack_sources.PackSourcesUseCases
import info.octera.droidstorybox.domain.usecases.pack_sources.UpsertPackSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManger: LocalUserManager): AppEntryUseCases =
        AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManger),
            saveAppEntry = SaveAppEntry(localUserManger),
        )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePackSourcesRepository(packSourcesDao: PackSourcesDao): PackSourcesRepository {
        return PackSourcesRepositoryImpl(packSourcesDao)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao,
    ): NewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

    @Provides
    @Singleton
    fun providePackSourcesUseCases(
        packSourcesRepository: PackSourcesRepository,
    ): PackSourcesUseCases {
        return PackSourcesUseCases(
            getPackSources = GetPackSources(packSourcesRepository),
            deletePackSource = DeletePackSource(packSourcesRepository),
            upsertPackSource = UpsertPackSource(packSourcesRepository),
        )
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository),
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEW_DATABASE,
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao
    }

    @Provides
    @Singleton
    fun providePackSourcesDao(
        newsDatabase: NewsDatabase
    ): PackSourcesDao {
        return newsDatabase.packSourcesDao
    }
}
