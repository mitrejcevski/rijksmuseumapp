package nl.jovmit.rmapp.ui.artdetails

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val artWorkDetailsModule = module {

  viewModel<ArtWorkDetailsViewModel> {
    ArtWorkDetailsViewModel(
      artWorksRepository = get(),
      backgroundDispatcher = get(),
    )
  }
}