package com.soai.assistant.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.appearanceDataStore by preferencesDataStore(name = "profile_appearance")

class ProfileAppearanceStore(private val context: Context) {
    private val primaryKey = longPreferencesKey("profile_primary_color")
    private val secondaryKey = longPreferencesKey("profile_secondary_color")

    val colors = context.appearanceDataStore.data.map { preferences ->
        ProfileColors(
            primary = preferences[primaryKey] ?: 0xFF6A5CFF,
            secondary = preferences[secondaryKey] ?: 0xFFA78BFA
        )
    }

    suspend fun savePrimary(color: Long) {
        context.appearanceDataStore.edit { it[primaryKey] = color }
    }

    suspend fun saveSecondary(color: Long) {
        context.appearanceDataStore.edit { it[secondaryKey] = color }
    }

    suspend fun save(primary: Long, secondary: Long) {
        context.appearanceDataStore.edit {
            it[primaryKey] = primary
            it[secondaryKey] = secondary
        }
    }
}

data class ProfileColors(
    val primary: Long = 0xFF6A5CFF,
    val secondary: Long = 0xFFA78BFA
)
