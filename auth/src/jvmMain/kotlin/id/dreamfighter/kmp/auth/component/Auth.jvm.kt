package id.dreamfighter.kmp.auth.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.dreamfighter.kmp.auth.generated.resources.Res
import com.github.dreamfighter.kmp.auth.generated.resources.google_logo
import id.dreamfighter.kmp.auth.GoogleJvm
import id.dreamfighter.kmp.auth.model.User
import id.dreamfighter.kmp.auth.google
import id.dreamfighter.kmp.auth.viewmodel.AccountViewModel
import org.jetbrains.compose.resources.painterResource

class DesktopAuthProvider:AuthProvider{
    //private val json = FileUtil.readFile("google-services.json")
    //private val accountService: AccountService = json.toObject<AccountService>()

    @Composable
    override fun SignIn(modifier: Modifier?, signedInUser: (User) -> Unit?){
        //val preference: Preference = remember { PreferenceImpl() }

        val viewModel: AccountViewModel = viewModel<AccountViewModel> {
            AccountViewModel()
        }
        val signInState = viewModel.uiState.collectAsState()

        LoadingButton(
            onClick = {
                signInState.value.loading = true
                val googleJvm = (google as GoogleJvm)

                googleJvm.authCode(code = {
                    viewModel.signInGoogleWithCode(it)
                })
            },
            modifier = modifier?: Modifier,
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
                //viewModel.signInGoogleWithCode("it")
                val googleJvm = (google as GoogleJvm)

                googleJvm.authCode(code = {
                   viewModel.signInGoogleWithCode(it)
                })
            },
            modifier = modifier?: Modifier,
            shape = RoundedCornerShape(10.dp)) {
            Icon(
                painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "content description",
                tint = AppTheme.extendedColors.topBarColor, modifier = Modifier.size(36.dp))
            Text("Google", color = Color.White)
        }

         */
    }
}

actual val Auth: AuthProvider = DesktopAuthProvider()