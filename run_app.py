import os
import subprocess
import shutil

PACKAGE_NAME = "com.example.virasatnamma"
MAIN_ACTIVITY = ".MainActivity"

def run_command(command, shell=True):
    print(f"\nExecuting: {command}\n")

    try:
        subprocess.check_call(command, shell=shell)
        return True
    except subprocess.CalledProcessError as e:
        print(f"Command failed: {e}")
        return False

def find_android_sdk():
    # Try environment variables
    sdk_path = os.environ.get("ANDROID_HOME") or os.environ.get("ANDROID_SDK_ROOT")

    if sdk_path and os.path.exists(sdk_path):
        return sdk_path

    # Try default Windows location
    local_app_data = os.environ.get("LOCALAPPDATA")

    if local_app_data:
        path = os.path.join(local_app_data, "Android", "Sdk")

        if os.path.exists(path):
            return path

    return None

def restore_gradle_files(rn_android_dir):
    # Restore gradlew files
    for file in ["gradlew", "gradlew.bat"]:
        src = os.path.join(rn_android_dir, file)

        if os.path.exists(src) and not os.path.exists(file):
            shutil.copy2(src, file)
            print(f"Restored {file}")

    # Restore wrapper files
    wrapper_files = [
        "gradle-wrapper.jar",
        "gradle-wrapper.properties"
    ]

    for wf in wrapper_files:
        src = os.path.join(rn_android_dir, "gradle", "wrapper", wf)
        dest = os.path.join("gradle", "wrapper", wf)

        if os.path.exists(src) and not os.path.exists(dest):
            os.makedirs(os.path.dirname(dest), exist_ok=True)
            shutil.copy2(src, dest)
            print(f"Restored {wf}")

def main():
    # Ensure script runs from its own directory
    os.chdir(os.path.dirname(os.path.abspath(__file__)))

    # React Native Android folder (source for missing gradle files)
    rn_android_dir = r"C:\Users\kishorab\Downloads\VirasatNamma_ReactNative\VirasatNamma\android"

    # Restore gradle files if needed
    if os.path.exists(rn_android_dir):
        restore_gradle_files(rn_android_dir)

    # Find Android SDK
    sdk_path = find_android_sdk()

    if not sdk_path:
        print("CRITICAL: Android SDK not found.")
        print("Please install Android Studio.")
        return

    print(f"Android SDK Found: {sdk_path}")

    # Use Android Studio JDK
    studio_jdk = r"C:\Program Files\Android\Android Studio\jbr"

    if os.path.exists(studio_jdk):
        os.environ["JAVA_HOME"] = studio_jdk
        print(f"Using Android Studio JDK: {studio_jdk}")

    # Create local.properties
    sdk_dir = sdk_path.replace("\\", "/")

    with open("local.properties", "w") as f:
        f.write(f"sdk.dir={sdk_dir}\n")

    print("Created local.properties")

    # Add adb to PATH
    platform_tools = os.path.join(sdk_path, "platform-tools")

    if os.path.exists(platform_tools):
        os.environ["PATH"] = platform_tools + os.pathsep + os.environ["PATH"]

    # Start adb
    run_command("adb start-server")

    # Check devices
    print("\nChecking connected devices...\n")

    result = subprocess.run(
        "adb devices",
        shell=True,
        capture_output=True,
        text=True
    )

    print(result.stdout)

    # Build app
    print("\nBuilding and installing Kotlin app...\n")

    if os.name == "nt":
        success = run_command("gradlew.bat clean installDebug")
    else:
        run_command("chmod +x gradlew")
        success = run_command("./gradlew clean installDebug")

    # Launch app
    if success:
        print("\nBuild successful!\nLaunching app...\n")

        launch_command = (
            f"adb shell am start -n "
            f"{PACKAGE_NAME}/{MAIN_ACTIVITY}"
        )

        run_command(launch_command)

        print("\nDone.")
    else:
        print("\nBuild failed.")
        print("Check Gradle errors above.")
        print("Also ensure emulator/device is running.")

if __name__ == "__main__":
    main()