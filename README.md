# Power Optimization Enabler

A lightweight Android application that enables proper power optimization for apps on MIUI/HyperOS devices.

## Background

MIUI/HyperOS has a unique behavior in its power management system: when users set an app to "No restrictions" in the battery settings, the system also sets the corresponding AOSP power management policy to unrestricted mode. This is unnecessary for apps that use FCM (Firebase Cloud Messaging) for push notifications.

### The Problem

- MIUI/HyperOS automatically disables AOSP power optimization when users grant "No restrictions" to apps
- This leads to increased battery consumption
- Apps with FCM can work perfectly fine with standard AOSP optimization enabled

### The Solution

This app uses Shizuku to restore AOSP power optimization settings for specified apps, allowing them to:
- Continue receiving FCM push notifications reliably
- Benefit from Android's built-in battery optimization
- Reduce unnecessary battery drain

## How It Works

The application performs two operations via Shizuku:

1. **Removes the app from DeviceIdle whitelist** - Allows the system to apply Doze mode restrictions
2. **Sets App Standby Bucket to "Working Set" (20)** - Maintains a balanced power profile for the app

## Current Configuration

**Hardcoded Package**: `org.telegram.messenger` (Official Telegram client)

This package name is currently hardcoded in the application. If you need to target a different application, you can modify the `PKG` constant in `PowerOptimizationManager.kt` and compile the app yourself.

```kotlin
private const val PKG = "org.telegram.messenger"
```

## Requirements

- Android device with MIUI/HyperOS
- [Shizuku](https://shizuku.rikka.app/) installed and running
- Root access or Wireless debugging enabled for Shizuku

## Usage

### Initial Setup

1. Install and start Shizuku on your device
2. Install Power Optimization Enabler
3. Open Shizuku app and grant Shizuku permission
4. Done! The app will automatically apply the optimization settings

### Important Notes

- **Only Shizuku permission is required** - You do NOT need to grant background execution, autostart, or any other system permissions to this app
- **Automatic Trigger** - The optimization will be automatically applied every time Shizuku service is activated (either on boot or manually started)
- **No Manual Intervention Needed** - After the initial setup, the app works silently in the background whenever Shizuku is running

## Permissions

This app requires Shizuku permission to:
- Access DeviceIdleController service
- Access UsageStatsManager service

No other permissions are needed.

## Disclaimer

This is a personal utility application. Use at your own risk. The developer is not responsible for any issues that may arise from using this application.

## License

This project is licensed under the GNU General Public License v3.0 (GPLv3).

See [LICENSE](LICENSE) for more details.

## Acknowledgments

Inspired by [Ims](https://github.com/vvb2060/Ims)