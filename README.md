# 🌟 Inspire

**Inspire** is a beautifully crafted Android application that delivers random motivational quotes, complete with a Home Screen widget for daily inspiration. Built entirely using **Jetpack Compose**, the app showcases modern Android development practices with a focus on **clean architecture**, **Ktor networking**, and **Glance** for widgets.

---

## 📱 Features

- 🎯 Random motivational quote generator  
- 🏠 Home Screen widget for quick inspiration   
- 🔁 Refresh quotes with a single tap  
- ⚡ Smooth and modern UI with Jetpack Compose  

---

## 🛠️ Tech Stack

| Layer         | Technology Used              |
|--------------|------------------------------|
| UI           | **Jetpack Compose**, **Glance** |
| Navigation   | **Navigation 3.0**           |
| Networking   | **Ktor Client**              |
| Architecture | **Clean Architecture**       |
| Dependency Injection |**Koin** |
| Other        | **Kotlin Coroutines, StateFlow** |

---

## 🧱 Architecture

The app follows a **Clean Architecture** pattern:

- **Presentation Layer** – Jetpack Compose UI, ViewModels  
- **Domain Layer** – Use cases, business logic  
- **Data Layer** – Repository pattern, Ktor-based API integration  
- **Widget Layer** – Glance for Home Screen widgets  

---

## 🔧 Setup & Installation

 - Clone the repository:

    ```bash
    git clone git@github.com:Syedovaiss/Random-Quote.git
    ```

 - Open the project in **Android Studio**

 - Sync Gradle and build the project

 - Run the app on an emulator or physical device

---

## 🚀 Future Improvements

- Favorite quotes & quote history  
- Daily quote notifications  
- Support for multiple categories  
- Offline mode  

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!  
Feel free to fork the repository and submit a pull request.
