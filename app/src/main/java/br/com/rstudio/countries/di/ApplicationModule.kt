package br.com.rstudio.countries.di

import androidx.room.Room
import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.GlideImageLoader
import br.com.rstudio.countries.arch.ImageLoader
import br.com.rstudio.countries.arch.database.AppDatabase
import br.com.rstudio.countries.arch.featuretoggle.FirebaseRemoteConfigImp
import br.com.rstudio.countries.arch.featuretoggle.RemoteConfig
import br.com.rstudio.countries.arch.network.RetrofitClient
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.observability.analytics.FirebaseAnalyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.CrashlyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.FirebaseCrashlyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.FirebaseCrashlyticsReportTree
import br.com.rstudio.countries.arch.widget.imageLoaderShimmerDrawable
import br.com.rstudio.countries.data.CountryApi
import br.com.rstudio.countries.data.datasource.CountryDataSource
import br.com.rstudio.countries.data.datasource.CountryRemoteDataSourceImp
import br.com.rstudio.countries.data.datasource.QuizDataSource
import br.com.rstudio.countries.data.datasource.QuizLocalDataSourceImp
import br.com.rstudio.countries.data.datasource.QuizRemoteDataSourceImp
import br.com.rstudio.countries.data.model.CountryMapper
import br.com.rstudio.countries.data.repository.CountryRepository
import br.com.rstudio.countries.data.repository.CountryRepositoryImpl
import br.com.rstudio.countries.data.repository.QuizRepository
import br.com.rstudio.countries.data.repository.QuizRepositoryImp
import br.com.rstudio.countries.domain.GenerateQuizUseCase
import br.com.rstudio.countries.domain.SaveQuizAnsweredUseCase
import br.com.rstudio.countries.presentation.details.screen.DetailsContract
import br.com.rstudio.countries.presentation.details.screen.DetailsPresenter
import br.com.rstudio.countries.presentation.details.screen.DetailsTracker
import br.com.rstudio.countries.presentation.homescreen.v1.ListContract
import br.com.rstudio.countries.presentation.homescreen.v1.ListPresenter
import br.com.rstudio.countries.presentation.homescreen.v1.ListTracker
import br.com.rstudio.countries.presentation.homescreen.v2.HomeContract
import br.com.rstudio.countries.presentation.homescreen.v2.HomePresenter
import br.com.rstudio.countries.presentation.homescreen.v2.HomeTracker
import br.com.rstudio.countries.presentation.quizscreen.QuizViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val applicationModule = module {

  single<AppDatabase> {
    Room.databaseBuilder(
      androidContext(),
      AppDatabase::class.java, androidContext().getString(R.string.db_name)
    ).build()
  }

  single {
    RetrofitClient(androidContext()).newInstance()
  }

  single<CountryApi> {
    get<Retrofit>().create()
  }

  single<FirebaseCrashlytics> {
    FirebaseCrashlytics.getInstance()
  }

  single<CrashlyticsReport> {
    FirebaseCrashlyticsReport(firebaseCrashlytics = get())
  }

  single {
    FirebaseCrashlyticsReportTree(crashlyticsReport = get())
  }

  single<FirebaseAnalytics> {
    FirebaseAnalytics.getInstance(androidContext())
  }

  single<AnalyticsReport> {
    FirebaseAnalyticsReport(analytics = get())
  }

  single<FirebaseRemoteConfig> {
    FirebaseRemoteConfig.getInstance()
  }

  single<RemoteConfig> {
    FirebaseRemoteConfigImp(remoteConfig = get())
  }

  single {
    Glide.with(androidContext())
      .setDefaultRequestOptions(
        RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .placeholder(imageLoaderShimmerDrawable())
          .error(R.drawable.ic_error)
          .centerCrop()
      )
  }

  single<ImageLoader> {
    GlideImageLoader(glide = get())
  }

  single { CountryMapper() }

  single<CountryDataSource>(named("remote")) {
    CountryRemoteDataSourceImp(api = get(), mapper = get())
  }

  single<CountryRepository> {
    CountryRepositoryImpl(remoteDataSource = get(named("remote")))
  }

  factory<ListContract.Tracker> {
    ListTracker(analyticsReport = get())
  }

  factory<ListContract.Presenter> { (view: ListContract.View) ->
    ListPresenter(view = view, repository = get(), tracker = get())
  }

  factory<HomeContract.Tracker> {
    HomeTracker(analyticsReport = get())
  }

  factory<HomeContract.Presenter> { (view: HomeContract.View) ->
    HomePresenter(view = view, repository = get(), tracker = get())
  }

  factory<DetailsContract.Tracker> {
    DetailsTracker(analyticsReport = get())
  }

  factory<DetailsContract.Presenter> { (view: DetailsContract.View) ->
    DetailsPresenter(view = view, tracker = get())
  }

  single<QuizDataSource>(named("local")) {
    QuizLocalDataSourceImp(roomDb = get())
  }

  single<QuizDataSource>(named("remote")) {
    QuizRemoteDataSourceImp(countryRepository = get())
  }

  factory<QuizRepository> {
    QuizRepositoryImp(
      localDataSource = get(named("local")),
      remoteDataSource = get(named("remote"))
    )
  }

  factory {
    GenerateQuizUseCase(quizRepository = get())
  }

  factory {
    SaveQuizAnsweredUseCase(quizRepository = get())
  }

  viewModel {
    QuizViewModel(generateQuizUseCase = get(), saveQuizAnsweredUseCase = get())
  }
}
