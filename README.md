# 207200

SO Chat is an Android chat-app UI prototype built with Kotlin and Jetpack Compose. The project is being transitioned away from the former AI-assistant concept.

## Current prototype

- Dark interface with purple accent styling
- Sample conversation list and search
- Sample Friends and Groups views
- Profile placeholder
- Online, Offline, and Do Not Disturb status selector
- Conversation screen layout
- Bottom navigation for Messages, Calls, Notifications, and Profile

## Important limitations

This branch currently contains a UI prototype only. Messages, authentication, friend requests, calls, push notifications, profile editing, and cross-device synchronization are not connected to a backend. Sample conversations are local demo data, not real users.

## Build

Open the project in a compatible Android Studio installation and run the Gradle Android debug build. The GitHub Actions workflow can produce a debug APK artifact after a successful build.

## Planned next steps

1. Verify Gradle and Android Gradle Plugin compatibility and build the app.
2. Add authentication and a messaging backend.
3. Implement persistent conversations, friend relationships, and groups.
4. Add push notifications and profile customization.
5. Add voice/video calling only after the core messaging flow is stable.

Do not commit API secrets, signing keys, or private credentials to the repository.
