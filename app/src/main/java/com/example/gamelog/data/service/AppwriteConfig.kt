package com.example.gamelog.data.service

import android.content.Context
import com.example.gamelog.data.util.Constants
import io.appwrite.Client
import io.appwrite.services.Storage

object AppwriteConfig {

    private lateinit var client: Client
    lateinit var storage: Storage
        private set

    fun init(context: Context) {
        client = Client(context)
            .setEndpoint(Constants.APPWRITE_URL)
            .setProject(Constants.APPWRITE_PROJECT_ID)
        storage = Storage(client)
    }
}