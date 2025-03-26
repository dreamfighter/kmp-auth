package id.dreamfighter.kmp.auth.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.dreamfighter.kmp.auth.model.User

interface AuthProvider{
    @Composable
    fun SignIn(modifier: Modifier?, signedInUser: (User) -> Unit?)
}


expect val Auth:AuthProvider