package com.mfeldsztejn.rappitest.events

import android.content.Intent
import android.view.View

data class StartIntentEvent(val intent: Intent, val sharedView: List<View>)