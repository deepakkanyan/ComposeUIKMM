# **ComposeUI CMP Project**

Welcome to **ComposeUI KMM**, a **Kotlin Multiplatform (KMM)** project that seamlessly integrates with **Firebase** to provide a robust, cross-platform experience for **Android** and **iOS** using **Jetpack Compose Multiplatform**. This project ensures maximum code reusability while leveraging platform-specific capabilities where needed.

---
## **Screenshot**
<img src="https://github.com/user-attachments/assets/8ad679fd-3462-4c6d-9226-015ef115c6c6" width="900" height="565" />

 

---

## **Project Structure**

📂 **composeApp (Shared Code)**

- **commonMain** → Contains shared business logic across **Android** & **iOS**.
- **androidMain** → Platform-specific implementations for **Android**.
- **iosMain** → Platform-specific implementations for **iOS**.

---

## **🔥 Features**

✅ **Kotlin Multiplatform (KMM)** → Write once, run on **Android** & **iOS**.

✅ **Jetpack Compose Multiplatform** → Unified **UI** for both platforms.

✅ **Firebase Integration** → Authentication, Firestore, Analytics, and more.

✅ **MVVM Architecture** → Uses **StateFlow** & **ViewModel** for reactive UI updates.

✅ **Dynamic Theming** → Supports **light/dark mode** and custom themes.

✅ **SwiftUI Interop** → Easily integrate **KMM** with existing **SwiftUI** code.

---

## **🚀 Getting Started**

### **Clone the Repo**
```sh
git clone https://github.com/your-repo/composeui-kmm.git
cd composeui-kmm
```

### **Run on Android**
- Build and install using **Gradle**.

### **Run on iOS**
- Open `iosApp.xcworkspace` in **Xcode**.
- Select a **simulator** and run the project.

---

## **📌 Roadmap**

🔹 Improve **Firebase Cloud Messaging (FCM)** support.

🔹 Add more **Compose Multiplatform UI components**.

🔹 Add **Koin** for **Dependency Injection (DI)**.

---

## **💡 Contributing**

Contributions are welcome! Please check the **issues tab** and feel free to submit **PRs**.

---

## **📄 License**

This project is licensed under the **MIT License**.

---

Made with ❤️ by **Deepak Kanyan**. 🚀
