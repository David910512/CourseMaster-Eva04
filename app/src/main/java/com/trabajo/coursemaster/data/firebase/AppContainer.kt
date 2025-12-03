package com.trabajo.coursemaster.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trabajo.coursemaster.data.repository.AuthRepository
import com.trabajo.coursemaster.data.repository.ProductRepository

object AppContainer {

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val authRepository: AuthRepository by lazy {
        AuthRepository(auth = auth)
    }

    val productRepository: ProductRepository by lazy {
        ProductRepository(firestore)
    }
}