package com.digiapt.digiapttemplate.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.digiapt.digiapttemplate.database.AppDatabase
import com.digiapt.digiapttemplate.database.entities.Quote
import com.digiapt.digiapttemplate.network.Api
import com.explore.utils.network.NetworkSafeApiRequest
import com.digiapt.digiapttemplate.preferences.PreferenceProvider
import com.digiapt.digiapttemplate.utils.Coroutine
import java.lang.Exception
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

class ContentsRepository(
    private val api: Api,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : NetworkSafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest { api.getQuotes() }
                Log.d("exlog",response.toString())
                quotes.postValue(response.quotes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        var listSize = quotes.size
        Coroutine.io {
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}