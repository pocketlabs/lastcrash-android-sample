# LastCrash Android Sample Application

## SDK Setup Instructions

### Add Dependencies:

- In your project build.gradle file, add the following under **allprojects -> repositories:**

```bash
allprojects {
	...
	repositories { ... maven { url "https://mvn.lastcrash.io/releases" } }
}
```

- Then, add the following to your app build.gradle file, then sync your project:

```bash
implementation 'io.lastcrash:lastcrash-android:1.1.13'
implementation("androidx.lifecycle:lifecycle-runtime:2.6.2")
```

### Initialize SDK:

- In your MainActivity file, add `LastCrashListener`, initialize the SDK, and configure the `lastCrashDidCrash` method.
- Replace `LASTCRASH_API_KEY` with your LastCrash API key.

### Kotlin

```kotlin
class MainActivity : LastCrashListener {
  ...
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ...
    LastCrash.setListener(this);
    LastCrash.configure("LASTCRASH_API_KEY", this, true);
  }
  ...
  override fun lastCrashDidCrash() {
    // logic here to handle crash
    LastCrash.send();
  }
}
```

### Java

```java
public class MainActivity implements LastCrashListener {
...
@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(null);
    ...
    LastCrash.setListener(this);
    LastCrash.configure("LASTCRASH_API_KEY", this, true);
  }
  ...
  @Override
  public void lastCrashDidCrash() {
    // logic here to handle crash
    LastCrash.send();
  }
}
```

## Testing

Run app in `Release` mode with debugging turned off. For best results, run on a physical device, rather than an emulator.

Tap `Test Crash` to trigger a crash.  Then re-run the app and watch the output log for the crash being uploaded.  Go to your LastCrash account to view the crash recording.