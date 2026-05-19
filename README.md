# VirasatNamma Kotlin Android App

A native Android application built using Kotlin and Android Studio.

---

## Features

- Native Android app using Kotlin
- Automated build and install script (`run_app.py`)
- Gradle wrapper recovery support
- Android SDK auto-detection
- ADB integration for device installation and app launch

---

## Project Structure

```text
VirasatNamma_Kotlin/
│
├── app/
├── gradle/
├── gradlew
├── gradlew.bat
├── run_app.py
├── settings.gradle
├── build.gradle
└── README.md
```

---

## Requirements

Before running the project, install:

- Android Studio
- Android SDK
- Python 3.x
- Git

---

## Setup

### 1. Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/VirasatNamma_Kotlin.git
```

### 2. Open Project

Open the project in Android Studio.

---

## Running the App

### Option 1 — Using Python Automation Script

Run:

```bash
python run_app.py
```

The script will:

- Detect Android SDK
- Configure `local.properties`
- Restore Gradle wrapper files if missing
- Build the app
- Install APK on connected device/emulator
- Launch the application

---

### Option 2 — Using Android Studio

1. Open project in Android Studio
2. Start emulator or connect Android device
3. Click Run ▶

---

## Android Device Setup

Enable:

```text
Developer Options → USB Debugging
```

Then verify connection:

```bash
adb devices
```

---

## Build APK Manually

```bash
gradlew assembleDebug
```

Generated APK:

```text
app/build/outputs/apk/debug/app-debug.apk
```

---

## Technologies Used

- Kotlin
- Android SDK
- Gradle
- Python Automation
- ADB

---

## Author

Mohammed

---

## License

This project is for educational and development purposes.
