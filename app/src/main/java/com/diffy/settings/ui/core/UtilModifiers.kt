package com.diffy.settings.ui.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.multiTapTrigger(
    tapCount: Int = 5,
    timeoutMillis: Long = 1000L,
    onTrigger: () -> Unit,
): Modifier = composed {
    val clickCounter = remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    this.then(
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            clickCounter.intValue++

            coroutineScope.launch {
                delay(timeoutMillis)
                clickCounter.intValue = 0 // Reset after timeout
            }

            if (clickCounter.intValue >= tapCount) {
                onTrigger()
                clickCounter.intValue = 0 // Reset after triggering
            }
        }
    )
}