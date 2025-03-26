package id.dreamfighter.kmp.auth.model

import id.dreamfighter.kmp.auth.datastore.StorageData
import id.dreamfighter.kmp.auth.datastore.getStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface Preference {
    suspend fun updateUser(user: User)
    suspend fun deleteUser()
    fun getUser(): Flow<User>
}

class PreferenceImpl(private val storageData: StorageData<PreferenceData> = getStorage()) :
    Preference {

    override suspend fun deleteUser() {
        storageData.updateData { data ->
            val userData = data.user?.copy(uid = "", name = "", email = "NONE", accessToken = "", address = "", phone = "")
            data.copy(user = userData)
        }
    }

    override suspend fun updateUser(user: User) {

        storageData.updateData { data ->
            val userData = data.user?.copy(uid = user.uid, name = user.name, email = user.email, accessToken = user.accessToken, address = user.address, phone = user.phone)
                ?:UserData(uid = user.uid, name = user.name, email = user.email, accessToken = user.accessToken, address = user.address, phone = user.phone)
            data.copy(user = userData)
        }
    }

    override fun getUser(): Flow<User> {
        return storageData.data.map { data ->
            data.user?.run {
                User(uid = uid, email = email, accessToken = accessToken, name = name, address = address, phone = phone)
            }?: User()
        }
    }
}

object Storage {
    private val prefs = PreferenceImpl()
    fun getPreference() = prefs
}