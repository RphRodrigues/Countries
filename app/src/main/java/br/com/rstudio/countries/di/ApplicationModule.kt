package br.com.rstudio.countries.di

import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.GlideImageLoader
import br.com.rstudio.countries.arch.ImageLoader
import br.com.rstudio.countries.arch.network.RetrofitClient
import br.com.rstudio.countries.arch.observability.analytics.AnalyticsReport
import br.com.rstudio.countries.arch.observability.analytics.FirebaseAnalyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.CrashlyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.FirebaseCrashlyticsReport
import br.com.rstudio.countries.arch.observability.crashlytics.FirebaseCrashlyticsReportTree
import br.com.rstudio.countries.data.CountryApi
import br.com.rstudio.countries.data.model.CountryMapper
import br.com.rstudio.countries.data.repository.CountryRepository
import br.com.rstudio.countries.data.repository.CountryRepositoryImpl
import br.com.rstudio.countries.presentation.details.screen.DetailsContract
import br.com.rstudio.countries.presentation.details.screen.DetailsPresenter
import br.com.rstudio.countries.presentation.details.screen.DetailsTracker
import br.com.rstudio.countries.presentation.listscreen.ListContract
import br.com.rstudio.countries.presentation.listscreen.ListPresenter
import br.com.rstudio.countries.presentation.listscreen.ListTracker
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val ApplicationModule = module {

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

  single {
    Glide.with(androidContext())
      .setDefaultRequestOptions(
        RequestOptions()
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .placeholder(android.R.drawable.stat_sys_download)
          .error(R.drawable.ic_error)
          .centerCrop()
      )
  }

  single<ImageLoader> {
    GlideImageLoader(glide = get())
  }

  single { CountryMapper() }

  single<CountryRepository> {
    CountryRepositoryImpl(api = get(), mapper = get())
  }

  factory<ListContract.Tracker> {
    ListTracker(analyticsReport = get())
  }

  factory<ListContract.Presenter> { (view: ListContract.View) ->
    ListPresenter(view = view, repository = get(), tracker = get())
  }

  factory<DetailsContract.Tracker> {
    DetailsTracker(analyticsReport = get())
  }

  factory<DetailsContract.Presenter> { (view: DetailsContract.View) ->
    DetailsPresenter(view = view, tracker = get())
  }
}
