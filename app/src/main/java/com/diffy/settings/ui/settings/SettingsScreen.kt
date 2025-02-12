package com.diffy.settings.ui.settings

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.diffy.settings.ui.core.SettingsSaver
import com.diffy.settings.ui.core.multiTapTrigger
import com.diffy.settings.ui.settings.components.ItemPosition
import com.diffy.settings.ui.theme.conf.DarkTheme
import com.diffy.settings.ui.core.SettingsExt
import com.diffy.settings.BuildConfig

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SettingsScreen() {
    
    val appSettings by SettingsSaver.AppSettingsStateFlow.collectAsState()

    val isDark = appSettings.themePreference.darkMode == DarkTheme.On || (appSettings.themePreference.darkMode == DarkTheme.System && isSystemInDarkTheme())

    val settingsGroups = remember(appSettings) { SettingsGroup.getSettings(appSettings,isDark) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            settingsGroups.forEach { settingsGroup ->
                stickyHeader {
                    Text(
                        text = settingsGroup.header,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 8.dp)
                            .padding(bottom = 4.dp)
                    )
                }

                itemsIndexed(settingsGroup.settingsItems) { index,item ->
                    val itemPosition = when {
                        settingsGroup.settingsItems.size == 1 -> ItemPosition.Alone
                        index == 0 -> ItemPosition.Top
                        index == settingsGroup.settingsItems.lastIndex -> ItemPosition.Bottom
                        else -> ItemPosition.Middle
                    }
                    SettingItem(
                        item = item,
                        itemPosition = itemPosition
                    )
                }
            }

            item {
                val versionSetting = SettingsExt.InfoSetting(
                    icon = Icons.Default.Info,
                    title = "Version",
                    summary = null,
                    enabled = true,
                    value = BuildConfig.VERSION_NAME
                )
                SettingItem(
                    modifier = Modifier
                        .multiTapTrigger {
                            Toast.makeText(context, "Developer, Restart App!!", Toast.LENGTH_SHORT).show()
                            SettingsSaver.setDeveloper(!SettingsSaver.isADeveloper())
                        },
                    item = versionSetting,
                    itemPosition = ItemPosition.Alone
                )
            }
        }
    }
}