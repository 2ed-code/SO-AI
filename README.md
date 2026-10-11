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


## Firebase setup for real accounts and chat

The app includes Firebase Authentication and Cloud Firestore repository code. A Firebase project is not bundled with this repository, so the app intentionally shows a setup screen until project configuration is added.

1. Create a Firebase project in the Firebase Console.
2. Register an Android app with package name `com.soai.assistant`.
3. Enable **Authentication → Email/Password**.
4. Create a **Cloud Firestore** database.
5. Download `google-services.json` and place it at `app/google-services.json`. Do not commit project credentials to a public repository.
6. Publish the rules in `firestore.rules` to the Firestore database. Review and test the rules before production.
7. Push the branch again; the Google Services Gradle plugin is applied only when the config file exists.

### Implemented Firebase code

- Email/password account creation and sign-in.
- Firestore conversation and message repositories with real-time snapshot listeners.
- A conversation screen that reads and sends messages.
- DataStore persistence helpers for primary and secondary profile colors.

### Current integration status

The authentication screen is wired into startup when Firebase configuration is present. The existing main conversation list is still a demo list and is not yet connected to the Firestore conversation repository; direct-conversation discovery/contact search and account profile records remain follow-up integration work. Never deploy Firestore with permissive test rules.
