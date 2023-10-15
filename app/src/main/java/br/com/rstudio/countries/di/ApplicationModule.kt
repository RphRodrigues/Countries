package br.com.rstudio.countries.di

import br.com.rstudio.countries.R
import br.com.rstudio.countries.arch.GlideImageLoader
import br.com.rstudio.countries.arch.ImageLoader
import br.com.rstudio.countries.arch.network.RetrofitClient
import br.com.rstudio.countries.arch.observability.CrashlyticsReporting
import br.com.rstudio.countries.arch.observability.CrashlyticsReportingTree
import br.com.rstudio.countries.arch.observability.FirebaseCrashlyticsReporting
import br.com.rstudio.countries.data.CountryApi
import br.com.rstudio.countries.data.model.CountryMapper
import br.com.rstudio.countries.data.repository.CountryRepository
import br.com.rstudio.countries.data.repository.CountryRepositoryImpl
import br.com.rstudio.countries.presentation.details.screen.DetailsContract
import br.com.rstudio.countries.presentation.details.screen.DetailsPresenter
import br.com.rstudio.countries.presentation.listscreen.ListContract
import br.com.rstudio.countries.presentation.listscreen.ListPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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

  single<CrashlyticsReporting> {
    FirebaseCrashlyticsReporting(firebaseCrashlytics = get())
  }

  single {
    CrashlyticsReportingTree(crashlyticsReporting = get())
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

  factory<ListContract.Presenter> { (view: ListContract.View) ->
    ListPresenter(view = view, repository = get())
  }

  factory<DetailsContract.Presenter> { (view: DetailsContract.View) ->
    DetailsPresenter(view = view)
  }
}
