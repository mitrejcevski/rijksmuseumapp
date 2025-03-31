package nl.jovmit.rmapp.ui.artworks

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val artWorksModule = module {

  viewModel<ArtWorksViewModel> {
    ArtWorksViewModel(
      artWorksRepository = get(),
      backgroundDispatcher = get(),
      eventsQueries = get()
    )
  }
}