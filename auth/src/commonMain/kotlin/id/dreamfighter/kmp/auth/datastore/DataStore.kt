package id.dreamfighter.kmp.auth.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import id.dreamfighter.kmp.auth.model.PreferenceData
import id.dreamfighter.kmp.auth.serializer.PreferenceSerializer
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path

internal const val DATA_STORE_FILE_NAME = "proto_datastore.preferences_pb"

interface StorageData<T>{
    suspend fun updateData(transform: suspend (t: T) -> T): T
    val data: Flow<T>
}

expect fun getStorage(): StorageData<PreferenceData>

fun createDataStore(
    fileSystem: FileSystem,
    producePath: () -> Path
): DataStore<PreferenceData> =
    DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = fileSystem,
            producePath = producePath,
            serializer = PreferenceSerializer,
        ),
    )

