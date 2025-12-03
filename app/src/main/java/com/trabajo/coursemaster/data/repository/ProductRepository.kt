package com.trabajo.coursemaster.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.trabajo.coursemaster.data.model.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProductRepository(firestore1: FirebaseFirestore) {
    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")


    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val listener = productsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val products = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Product::class.java)?.copy(id = doc.id)
            } ?: emptyList()
            trySend(products)
        }
        awaitClose { listener.remove() }
    }


    suspend fun createProduct(product: Product) {
        productsCollection.add(product).await()
    }


    suspend fun updateProduct(product: Product) {
        productsCollection.document(product.id).set(product).await()
    }


    suspend fun deleteProduct(productId: String) {
        productsCollection.document(productId).delete().await()
    }
}