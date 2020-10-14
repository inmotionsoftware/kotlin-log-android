/***************************************************************************
 * This source file is part of the kotlin-log-android open source project. *
 *                                                                         *
 * Copyright (c) 2020-present, InMotion Software and the project authors   *
 * Licensed under the MIT License                                          *
 *                                                                         *
 * See LICENSE.txt for license information                                 *
 ***************************************************************************/

package com.inmotionsoftware.logging.android.sample

import android.app.Application
import com.inmotionsoftware.logging.android.AndroidLogcatHandler
import com.inmotionsoftware.logging.android.AndroidLogcatHandler.MetadataContentType
import com.inmotionsoftware.logging.LoggingSystem

class SampleApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Configure LoggingSystem
        LoggingSystem.bootstrap { label ->
            if (BuildConfig.DEBUG)
                AndroidLogcatHandler(label, MetadataContentType.Public)
            else
                AndroidLogcatHandler(label, MetadataContentType.Private)
        }
    }

}
