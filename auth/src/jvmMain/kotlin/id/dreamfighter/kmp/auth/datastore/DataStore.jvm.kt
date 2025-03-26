package id.dreamfighter.kmp.auth.datastore

import androidx.datastore.core.DataStore
import id.dreamfighter.kmp.auth.model.PreferenceData
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import java.io.File

fun getDataStore(): DataStore<PreferenceData> {
    val producePath = { File(".",DATA_STORE_FILE_NAME).absolutePath.toPath(normalize = true)}

    return createDataStore(fileSystem = FileSystem.SYSTEM, producePath = producePath)
}

class JvmStorage:StorageData<PreferenceData>{
    private val storage = getDataStore()
    override suspend fun updateData(transform: suspend (t: PreferenceData) -> PreferenceData): PreferenceData {
        return storage.updateData(transform)
    }

    override val data: Flow<PreferenceData>
        get() {
            return storage.data
        }
}

actual fun getStorage(): StorageData<PreferenceData>  = JvmStorage()