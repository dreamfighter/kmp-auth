package id.dreamfighter.kmp.auth.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.dreamfighter.kmp.auth.generated.resources.Res
import com.github.dreamfighter.kmp.auth.generated.resources.google_logo
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.google.GoogleButtonUiContainer
import id.dreamfighter.kmp.auth.model.AccountService
import id.dreamfighter.kmp.auth.model.User
import id.dreamfighter.kmp.auth.utils.FileUtil
import id.dreamfighter.kmp.auth.utils.FileUtil.toObject
import id.dreamfighter.kmp.auth.viewmodel.AccountViewModel
import org.jetbrains.compose.resources.painterResource

class AndroidAuthProvider:AuthProvider{
    private val json = FileUtil.readFile("google-services.json")
    private val accountService: AccountService = json.toObject<AccountService>()

    @Composable
    override fun SignIn(modifier: Modifier?, signedInUser: (User) -> Unit?){
        val viewModel: AccountViewModel = viewModel<AccountViewModel> {
            AccountViewModel()
        }
        val signInState = viewModel.uiState.collectAsState()
        GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = accountService.clientId))

        LaunchedEffect(signInState.value){
            println(signInState.value.data)
            signInState.value.data?.let { signedInUser(it) }
        }

        GoogleButtonUiContainer(modifier = modifier?:Modifier, onGoogleSignInResult = { gUser ->
            val idToken = gUser?.idToken // Send this idToken to your backend to verify

            println(idToken)
            if (idToken != null) {
                viewModel.signInGoogle(idToken)
            }
        }) {
            LoadingButton(
                onClick = {  this.onClick() },
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    Icon(
                        painterResource(Res.drawable.google_logo),
                        contentDescription = "content description",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                },
                loading = signInState.value.loading,
            ) {
                Text("Google", color = Color.White)
            }
            /*
            OutlinedButton(
                onClick = {
                    //onNavigate(AppScreen.Home.route)
                    this.onClick()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),

                ) {
                Icon(
                    painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "content description",
                    tint = AppTheme.extendedColors.topBarColor, modifier = Modifier.size(36.dp))
                Text("Google", color = Color.White)
            }

             */
        }
    }
}
actual val Auth: AuthProvider = AndroidAuthProvider()