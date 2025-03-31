package nl.jovmit.rmapp.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import nl.jovmit.rmapp.EventsDb
import nl.jovmit.rmapp.EventsQueries
import org.koin.dsl.module

val databaseModule = module {
  single<SqlDriver> { AndroidSqliteDriver(EventsDb.Schema, get(), "events.db") }

  single<EventsDb> { EventsDb(get()) }

  single<EventsQueries> { get<EventsDb>().eventsQueries }
}