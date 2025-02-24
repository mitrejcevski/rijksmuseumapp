package nl.jovmit.rmapp.domain

import org.koin.dsl.module

val domainModule = module {

  factory<ArtWorksRepository> {
    RemoteArtWorksRepository(
      museumApi = get()
    )
  }
}