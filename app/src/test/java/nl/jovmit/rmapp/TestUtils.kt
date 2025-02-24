package nl.jovmit.rmapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch

fun <T> CoroutineScope.observeFlow(
  stateFlow: StateFlow<T>,
  dropInitialValue: Boolean = true,
  block: () -> Unit
): List<T> {
  val result = mutableListOf<T>()
  val collectJob = launch(Dispatchers.Unconfined) {
    stateFlow.toCollection(result)
  }
  block()
  collectJob.cancel()
  return if (dropInitialValue) result.drop(1) else result
}