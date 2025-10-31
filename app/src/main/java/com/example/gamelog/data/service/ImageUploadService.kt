package com.example.gamelog.data.service

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.gamelog.data.util.Constants
import io.appwrite.ID
import io.appwrite.models.InputFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageUploadService {

    suspend fun uploadProfileImage(
        userId: String,
        imageUri: Uri,
        context: Context
    ): String? {

        return withContext(Dispatchers.IO) {

            try {
                val inputStream = context.contentResolver.openInputStream(imageUri)
                    ?: throw Exception("Não foi possível abrir o arquivo")

                val bytes = inputStream.readBytes()
                inputStream.close()

                val mimeType = context.contentResolver.getType(imageUri)
                    ?: getMimeTypeFromExtension(imageUri.toString())
                    ?: "image/jpeg"

                Log.d("ImageUpload", "MIME Type: $mimeType")

                val extension = when (mimeType) {
                    "image/png" -> "png"
                    "image/jpg", "image/jpeg" -> "jpg"
                    "image/webp" -> "webp"
                    else -> "jpg"
                }

                val fileName = "profile_${userId}.${extension}"

                val file = InputFile.fromBytes(
                    bytes = bytes,
                    filename = fileName,
                    mimeType = mimeType
                )

                val response = AppwriteConfig.storage.createFile(
                    bucketId = Constants.APPWRITE_BUCKET_ID,
                    fileId = ID.unique(),
                    file = file
                )

                getImageUrl(response.id)
            } catch (e: Exception) {
                Log.e("ImageUpload", "Image upload error: ${e.message}", e)
                throw e
            }
        }
    }

    private fun getMimeTypeFromExtension(url: String): String? {
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return if (extension != null) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        } else {
            null
        }
    }

    private fun getImageUrl(fileId: String): String {
        return "${Constants.APPWRITE_URL}/storage/buckets/${Constants.APPWRITE_BUCKET_ID}/files/$fileId/view?project=${Constants.APPWRITE_PROJECT_ID}"
    }
}
