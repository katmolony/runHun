package ie.setu.placemark

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.placemark.ui.screens.home.HomeScreen
import ie.setu.placemark.ui.screens.register.RegisterViewModel
import ie.setu.placemark.ui.theme.RunHunTheme

@AndroidEntryPoint
class RunHunMainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val token = account.idToken
                if (token != null) {
                    registerViewModel.signInWithGoogle(token)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
