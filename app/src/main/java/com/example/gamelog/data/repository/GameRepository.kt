package com.example.gamelog.data.repository

import android.util.Log
import com.example.gamelog.data.model.game.FirebaseGameDetail
import com.example.gamelog.data.model.game.GameDetail
import com.example.gamelog.data.model.game.GameStatus
import com.example.gamelog.data.model.game.toFirebase
import com.example.gamelog.data.model.game.toDomain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object GameRepository {

    private val db = FirebaseDatabase.getInstance()

    fun addGame(game: GameDetail, status: GameStatus) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gamesRef = db.getReference("users/$userId/games")

        val key = game.id?.toString() ?: gamesRef.push().key!!
        val gameWithStatus = game.copy(status = status.value)
        val firebaseGame = gameWithStatus.toFirebase()

        gamesRef.child(key).setValue(firebaseGame)
            .addOnSuccessListener {
                Log.d("GameRepository", "Game added successfully for user: $userId")
            }
            .addOnFailureListener { e ->
                Log.e("GameRepository", "Error adding game", e)
            }
    }

    fun getAllGames(callback: (List<GameDetail>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            callback(emptyList())
            return
        }

        val gamesRef = db.getReference("users/$userId/games")

        gamesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val gamesList = snapshot.children.mapNotNull { gameSnapshot ->
                    try {
                        gameSnapshot.getValue(FirebaseGameDetail::class.java)?.toDomain()
                    } catch (e: Exception) {
                        Log.e("GameRepository", "Error parsing game: ${e.message}", e)
                        null
                    }
                }

                callback(gamesList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("GameRepository", "Error loading games: ${error.message}")
                callback(emptyList())
            }
        })
    }

    fun getGame(id: Int, callback: (GameDetail?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            callback(null)
            return
        }

        val gamesRef = db.getReference("users/$userId/games")

        gamesRef.child(id.toString()).get()
            .addOnSuccessListener { snapshot ->
                try {
                    val firebaseGame = snapshot.getValue(FirebaseGameDetail::class.java)
                    callback(firebaseGame?.toDomain())
                } catch (e: Exception) {
                    Log.e("GameRepository", "Error parsing game: ${e.message}", e)
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("GameRepository", "Error loading game", e)
                callback(null)
            }
    }

    fun updateGame(game: GameDetail) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gamesRef = db.getReference("users/$userId/games")

        val key = game.id?.toString() ?: return
        val firebaseGame = game.toFirebase()

        gamesRef.child(key).setValue(firebaseGame)
            .addOnSuccessListener {
                Log.d("GameRepository", "Game updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("GameRepository", "Error updating game", e)
            }
    }

    fun deleteGame(id: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gamesRef = db.getReference("users/$userId/games")

        gamesRef.child(id.toString()).removeValue()
            .addOnSuccessListener {
                Log.d("GameRepository", "Game deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("GameRepository", "Error deleting game", e)
            }
    }
}
