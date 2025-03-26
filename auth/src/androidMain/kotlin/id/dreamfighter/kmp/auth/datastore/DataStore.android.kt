package id.dreamfighter.kmp.auth.datastore

import androidx.datastore.core.DataStore
import id.dreamfighter.kmp.auth.applicationContext
import id.dreamfighter.kmp.auth.model.PreferenceData
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath

fun getDataStore(): DataStore<PreferenceData> {
    val content = requireNotNull(applicationContext)
    val producePath = { content.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath() }

    return createDataStore(fileSystem = FileSystem.SYSTEM, producePath = producePath)
}

class AndroidStorage:StorageData<PreferenceData>{
    private val dataStore = getDataStore()
    override suspend fun updateData(transform: suspend (t: PreferenceData) -> PreferenceData): PreferenceData {
        return dataStore.updateData(transform)
    }

    override val data: Flow<PreferenceData>
        get() {
            return dataStore.data
        }
}

actual fun getStorage(): StorageData<PreferenceData> =  AndroidStorage()