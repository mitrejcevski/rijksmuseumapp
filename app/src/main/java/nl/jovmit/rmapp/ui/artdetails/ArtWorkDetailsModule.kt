package nl.jovmit.rmapp.ui.artdetails

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val artWorkDetailsModule = module {

  viewModel<ArtWorkDetailsViewModel> {
    ArtWorkDetailsViewModel(
      artWorksRepository = get(),
      backgroundDispatcher = get(),
      savedStateHandle = get()
    )
  }
}