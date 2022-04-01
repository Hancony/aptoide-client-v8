package cm.aptoide.pt.feature_search.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
      .addInterceptor(interceptor)
      .build()
  }


  @RetrofitV7
  @Provides
  @Singleton
  fun provideRetrofitV7(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://ws75.aptoide.com/api/7/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @RetrofitBuzz
  @Provides
  @Singleton
  fun provideSearchAutoCompleteRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://buzz.aptoide.com:10002")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitBuzz

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitV7







