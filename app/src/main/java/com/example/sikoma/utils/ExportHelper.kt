package com.example.sikoma.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.sikoma.data.models.EventParticipant
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter
object ExportHelper {
    /**
     * Mengekspor data user dari daftar [participants] ke file CSV.
     *
     * @param context Context yang digunakan untuk menampilkan Toast.
     * @param participants List dari [EventParticipant] yang berisi data user.
     * @param directory Direktori tempat file CSV akan disimpan.
     * @param csvFileName Nama file CSV (default: "UserData.csv").
     */
    fun exportUserData(
        context: Context,
        participants: List<EventParticipant>,
        directory: File,
        csvFileName: String = "EventData.csv"
    ) {
        val csvFile = File(directory, csvFileName)
        try {
            csvFile.createNewFile()
            val csvWriter = CSVWriter(FileWriter(csvFile))

            val header = arrayOf("user_id", "email", "full_name", "profile_pic", "study_prog", "faculty", "nim")
            csvWriter.writeNext(header)

            participants.forEach { participant ->
                val user = participant.user
                val row = arrayOf(
                    user.userId?.toString() ?: "",
                    user.email ?: "",
                    user.fullName ?: "",
                    user.profilePic ?: "",
                    user.studyProg ?: "",
                    user.faculty ?: "",
                    user.nim ?: ""
                )
                csvWriter.writeNext(row)
            }

            csvWriter.close()

            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.applicationContext.packageName}.provider",
                csvFile
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "User Data CSV")
                putExtra(Intent.EXTRA_TEXT, "Berikut file CSV hasil ekspor data user.")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            Toast.makeText(
                context,
                "Data berhasil diekspor ke ${csvFile.absolutePath}",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Log.e("CsvExportHelper", e.message, e)
            Toast.makeText(
                context,
                "Ekspor gagal: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}