package com.yubstore.piking.database

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

interface AppRepository{
    suspend fun newApp(app: App)
    suspend fun deleteApp(toDelete: App)
    fun getAllApp(): LiveData<List<App>>
}
@Singleton
class AppRepositoryImp @Inject constructor(
    private val appDao: AppDao
): AppRepository {
    override suspend fun newApp(app: App){
        //TODO("Not yet implemented")
        return appDao.insert(app)
    }

    override suspend fun deleteApp(toDelete: App) {
        //TODO("Not yet implemented")
        appDao.delete(toDelete)
    }

    override fun getAllApp(): LiveData<List<App>> {
        //TODO("Not yet implemented")
        return appDao.getAll()
    }
}