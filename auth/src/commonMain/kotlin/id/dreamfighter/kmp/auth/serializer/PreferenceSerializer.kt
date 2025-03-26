package id.dreamfighter.kmp.auth.serializer

import androidx.datastore.core.okio.OkioSerializer
import id.dreamfighter.kmp.auth.model.PreferenceData
import okio.BufferedSink
import okio.BufferedSource
import okio.IOException

object PreferenceSerializer : OkioSerializer<PreferenceData> {
    override val defaultValue: PreferenceData
        get() = PreferenceData()

    override suspend fun readFrom(source: BufferedSource): PreferenceData {
        try {
            return PreferenceData.ADAPTER.decode(source)
        } catch (exception: IOException) {
            throw Exception(exception.message ?: "Serialization Exception")
        }
    }

    override suspend fun writeTo(t: PreferenceData, sink: BufferedSink) {
        sink.write(t.encode())
    }
}