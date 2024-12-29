package ie.setu.placemark.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.setu.placemark.firebase.auth.AuthRepository
import ie.setu.placemark.firebase.database.FirestoreRepository
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        @ApplicationContext context: Context
    ): AuthService = AuthRepository(firebaseAuth = auth)

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirestoreRepository(
        auth: AuthService,
        firebaseFirestore: FirebaseFirestore,
        @ApplicationContext context: Context
    ): FirestoreService = FirestoreRepository(
        auth = auth,
        firestore = firebaseFirestore,
        context = context
    )
}