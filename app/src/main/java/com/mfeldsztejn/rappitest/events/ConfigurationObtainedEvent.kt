package com.mfeldsztejn.rappitest.events

import com.mfeldsztejn.rappitest.dtos.Configuration

data class ConfigurationObtainedEvent(val configuration: Configuration)