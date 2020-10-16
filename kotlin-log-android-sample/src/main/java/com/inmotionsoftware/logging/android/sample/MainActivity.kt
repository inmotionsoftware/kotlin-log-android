/***************************************************************************
 * This source file is part of the kotlin-log-android open source project. *
 *                                                                         *
 * Copyright (c) 2020-present, InMotion Software and the project authors   *
 * Licensed under the MIT License                                          *
 *                                                                         *
 * See LICENSE.txt for license information                                 *
 ***************************************************************************/

package com.inmotionsoftware.logging.android.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inmotionsoftware.logging.*

class MainActivity : AppCompatActivity() {
    private val logger = Logger("MainActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logger.debug("A test debug message", metadata = {
            mutableMapOf(
                "test" to "testValue".asLoggerMetadataValue(),
                "test2" to "testValue2".asLoggerMetadataValue()
            )
        }, location = __location())

        logger.error(Throwable("A test error"))

    }

}
