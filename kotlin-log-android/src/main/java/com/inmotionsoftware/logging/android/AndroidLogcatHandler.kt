/***************************************************************************
 * This source file is part of the kotlin-log-android open source project. *
 *                                                                         *
 * Copyright (c) 2020-present, InMotion Software and the project authors   *
 * Licensed under the MIT License                                          *
 *                                                                         *
 * See LICENSE.txt for license information                                 *
 ***************************************************************************/

package com.inmotionsoftware.logging.android

import android.util.Log
import com.inmotionsoftware.logging.*

/**
 * A logging backend for `KotlinLog` that sends log messages to `Logcat`.
 *
 * The logger's `label` is used as the TAG and `metadataContentType` is used to
 * control the output of `LoggerMetadata`. If `Private` is set, the `LoggerMetadata` content
 * output is replaced with `<private>` String value. This is useful for protecting
 * sensitive information for Release build.
 *
 * @param label: The label (TAG) for identifying the logger.
 * @param metadataContentType: Flag to indicate the content type of LoggerMetadata.
 */
class AndroidLogcatHandler(
    private val label: String,
    private val metadataContentType: MetadataContentType = MetadataContentType.Private
) : LogHandler {

    /** The LoggerMetadata content type. */
    enum class MetadataContentType {
        /** Replace metadata content type with `<private>` String*/
        Private,
        /** Log metadata content as String */
        Public
    }

    override var logLevel: LoggerLevel = LoggerLevel.Trace

    private var prettyMetadata: String? = null
    override var metadata: LoggerMetadata = LoggerMetadata()
    set(value) {
        field = value
        this.prettyMetadata = this.prettify(value)
    }

    override operator fun get(metadataKey: String): LoggerMetadataValue? {
        return this.metadata[metadataKey]
    }

    override operator fun set(metadataKey: String, value: LoggerMetadataValue) {
        this.metadata[metadataKey] = value
    }

    override fun log(
        level: LoggerLevel,
        message: LoggerMessage,
        metadata: LoggerMetadata?,
        source: String?,
        file: String?,
        function: String?,
        line: Int?
    ) {
        var formedMessage = ""
        if (level < LoggerLevel.Info && this.metadataContentType == MetadataContentType.Public && !file.isNullOrEmpty()) {
            val filename = file.split("/").lastOrNull()  ?: ""
            formedMessage += "[$filename ($line)] "
        }

        formedMessage += "$message"

        metadata?.let {
            formedMessage += when (this.metadataContentType) {
                MetadataContentType.Private ->
                    " -- <private>"
                MetadataContentType.Public ->
                    " -- ${this.prettyMetadata ?: ""}${this.prettify(it) ?: ""}"
            }
        }

        when (level) {
            LoggerLevel.Trace    -> Log.v(this.label, formedMessage)
            LoggerLevel.Debug    -> Log.d(this.label, formedMessage)
            LoggerLevel.Info     -> Log.i(this.label, formedMessage)
            LoggerLevel.Notice   -> Log.i(this.label, formedMessage)
            LoggerLevel.Warning  -> Log.w(this.label, formedMessage)
            LoggerLevel.Error    -> Log.e(this.label, formedMessage)
            LoggerLevel.Critical -> Log.wtf(this.label, formedMessage)
        }
    }

    private fun prettify(metadata: LoggerMetadata): String? {
        return if (metadata.isNotEmpty()) metadata.entries.joinToString(separator = " ") {"${it.key}=${it.value}"} else null
    }

}
