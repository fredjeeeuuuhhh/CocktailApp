package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.local.cocktails.CocktailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "WifiRefreshCocktailWorker"
class WifiRefreshCocktailWorker(context: Context, params: WorkerParameters, private val cocktailRepository: CocktailRepository) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                cocktailRepository.refreshCocktailsInWorker()
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}
// use the workerstate in overview ui
// val workerState by taskOverviewViewModel.wifiWorkerState.collectAsState()
// Column {
//   when(workerState.workerInfo?.state){
//     null -> Text("state unknown")
//    else -> Text(workerState.workerInfo?.state!!.name)
// }
// data class WorkerState(val workerInfo: WorkInfo? = null) in overview state
// state of the workers, prepared here for the UI
// note, a better approach would use a new data class to represent the state...
// lateinit var wifiWorkerState: StateFlow<WorkerState> viewmodel
// wifiWorkerState = tasksRepository.wifiWorkInfo.map { WorkerState(it)}.stateIn( in load cocktails
// scope = viewModelScope,
// started = SharingStarted.WhileSubscribed(5_000L),
// initialValue = WorkerState(),
// )
