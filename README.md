# Quad-Bytes

**`PROBLEM STATEMENT : Nutrition and Performance Planner`**

*`DESCRIPTION : Design an app that combines personalized workout routines with nutrition planning to optimize athletic performance based on individual health data.`*
# 🏆 FIT-Quest - Innovation in Nutrition

Welcome to **FIT-Quest**, an innovative hackathon project that gamifies fitness and nutrition tracking!  
The project combines **Java**, **Swing UI**, **AI chatbots**, and a **website** for a full-stack experience.

---

## 🚀 Project Overview

FIT-Quest is a fitness and nutrition companion where users can:
- 🏋️ **Get personalized workout tips** using an AI chatbot.
- 🥗 **Fetch nutritional information** of any food item instantly.
- 🛍️ **Buy gym gear** from a virtual store using earned coins.
- 📈 **Track XP, Level, and Coins** as they progress in their health journey.

**Tech-Stacks Used:**
- Java + Swing (Desktop Application)
- Cohere AI (Workout ChatFit API)
- Nutritionix API (Nutrition Info Fetching)
- Website & UI/UX-Design (Frontend UI, modern styling)
- Custom Graphic Design (In-application articons)

---

## 🔥 Project Workflow & Breakdown

| Section                     | Done by              | Details                                                                                  |
|------------------------------|----------------------|------------------------------------------------------------------------------------------|
| 🖥️ Java Application (FIT-Quest App)  | Ritankar Bose & Ayush Chowdhury           | Developed the complete Java Swing UI app, API integration, logic for gamified experience |
| 🤖 AI Chatbots               | Ritankar Bose            | Integrated Cohere API for personalized workout advice and chatbot history management     |
| 🌐 Website, UI/UX-Design                   | Rajdeep Das           | Built the frontend website to promote FIT-Quest (Modern, Minimal)                  |
| 🎨 Custom Graphic Design                 | Rani Bhattacharjee            | Designed the articons (Icons, in-application articons)                           |

---

## 🛠️ Java Application - Key Features

- **Login / Register Screen:** Secure login with password validation.
- **Home Dashboard:** Choose between Workout, Nutrition, Store, or Progress sections.
- **Workout Section:** Chat with an AI workout trainer powered by Cohere API.
- **Nutrition Section:** Enter any food item and get calorie, protein, and fat breakdown instantly.
- **Store Section:** Spend your earned coins to buy gym items (mat, dumbbells, bottle).
- **Gamification:** Earn XP after each interaction, Level Up automatically and get reward coins.

---

## 🧩 Workflow in the Java Application

```mermaid
flowchart TD
    Start([Start App])
    --> LoginScreen[Show Login/Register Screen]
    LoginScreen --> |Success| HomeScreen[Main Dashboard]
    HomeScreen --> Workout[Workout Section - AI Chatbot]
    HomeScreen --> Nutrition[Nutrition Section - Fetch Food Data]
    HomeScreen --> Store[Store Section - Buy Items]
    HomeScreen --> Progress[Progress Section - Check XP gained per day]
    Workout --> EarnXP[Earn XP + Level Up]
    Nutrition --> EarnXP
    Store --> UpdateCoins[Spend Coins]
```

---

## ⚙️ Setup Instructions

### 1. **Clone the Repository**
   ```bash
   git clone [https://github.com/yourajdeep/Quad-Bytes/fit-quest.git]
   ```

#
### 2. **Open the Project**
 - Open the project folder using your favorite Java IDE:
  - **VS Code** (Recommended)
  - **Eclipse** (Recommended)
  - **IntelliJ IDEA** 
  - **NetBeans**
    
   Make sure the JDK (Java Development Kit) is properly configured in your IDE.


#
### 3. **Install Required Libraries**

The application depends on:
- **org.json** for parsing JSON data.
- **Java Swing** (already included with the JDK).

If `org.json` is not available, you can manually add it:
- Download it from [Maven Repository](https://mvnrepository.com/artifact/org.json/json).
- Add the `json.jar` to your project libraries.

#
### 4. **API Access Requirements**

Ensure that:
- You have an **active internet connection** to make API requests.
- The project uses the following APIs:
  - **Cohere AI API**: For workout trainer chatbot responses.
  - **Nutritionix API**: To fetch nutrition facts for food items.

> 🚨 **Note:**  
> API keys are **currently hardcoded** for demonstration purposes.  
> For production, you should store API keys in **environment variables** or **a separate config file**.

#
### 5. **Run the Application**

- Locate and open the `main.java` file.
- Run the `main` method.
- The FIT-Quest application will launch, and you’ll see the login/registration screen.

Congratulations! 🎉 You're ready to explore FIT-Quest!

#
### If you don't want to follow these steps:

- You can simply download the sotware `.exe` file from our website.

---

## 🌟 APIs Used

FIT-Quest uses the following third-party APIs to enhance functionality:

### 🧠 Cohere AI API
- Used to power the **AI workout chatbot**.
- Provides smart responses to user queries about fitness routines, exercises, and tips.
- API Endpoint: `https://api.cohere.ai/v1/chat`

### 🍎 Nutritionix API
- Used to fetch **real-time nutritional information**.
- Accepts natural language input like "2 eggs and toast" and returns calories, protein, fat, etc.
- API Endpoint: `https://trackapi.nutritionix.com/v2/natural/nutrients`

> 🔐 **Security Note**:  
> API keys are currently embedded for hackathon/demo purposes.  
> It's recommended to move them to environment variables or a secure config file in production.

---

## 📢 Future Improvements

Here are some ideas to enhance FIT-Quest in future versions:

- 📊 **Progress Tracking Dashboard**  
  Add detailed stats like calories burned, workout duration, and daily streaks.

- 📈 **Leaderboard System**  
  Introduce a leaderboard where users can compare XP, coins, and levels with friends.

- 🔒 **Secure Credential Storage**  
  Encrypt user passwords and move API keys to a secure environment.

- 📱 **Mobile Application**  
  Develop a mobile app version for Android and iOS for on-the-go access.

- 🎮 **Mini Fitness Games**  
  Add interactive mini-games that reward users with bonus XP and coins for completing fitness challenges.

- 🧠 **Smarter AI Workouts**  
  Train custom AI models to generate personalized fitness plans based on user preferences.

- 🎨 **More Store Items and Avatars**  
  Expand the in-game store with customizable avatars, gym equipment, and power-ups.

#
_Stay tuned for more exciting updates! 🚀_

---

# 🏁 Thank You for Exploring FIT-Quest!

_“Making fitness fun, one quest at a time!”_

---

We hope you enjoyed exploring our project.  
Feel free to contribute, suggest new features, or reach out if you have ideas to make FIT-Quest even better! 🚀

Stay fit, stay awesome! ✨




 


