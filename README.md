# 👁️‍🗨️ CourseWork — Mobile Assistant for the Visually Impaired

A cross-platform mobile application that empowers people with visual impairments through real-time object recognition, location tracking, and emergency assistance — powered by a modern backend and multimodal vision model.

---

## 🏗️ System Architecture

The system is based on a **client-server architecture**, where the mobile app acts as a client interacting with the server through RESTful HTTP APIs.

### 🔧 Main Components

#### 📱 Mobile Client (React Native)
- Built with **React Native** for cross-platform support (Android & iOS).
- Provides an accessible and intuitive UI.
- Communicates with the backend via secure REST APIs.
- Supports multilingual UI and voice guidance (Ukrainian, English).
- Accessible interaction: double-tap, voice TTS, and haptic feedback.

#### 🌐 Backend Server (Spring Boot)
- Built with **Spring Boot** for fast, scalable RESTful services.
- Handles:
  - Authentication & authorization
  - User account management
  - Emergency contact & number management
  - Language-specific resource delivery
- Integrates with PostgreSQL and Redis.

#### 🧠 Multimodal Object Recognition Module (Flask)
- Implemented using **Python + Flask**.
- Processes camera images and detects objects using a **Moondream vision-language model**.
- Runs as a **separate microservice**, independently deployable and scalable.

#### 🛢️ Database (PostgreSQL)
- Stores:
  - User profiles and emergency contacts
  - Emergency numbers by country
  - Localized textual resources

#### ⚡ Redis Cache
- Used to:
  - Cache frequently accessed data
  - Store temporary runtime values
  - Improve performance and responsiveness of the backend

---

## 📌 Features

### 📍 Location Detection
- Retrieves device GPS location.
- Voice output for current coordinates or address.

### 🧠 Object Analysis
- Takes a photo using the device camera.
- Sends it to the Flask module for AI-based object recognition.
- Responds with spoken object descriptions.

### 🚨 Emergency Support
- Shows emergency numbers based on user's country.
- Allows fast dialing.
- Sends alerts to trusted contacts (server logic implemented, client-side integration planned).

### 🧑‍🦯 Accessibility Features
- Double-tap gesture interactions
- Voice narration and TTS
- Haptic feedback on touch
- Large touch targets and screen reader compatibility

### 🌍 Multilingual Interface
- Currently supports Ukrainian 🇺🇦 and English 🇬🇧.
- Language is auto-detected from device settings.
- UI texts and voice responses adapt accordingly.

---

## 🛠️ Tech Stack

| Layer      | Technology                     |
|------------|---------------------------------|
| Mobile App | React Native                    |
| Backend    | Java 21, Spring Boot, Spring Security |
| AI Module  | Python, Flask, Moondream     |
| Database   | PostgreSQL                      |
| Cache      | Redis                           |

---

## ✅ Results
![image](https://github.com/user-attachments/assets/7b662de5-6000-41ec-8a28-2e048c17c310)
![image](https://github.com/user-attachments/assets/9145b642-fab6-45c9-9f8a-207583ac1ba1)
![image](https://github.com/user-attachments/assets/6e9f8696-83c3-4d13-8f85-714606965f96)





