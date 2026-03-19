Got it 😄 — **one single clean markdown block**, no internal splits, no extra IDs — just copy-paste 👇

```md
# 📚 Smart Study Tracker (GUI Version)

![Java](https://img.shields.io/badge/Java-17-orange)
![SQLite](https://img.shields.io/badge/Database-SQLite-blue)
![JDBC](https://img.shields.io/badge/JDBC-SQLite-green)
![GUI](https://img.shields.io/badge/Interface-Swing_GUI-purple)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Repo Size](https://img.shields.io/github/repo-size/Aman-Tech990/Smart-Study-Tracker)
![Stars](https://img.shields.io/github/stars/Aman-Tech990/Smart-Study-Tracker?style=social)
![Forks](https://img.shields.io/github/forks/Aman-Tech990/Smart-Study-Tracker?style=social)
![Issues](https://img.shields.io/github/issues/Aman-Tech990/Smart-Study-Tracker)
![Last Commit](https://img.shields.io/github/last-commit/Aman-Tech990/Smart-Study-Tracker)
![Top Language](https://img.shields.io/github/languages/top/Aman-Tech990/Smart-Study-Tracker)

---

## 🧠 About the Project

**Smart Study Tracker** is a Java-based GUI application designed to help students track and manage their study sessions efficiently.

This project demonstrates real-world backend + GUI integration using:

- Java (Core)
- Swing (GUI Development)
- SQLite (Database)
- JDBC (Connectivity)
- DAO Design Pattern

The application allows users to add, view, and delete study sessions through an interactive graphical interface.

---

## ✨ Features

- ➕ Add Study Sessions  
- 📋 View Sessions in Table Format  
- ❌ Delete Selected Session  
- 💾 Persistent Storage using SQLite  
- 🖥️ Interactive GUI (Java Swing)  
- 🧠 Clean architecture using DAO pattern  

---

## 🖥️ GUI Preview

```

---

## | Smart Study Tracker               |

| Subject: [**********]                |
| Topic:   [**********]                |
| Duration:[**********]                |
| Date:    [**********]                |
| [Add Session]  [Delete Selected]     |
----------------------------------------

| ID | Subject | Topic | Duration | Date |
|----------------------------------------|
| 1  | DSA     | Graph | 2 hrs    | ...  |
------------------------------------------

```

---

## 🏗️ Project Structure

```

Smart-Study-Tracker
│
├── GUI.java
├── Main.java
├── Database.java
├── StudySession.java
├── StudySessionDAO.java
│
├── GUI.class
├── Main.class
├── Database.class
├── StudySession.class
├── StudySessionDAO.class
│
├── sqlite-jdbc.jar
├── studytracker.db

```

---

## ⚙️ Technologies Used

| Technology | Purpose |
|------------|--------|
| Java | Core programming language |
| Swing | GUI Interface |
| SQLite | Lightweight embedded database |
| JDBC | Database connectivity |
| DAO Pattern | Clean architecture |

---

## 🧠 Architecture

```

User (GUI)
↓
GUI.java
↓
StudySessionDAO
↓
Database.java
↓
SQLite Database

````

---

## 🗄️ Database Schema

Table: `sessions`

| Column | Type |
|--------|------|
| id | INTEGER PRIMARY KEY AUTOINCREMENT |
| subject | TEXT |
| topic | TEXT |
| duration | INTEGER |
| date | TEXT |

---

## ▶️ How to Run the Project

### 🔹 Compile

```bash
javac -cp ".;sqlite-jdbc.jar" *.java
````

### 🔹 Run GUI

```bash
java -cp ".;sqlite-jdbc.jar" GUI
```

---

## 🎯 Learning Outcomes

* JDBC database connectivity
* SQLite integration
* Java Swing GUI development
* DAO (Data Access Object) pattern
* SQL CRUD operations
* Clean and modular Java architecture

---

## 📌 Future Improvements

* ✏️ Update study sessions
* 🔍 Search sessions by subject
* 📊 Study analytics (charts)
* 📅 Weekly/monthly reports
* 🎨 Dark mode UI
* 🌐 Web version using Spring Boot + React

## ⭐ Support

If you found this project helpful, please consider giving it a ⭐ on GitHub!

```
```
