package com.example.gamelog.data.repository

import android.util.Log
import com.example.gamelog.data.model.gamelist.GameList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object GameListRepository {

    private val db = FirebaseDatabase.getInstance()

    fun addGameList(gameList: GameList) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameListsRef = db.getReference("users/$userId/gamelists")

        val key = gameList.id.ifEmpty { gameListsRef.push().key!! }
        val gameListToSave = gameList.copy(
            id = key,
            userId = userId,
            createdAt = System.currentTimeMillis()
        )

        gameListsRef.child(key).setValue(gameListToSave)
            .addOnSuccessListener {
                Log.d("GameListRepository", "GameList added successfully for user: $userId")
            }
            .addOnFailureListener { e ->
                Log.e("GameListRepository", "Error adding gameList", e)
            }
    }

    fun getAllGameLists(callback: (List<GameList>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            callback(emptyList())
            return
        }

        val gameListsRef = db.getReference("users/$userId/gamelists")

        gameListsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val gameListsList = snapshot.children.mapNotNull { gameListSnapshot ->
                    try {
                        gameListSnapshot.getValue(GameList::class.java)
                    } catch (e: Exception) {
                        Log.e("GameListRepository", "Error parsing gameList: ${e.message}", e)
                        null
                    }
                }

                callback(gameListsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("GameListRepository", "Error loading gameLists: ${error.message}")
                callback(emptyList())
            }
        })
    }

    fun getGameList(id: String, callback: (GameList?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            callback(null)
            return
        }

        val gameListsRef = db.getReference("users/$userId/gamelists")

        gameListsRef.child(id).get()
            .addOnSuccessListener { snapshot ->
                try {
                    val gameList = snapshot.getValue(GameList::class.java)
                    callback(gameList)
                } catch (e: Exception) {
                    Log.e("GameListRepository", "Error parsing gameList: ${e.message}", e)
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("GameListRepository", "Error loading gameList", e)
                callback(null)
            }
    }

    fun updateGameList(gameList: GameList) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameListsRef = db.getReference("users/$userId/gamelists")

        val gameListToUpdate = gameList.copy(
            updatedAt = System.currentTimeMillis()
        )

        gameListsRef.child(gameList.id).setValue(gameListToUpdate)
            .addOnSuccessListener {
                Log.d("GameListRepository", "GameList updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("GameListRepository", "Error updating gameList", e)
            }
    }

    fun deleteGameList(id: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameListsRef = db.getReference("users/$userId/gamelists")

        gameListsRef.child(id).removeValue()
            .addOnSuccessListener {
                Log.d("GameListRepository", "GameList deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("GameListRepository", "Error deleting gameList", e)
            }
    }
}