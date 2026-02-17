# SwagLabs Mobile App - Cucumber Appium Tests

Automated test framework for the SwagLabs Mobile App using Cucumber (BDD), Appium, and Java.

---

## 📋 Table of Contents

1. [Environment Setup](#environment-setup)
2. [Running Tests Locally](#running-tests-locally)
3. [Known Limitations](#known-limitations)
4. [Test Scenarios](#test-scenarios)
5. [Test Reports](#test-reports)
6. [Troubleshooting](#troubleshooting)

---

## 🔧 Environment Setup

Follow these steps to set up your testing environment. Even if you're not technical, these instructions will guide you through each step.

### Step 1: Install Java Development Kit (JDK)

The JDK is required to run Java programs and compile test code.

#### For Mac:

1. Open Terminal
2. Install using Homebrew (if you have it):
   ```bash
   brew install openjdk@11
   ```
3. Set JAVA_HOME in your terminal:
   ```bash
   echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 11)' >> ~/.zshrc
   source ~/.zshrc
   ```
4. Verify installation:
   ```bash
   java -version
   ```
   You should see output like: `openjdk version "11.0.x"`

#### For Windows:

1. Download JDK 11 from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [Adoptium](https://adoptium.net/)
2. Run the installer and follow the prompts
3. Set JAVA_HOME environment variable:
   - Search "Environment Variables" in Windows Start menu
   - Add new System Variable: `JAVA_HOME` = `C:\Program Files\Java\jdk-11.x.x`
4. Verify in Command Prompt:
   ```cmd
   java -version
   ```

---

### Step 2: Install Maven

Maven is a build tool that manages dependencies and runs tests.

#### For Mac:
```bash
brew install maven
mvn -version
```

#### For Windows:

1. Download Maven from [maven.apache.org](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\Maven`
3. Add to PATH environment variable: `C:\Program Files\Maven\bin`
4. Verify in Command Prompt:
   ```cmd
   mvn -version
   ```

---

### Step 3: Install Node.js and npm

Node.js is required to run Appium (the mobile automation tool).

1. Download from [nodejs.org](https://nodejs.org/) (LTS version recommended)
2. Run the installer
3. Verify installation:
   ```bash
   node --version
   npm --version
   ```

---

### Step 4: Install Appium

Appium is the tool that automates mobile app interactions.

1. Install Appium globally:
   ```bash
   npm install -g appium
   ```
2. Install the Android driver:
   ```bash
   appium driver install uiautomator2
   ```
3. Verify installation:
   ```bash
   appium --version
   appium driver list --installed
   ```
   You should see `uiautomator2` in the installed drivers list.

---

### Step 5: Install Android Studio and SDK

Android Studio provides the Android SDK needed to run Android emulators.

1. Download [Android Studio](https://developer.android.com/studio)
2. Run the installer and follow the setup wizard
3. During setup, ensure these are installed:
   - Android SDK
   - Android SDK Platform-Tools
   - Android Emulator
   - At least one Android system image (e.g., Android 11 or higher)

4. Verify Android SDK installation:
   ```bash
   adb --version
   ```
   If this command doesn't work, add Android SDK to your PATH:
   - Mac: Add to `~/.zshrc`:
     ```bash
     export ANDROID_HOME=$HOME/Library/Android/sdk
     export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
     ```
   - Windows: Add to System PATH: `C:\Users\<YourName>\AppData\Local\Android\Sdk\platform-tools`

---

### Step 6: Set Up Android Emulator or Device

#### Option A: Android Emulator (Recommended for Testing)

1. Open Android Studio
2. Click "Device Manager" (phone icon on the right side)
3. Click "+ Create Device"
4. Select a phone model (e.g., Pixel 6)
5. Download and select a system image (e.g., API 30 - Android 11)
6. Finish setup
7. Start the emulator from Device Manager

#### Option B: Physical Android Device

1. Enable Developer Options on your Android phone:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings → Developer Options → USB Debugging (ON)
3. Connect phone to computer via USB
4. Verify connection:
   ```bash
   adb devices
   ```
   You should see your device listed.

---

### Step 7: Install the SwagLabs Mobile App

The app must be installed on your emulator or device before running tests.

1. Make sure your emulator or device is running
2. Install the app:
   ```bash
   adb install ../android/app/build/outputs/apk/debug/app-debug.apk
   ```
3. Verify installation:
   ```bash
   adb shell pm list packages | grep swaglabs
   ```
   You should see: `package:com.swaglabsmobileapp`

---

### 📱 Device/Emulator Matrix

The tests have been verified on the following configurations:

| Platform | OS Version | Device Type | Status |
|----------|-----------|-------------|--------|
| Android | 11+ (API 30+) | Emulator | ✅ Supported |
| Android | 11+ (API 30+) | Physical Device | ✅ Supported |
| Android | 10 and below | Any | ⚠️ Not Tested |

**Recommended Configuration:**
- **OS**: Android 11 or higher (API 30+)
- **Device**: Pixel 4/5/6 emulator or equivalent physical device
- **RAM**: At least 4GB available for emulator

---

## 🚀 Running Tests Locally

Once your environment is set up, follow these simple steps to run tests.

### Quick Start (3 Steps)

#### Step 1: Start Appium Server

Open a **new terminal window** and run:

```bash
appium
```

**What to expect:**
- You'll see output like: `[Appium] Welcome to Appium v2.x.x`
- **Keep this terminal open** while tests are running

#### Step 2: Start Your Android Device

**For Emulator:**
1. Open Android Studio
2. Click "Device Manager"
3. Click the ▶️ play button next to your emulator

**For Physical Device:**
1. Connect via USB
2. Run `adb devices` to verify it's connected

#### Step 3: Run Your Tests

Open a **second terminal window** and run:

```bash
cd /Users/UGARRLU/AndroidStudioProjects/sample-app-mobile/cucumber-tests
mvn clean test
```

**What happens:**
- Maven compiles the code
- Tests start executing on your device/emulator
- Results appear in the terminal
- Reports are generated in `target/cucumber-reports/`

---

### Running Specific Tests

You can run specific test scenarios using tags:

#### Run Only Login Tests
```bash
mvn clean test -Dcucumber.filter.tags="@login"
```

#### Run Only Positive Tests (Happy Path)
```bash
mvn clean test -Dcucumber.filter.tags="@positive"
```

#### Run Only Negative Tests (Error Cases)
```bash
mvn clean test -Dcucumber.filter.tags="@negative"
```

#### Run Only Smoke Tests (Critical Tests)
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

#### Run Product Navigation Tests
```bash
mvn clean test -Dcucumber.filter.tags="@product-navigation"
```

#### Run Checkout Tests
```bash
mvn clean test -Dcucumber.filter.tags="@checkout"
```

#### Run Multiple Tags (AND condition)
```bash
mvn clean test -Dcucumber.filter.tags="@positive and @smoke"
```

#### Run Multiple Tags (OR condition)
```bash
mvn clean test -Dcucumber.filter.tags="@positive or @smoke"
```

---

### Viewing Test Reports

After tests complete, open the HTML report:

#### Mac:
```bash
open target/cucumber-reports/cucumber.html
```

#### Windows:
```cmd
start target\cucumber-reports\cucumber.html
```

#### Linux:
```bash
xdg-open target/cucumber-reports/cucumber.html
```

The report shows:
- ✅ Passed scenarios (green)
- ❌ Failed scenarios (red)
- ⏭️ Skipped scenarios (yellow)
- Screenshots for failed tests
- Step-by-step execution details

---

## ⚠️ Known Limitations

### 1. Test State Between Scenarios

**Issue:** App state may carry over between test scenarios.

**Current Behavior:**
- Tests use `noReset=false` which resets some app data
- NOT using `fullReset=true` (which would uninstall/reinstall)

**Impact:**
- Generally not an issue for most tests
- If state pollution occurs, tests may fail

**Workaround:**
- Each test scenario creates a new driver session
- Tests should be independent and not rely on previous test state

### 2. Timing/Synchronization Issues

**Issue:** Elements may not be immediately available when app transitions between screens.

**Symptoms:**
- "Element not found" errors
- Tests occasionally fail then pass on retry

**Current Mitigation:**
- Explicit waits (15 seconds) configured for most elements
- Additional `Thread.sleep()` calls after navigation actions
- Scrolling to ensure elements are in viewport

**If Tests Are Flaky:**
- Increase wait times in `config.properties`:
  ```properties
  implicit.wait=15
  explicit.wait=20
  ```
- Or increase specific waits in page objects

### 3. Error Message Detection

**Issue:** Error messages are captured from TextView elements within error containers.

**Known Behavior:**
- Error messages display correctly on screen
- Tests successfully verify error text content

**If Error Tests Fail:**
- Check that error messages appear on screen
- Review debug output: `DEBUG - Error message captured: '...'`
- Error messages must match exactly (case-sensitive)

### 4. View Toggle and Filter Buttons

**Issue:** Filter button and view toggle may not be visible if products list is scrolled.

**Mitigation:**
- Tests automatically scroll to top before clicking these buttons
- Retry logic implemented for filter button clicks

**If Click Fails:**
- The element might be obscured by another UI element
- Check screenshots in `target/screenshots/` for visual confirmation

### 5. Device/Emulator Performance

**Issue:** Slow emulators may cause test failures due to timeouts.

**Recommendation:**
- Use hardware acceleration for emulators
- Allocate sufficient RAM (4GB+) to emulator
- Physical devices generally perform better than emulators

### 6. Multiple Test Runs

**Issue:** Running tests repeatedly in quick succession may cause session conflicts.

**Workaround:**
- Wait 5-10 seconds between test runs
- Or restart Appium server between runs:
  ```bash
  # Stop Appium (Ctrl+C)
  # Start again
  appium
  ```

### 7. App Installation

**Issue:** Tests assume app is already installed on device/emulator.

**Current Configuration:**
- Using `app.package` and `app.activity` to launch installed app
- Not using `app.path` to install APK automatically

**Before Running Tests:**
- Ensure app is installed: `adb shell pm list packages | grep swaglabs`
- If not installed:
  ```bash
  adb install ../android/app/build/outputs/apk/debug/app-debug.apk
  ```

---

## 🔍 Flaky Test Handling

### What Are Flaky Tests?

Flaky tests are tests that sometimes pass and sometimes fail without any code changes. Common in mobile testing due to timing, network, or device performance issues.

### Identifying Flaky Tests

Signs of a flaky test:
- Passes locally but fails in CI/CD (or vice versa)
- Fails intermittently with "element not found" errors
- Fails due to timeout but element eventually appears

### Strategies to Handle Flaky Tests

#### 1. **Increase Wait Times**

Edit `config.properties`:
```properties
implicit.wait=15   # Try 20 for slower devices
explicit.wait=20   # Try 25 for slower devices
```

#### 2. **Add Retry Logic**

Run tests with retry:
```bash
# Run tests up to 3 times if they fail
mvn clean test -Dsurefire.rerunFailingTestsCount=2
```

#### 3. **Run Tests Multiple Times**

To verify stability, run the same test 5 times:
```bash
for i in {1..5}; do 
  echo "Run $i"
  mvn clean test -Dcucumber.filter.tags="@smoke"
done
```

#### 4. **Enable Step-by-Step Screenshots**

In `Hooks.java`, uncomment line 88 to capture screenshots after EVERY step:
```java
captureScreenshot(scenario, "step");
```

This helps debug where timing issues occur.

#### 5. **Use Physical Device Instead of Emulator**

Emulators can be slower and less reliable. Physical devices often have fewer timing issues.

#### 6. **Check Appium Server Logs**

Appium logs show detailed information about what's happening. Look for:
- Element lookup attempts
- Timeout messages
- Network issues

#### 7. **Isolate the Flaky Test**

Run only the problematic test:
```bash
# Run specific feature file
mvn clean test -Dcucumber.filter.tags="@specific-tag"
```

---

## 🧪 Test Scenarios

### Login Feature (`login.feature`)

#### ✅ Positive Tests:
- Successful login with valid credentials (includes logout verification)

#### ❌ Negative Tests (Error Handling):
- Login with empty username
- Login with empty password
- Login with both fields empty
- Login with locked out user
- Login with invalid username
- Login with invalid password
- Login with incorrect case in username
- Login with incorrect case in password
- Login with special characters in username

### Product Navigation Feature (`product-navigation.feature`)

- View product details and return to inventory
- Switch product view to list

### Checkout Feature (`checkout.feature`)

- Complete purchase flow scenarios

---

## 📊 Test Reports

After running tests, reports are automatically generated in multiple formats:

### HTML Report (Most Readable)
- **Location**: `target/cucumber-reports/cucumber.html`
- **View**: Open in browser
  ```bash
  open target/cucumber-reports/cucumber.html
  ```
- **Contents**: Scenarios, steps, pass/fail status, execution time

### JSON Report (For Tools)
- **Location**: `target/cucumber-reports/cucumber.json`
- **Use**: Can be imported into test management tools

### JUnit XML Report (For CI/CD)
- **Location**: `target/cucumber-reports/cucumber.xml`
- **Use**: Jenkins, GitLab CI, GitHub Actions integration

### Screenshots (On Failure)
- **Location**: `target/screenshots/`
- **Contains**: PNG screenshots captured when tests fail
- **Naming**: `ScenarioName_timestamp.png`

---

## 🧰 Troubleshooting

### Problem: "JAVA_HOME is not defined"

**Cause:** Java JDK is not installed or JAVA_HOME not set.

**Solution:**
```bash
# Mac
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
echo $JAVA_HOME

# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-11
echo %JAVA_HOME%
```

---

### Problem: "Appium connection error" or "Connection refused"

**Cause:** Appium server is not running.

**Solution:**
1. Open a terminal
2. Run: `appium`
3. Keep terminal open
4. Run tests in a different terminal

---

### Problem: "No devices found" or "Device offline"

**Cause:** Emulator/device is not running or not connected.

**Solution:**
```bash
# Check connected devices
adb devices

# If no devices listed:
# - Start emulator from Android Studio
# - OR reconnect USB cable for physical device
# - OR restart adb:
adb kill-server
adb start-server
adb devices
```

---

### Problem: "App not installed" or "Activity not found"

**Cause:** SwagLabs app is not installed on the device.

**Solution:**
```bash
# Install the app
adb install ../android/app/build/outputs/apk/debug/app-debug.apk

# Verify installation
adb shell pm list packages | grep swaglabs
```

---

### Problem: Tests run very slowly

**Cause:** Multiple factors can slow down tests.

**Solutions:**
1. **Use faster emulator:**
   - Allocate more RAM (4GB+)
   - Enable hardware acceleration
   - Use x86 system image (faster than ARM)

2. **Reduce wait times:**
   - Edit `config.properties`:
     ```properties
     implicit.wait=10
     explicit.wait=15
     ```

3. **Use physical device:**
   - Physical devices are usually faster than emulators

---

### Problem: "Element not found" errors

**Cause:** Element not visible or wait timeout too short.

**Solutions:**
1. **Increase wait timeout:**
   - Edit wait durations in `config.properties`

2. **Verify accessibility IDs:**
   - Use Appium Inspector to check element IDs
   - Ensure app has correct `testID` properties

3. **Check screenshots:**
   - Failed tests capture screenshots in `target/screenshots/`
   - Review to see app state when error occurred

---

### Problem: Error messages not captured in negative tests

**Cause:** Error text elements not found correctly.

**Solution:**
- Tests are configured to find TextView elements within error containers
- If still failing, check debug output:
  ```
  DEBUG - Error container is visible
  DEBUG - Found X TextView elements in error container
  DEBUG - TextView text: 'Username is required'
  ```

---

### Problem: "Login screen should be displayed" error in product tests

**Cause:** App didn't fully reset or is still loading.

**Solution:**
- Tests automatically detect if on inventory screen and logout if needed
- If persists, restart the app manually:
  ```bash
  adb shell am force-stop com.swaglabsmobileapp
  ```

---

## 📁 Project Structure

```
cucumber-tests/
├── pom.xml                           # Maven dependencies and build configuration
├── README.md                         # This file
├── src/test/
│   ├── java/com/swaglabsmobileapp/
│   │   ├── runners/
│   │   │   └── TestRunner.java       # Cucumber test runner with JUnit
│   │   ├── stepdefinitions/
│   │   │   ├── LoginSteps.java       # Login scenario step definitions
│   │   │   ├── ProductNavigationSteps.java  # Product scenario steps
│   │   │   ├── CheckoutSteps.java    # Checkout scenario steps
│   │   │   └── Hooks.java            # Setup/teardown and screenshots
│   │   ├── pages/
│   │   │   ├── LoginPage.java        # Login screen page object
│   │   │   ├── InventoryPage.java    # Product list page object
│   │   │   ├── ProductDetailsPage.java  # Product details page object
│   │   │   └── MenuPage.java         # Side menu page object
│   │   └── utils/
│   │       ├── DriverManager.java    # Appium driver management
│   │       └── ConfigReader.java     # Configuration reader
│   └── resources/
│       ├── features/
│       │   ├── login.feature         # Login test scenarios (Gherkin)
│       │   ├── product-navigation.feature  # Product navigation scenarios
│       │   └── checkout.feature      # Checkout scenarios
│       ├── config.properties         # Test configuration
│       └── cucumber.properties       # Cucumber settings
└── target/                           # Generated after running tests
    ├── cucumber-reports/             # Test reports
    └── screenshots/                  # Failure screenshots
```

---

## 🎯 Test Credentials

The app supports the following test accounts:

| Username | Password | Purpose |
|----------|----------|---------|
| `standard_user` | `secret_sauce` | ✅ Valid user for positive tests |
| `locked_out_user` | `secret_sauce` | 🔒 Locked user for negative tests |
| `problem_user` | `secret_sauce` | ⚠️ User with app issues |

---

## 💡 Tips for Non-Technical Users

### Before Every Test Run:

1. ✅ Appium server is running (check terminal)
2. ✅ Emulator or device is running (`adb devices` shows device)
3. ✅ App is installed (`adb shell pm list packages | grep swaglabs`)
4. ✅ You're in the correct directory: `cucumber-tests/`

### Understanding Test Output:

When tests run, you'll see:

```
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
```

- **Tests run**: Total number of scenarios executed
- **Failures**: Tests that failed (expected behavior didn't happen)
- **Errors**: Tests that crashed (code errors)
- **Skipped**: Tests that were skipped

**Green output = All tests passed ✅**  
**Red output = Some tests failed ❌**

### If Tests Fail:

1. **Don't panic!** Check the error message first
2. Look at screenshots in `target/screenshots/`
3. Review the HTML report for detailed step-by-step info
4. Check the Troubleshooting section above
5. Try running the specific failing test again

---

## 🛠️ Advanced Configuration

Edit `src/test/resources/config.properties` for custom settings:

```properties
# Appium Server (change if running on different port)
appium.server.url=http://127.0.0.1:4723

# Device Configuration
device.name=Android Emulator        # Change to your device name
platform.version=                    # Leave empty for any version

# App Launch (using installed app)
app.package=com.swaglabsmobileapp
app.activity=.MainActivity

# Reset Options
no.reset=false      # false = reset app data between tests
full.reset=false    # true = uninstall/reinstall (requires APK path)

# Wait Timeouts (in seconds)
implicit.wait=10    # Increase if tests are flaky
explicit.wait=15    # Increase for slower devices
```

---

## 📚 Additional Resources

- [Appium Documentation](http://appium.io/docs/en/latest/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [Android ADB Commands](https://developer.android.com/studio/command-line/adb)

---

## 📝 Summary Checklist

Before running tests, ensure:

- [ ] Java JDK 11+ installed (`java -version`)
- [ ] Maven installed (`mvn -version`)
- [ ] Node.js and npm installed (`node --version`)
- [ ] Appium installed (`appium --version`)
- [ ] Appium UiAutomator2 driver installed (`appium driver list --installed`)
- [ ] Android SDK installed (`adb --version`)
- [ ] Emulator or device running (`adb devices`)
- [ ] SwagLabs app installed (`adb shell pm list packages | grep swaglabs`)
- [ ] Appium server running in terminal (`appium`)
- [ ] In correct directory (`cd cucumber-tests`)

Then run: `mvn clean test`

---

## 🆘 Getting Help

If you encounter issues not covered in this README:

1. Check the Appium server terminal output for detailed error logs
2. Review screenshots in `target/screenshots/` for failed tests
3. Check the HTML report in `target/cucumber-reports/cucumber.html`
4. Verify all environment setup steps were completed
5. Try restarting Appium server and device/emulator

---

**Happy Testing! 🎉**
