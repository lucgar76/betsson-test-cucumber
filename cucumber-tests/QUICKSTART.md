# Quick Start Guide - Cucumber Appium Tests

## Step-by-Step Setup and Execution

### Step 1: Install Appium (if not already done)
```bash
npm install -g appium
appium driver install uiautomator2
```

### Step 2: Verify Appium Installation
```bash
appium --version
appium driver list --installed
```

### Step 3: Build the Android App
```bash
cd ../android
./gradlew assembleDebug
cd ../cucumber-tests
```

### Step 4: Verify APK Location
The APK should be at:
```
../android/app/build/outputs/apk/debug/app-debug.apk
```

### Step 5: Start Android Emulator
```bash
# List available emulators
emulator -list-avds

# Start emulator (replace <emulator_name> with your AVD name)
emulator -avd <emulator_name>

# Verify device is connected
adb devices
```

### Step 6: Start Appium Server
Open a new terminal and run:
```bash
appium
```
Keep this terminal running. You should see:
```
[Appium] Welcome to Appium v2.x.x
[Appium] Appium REST http interface listener started on http://0.0.0.0:4723
```

### Step 7: Run Tests
Open a new terminal, navigate to cucumber-tests directory and run:

```bash
cd cucumber-tests

# Run all tests
mvn clean test

# OR run smoke tests only (recommended for first run)
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Step 8: View Test Reports
After execution, open the HTML report:
```bash
open target/cucumber-reports/cucumber.html
```

## Quick Test Commands

| Command | Description |
|---------|-------------|
| `mvn clean test` | Run all login tests |
| `mvn clean test -Dcucumber.filter.tags="@smoke"` | Run smoke tests only |
| `mvn clean test -Dcucumber.filter.tags="@positive"` | Run positive scenarios |
| `mvn clean test -Dcucumber.filter.tags="@negative"` | Run negative scenarios |
| `mvn clean test -Dcucumber.filter.tags="@login"` | Run all login feature tests |

## What Gets Tested?

### Positive Scenarios (✅)
- Successful login with valid credentials (`standard_user` / `secret_sauce`)
- Login session persistence

### Negative Scenarios (❌)
- Empty username/password
- Invalid credentials
- Locked out user (`locked_out_user`)
- Case sensitivity
- Special characters
- Whitespace handling

## Expected Test Results

**Total Scenarios**: 15
- **Positive**: 2 scenarios
- **Negative**: 13 scenarios

All tests should **PASS** if:
- Appium server is running
- Android emulator/device is connected
- App is built and APK exists
- Configuration is correct

## Troubleshooting Quick Fixes

### ❌ Appium not running
```bash
# Start Appium in terminal
appium
```

### ❌ No devices found
```bash
# Check devices
adb devices

# Restart adb if needed
adb kill-server
adb start-server
```

### ❌ APK not found
```bash
# Build the app
cd ../android
./gradlew assembleDebug
cd ../cucumber-tests
```

### ❌ Tests fail with timeout
Edit `src/test/resources/config.properties`:
```properties
implicit.wait=15
explicit.wait=20
```

### ❌ Maven dependency issues
```bash
mvn clean install -U
```

## Test Data

Use these credentials (already configured in tests):
- **Valid**: `standard_user` / `secret_sauce`
- **Locked**: `locked_out_user` / `secret_sauce`

## Need Help?

See the full [README.md](README.md) for detailed documentation.

## Project Structure Overview

```
cucumber-tests/
├── src/test/
│   ├── java/                     # Java test code
│   │   ├── runners/              # Test runner
│   │   ├── stepdefinitions/      # Gherkin step implementations
│   │   ├── pages/                # Page Object Model
│   │   └── utils/                # Utilities (driver, config)
│   └── resources/
│       ├── features/             # Gherkin feature files
│       │   └── login.feature     # Login test scenarios
│       ├── config.properties     # Test configuration
│       └── cucumber.properties   # Cucumber settings
├── target/
│   ├── cucumber-reports/         # Test reports (HTML, JSON, XML)
│   └── screenshots/              # Failure screenshots
├── pom.xml                       # Maven dependencies
└── README.md                     # Full documentation
```

## Success! 🎉

If everything is set up correctly, you should see output like:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.swaglabsmobileapp.runners.TestRunner

Scenario: Successful login with valid credentials ✓ PASSED
Scenario: Login with empty username ✓ PASSED
...

[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```
