# KotlinLogAndroid
 A logging backend for `KotlinLog` that sends log messages to `Logcat`.

## Getting started

#### Adding the dependency

```
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}
dependencies {
    implementation "com.github.inmotionsoftware:kotlin-log:1.4.0"
    implementation "com.github.inmotionsoftware:kotlin-log-android:1.0.0"
}
```

#### Bootstrap KotlinLog

```kotlin
import android.app.Application
import com.inmotionsoftware.logging.android.AndroidLogcatHandler
import com.inmotionsoftware.logging.android.AndroidLogcatHandler.MetadataContentType
import com.inmotionsoftware.logging.LoggingSystem

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        LoggingSystem.bootstrap { label ->
            if (BuildConfig.DEBUG)
                AndroidLogcatHandler(label, MetadataContentType.Public)
            else
                AndroidLogcatHandler(label, MetadataContentType.Private)
        }
    }
}
```

#### Let's log

```kotlin
// 1) let's import the logging API package
import com.inmotionsoftware.logging.Logger

// 2) we need to create a logger, the label works similarly to a DispatchQueue label
val logger = Logger(label="MyActivity")

// 3) we're now ready to use it
logger.info("Hello World!")
```

#### Logcat Output

```
2020-10-14 10:55:55.731 18216-18216/com.example.myapp I/MyActivity: Hello World!
```

#### Log with location

```kotlin
// log with file, method, and line information
logger.debug("Should not occur here", location=__location())

// log with throwable
try {
    ...    
} catch (e: Throwable) {
    logger.error(e, location=__location(error=e))
}
```

## License

MIT