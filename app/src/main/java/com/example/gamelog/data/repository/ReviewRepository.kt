package com.example.gamelog.data.repository

import android.util.Log
import com.example.gamelog.data.model.review.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object ReviewRepository {

    private val db = FirebaseDatabase.getInstance()

    fun addReview(review: Review) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: "Anônimo"
        val reviewsRef = db.getReference("users/$userId/reviews")

        val key = review.id.ifEmpty { reviewsRef.push().key!! }
        val reviewToSave = review.copy(
            id = key,
            userId = userId,
            userName = userName
        )

        reviewsRef.child(key).setValue(reviewToSave)
            .addOnSuccessListener {
                Log.d("ReviewRepository", "Review added successfully for user: $userId")
            }
            .addOnFailureListener { e ->
                Log.e("ReviewRepository", "Error adding review", e)
            }
    }

    fun getAllReviews(callback: (List<Review>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            callback(emptyList())
            return
        }

        val reviewsRef = db.getReference("users/$userId/reviews")

        reviewsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reviewsList = snapshot.children.mapNotNull { reviewSnapshot ->
                    try {
                        reviewSnapshot.getValue(Review::class.java)
                    } catch (e: Exception) {
                        Log.e("ReviewRepository", "Error parsing review: ${e.message}", e)
                        null
                    }
                }

                callback(reviewsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ReviewRepository", "Error loading reviews: ${error.message}")
                callback(emptyList())
            }
        })
    }

    fun getReview(id: String, callback: (Review?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            callback(null)
            return
        }

        val reviewsRef = db.getReference("users/$userId/reviews")

        reviewsRef.child(id).get()
            .addOnSuccessListener { snapshot ->
                try {
                    val review = snapshot.getValue(Review::class.java)
                    callback(review)
                } catch (e: Exception) {
                    Log.e("ReviewRepository", "Error parsing review: ${e.message}", e)
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("ReviewRepository", "Error loading review", e)
                callback(null)
            }
    }

    fun updateReview(review: Review) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val reviewsRef = db.getReference("users/$userId/reviews")

        val reviewToUpdate = review.copy(
            updatedAt = System.currentTimeMillis()
        )

        reviewsRef.child(review.id).setValue(reviewToUpdate)
            .addOnSuccessListener {
                Log.d("ReviewRepository", "Review updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("ReviewRepository", "Error updating review", e)
            }
    }

    fun deleteReview(id: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val reviewsRef = db.getReference("users/$userId/reviews")

        reviewsRef.child(id).removeValue()
            .addOnSuccessListener {
                Log.d("ReviewRepository", "Review deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("ReviewRepository", "Error deleting review", e)
            }
    }
}
