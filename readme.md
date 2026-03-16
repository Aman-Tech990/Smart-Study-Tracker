# Smart Study Tracker 📚

![Java](https://img.shields.io/badge/Java-17-orange)
![SQLite](https://img.shields.io/badge/Database-SQLite-blue)
![JDBC](https://img.shields.io/badge/JDBC-SQLite-green)
![CLI](https://img.shields.io/badge/Interface-CLI-lightgrey)
![License](https://img.shields.io/badge/License-MIT-yellow)

A **Java-based Command Line Study Tracker** that allows students to record, view, and manage their study sessions.  
The application uses an **SQLite database with JDBC** for persistent storage.

This project demonstrates **Java backend concepts, JDBC connectivity, the DAO design pattern, and database CRUD operations.**

---

## ✨ Features

- Add study sessions  
- View all study sessions  
- Delete a study session  
- SQLite database storage  
- JDBC connectivity  
- DAO-based project structure  
- Persistent local database

---

## 🏗️ Project Structure

```
Smart-Study-Tracker
│
├── Main.java
├── Database.java
├── StudySession.java
├── StudySessionDAO.java
│
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
| SQLite | Lightweight embedded database |
| JDBC | Database connectivity |
| CLI | Command Line Interface |

---

## 🧠 Architecture

The application follows the **DAO (Data Access Object) pattern**.

```
User (CLI)
   ↓
Main.java
   ↓
StudySessionDAO
   ↓
Database.java
   ↓
SQLite Database
```

This structure separates:

- Business logic  
- Database operations  
- Data model  

making the project cleaner and easier to maintain.

---

## 🗄️ Database Schema

Table: `sessions`

| Column | Type |
|------|------|
| id | INTEGER PRIMARY KEY AUTOINCREMENT |
| subject | TEXT |
| topic | TEXT |
| duration | INTEGER |
| date | TEXT |

---

## ▶️ How to Run the Project

### Compile the project

```bash
javac -cp ".;sqlite-jdbc.jar" *.java
```

### Run the application

```bash
java -cp ".;sqlite-jdbc.jar" Main
```

---

## 💻 Application Menu

```
SMART STUDY TRACKER

1. Add Session
2. View Sessions
3. Delete Session
4. Exit
```

---

## 📝 Example Output

```
SMART STUDY TRACKER
1. Add Session
2. View Sessions
3. Delete Session
4. Exit
Choose option: 1

Subject: DSA
Topic: Graph Traversal
Duration (hours): 2
Date: 2026-03-14

Session added successfully!
```

---

## 🎯 Learning Outcomes

This project helps practice:

- JDBC database connectivity
- SQLite integration
- Java CLI application development
- DAO design pattern
- SQL CRUD operations
- Structured Java project architecture

---

## 📌 Possible Improvements

- Update study sessions  
- Search sessions by subject  
- Study statistics  
- Weekly study reports  
- GUI using JavaFX or Swing  
- Web version using Spring Boot  

---


⭐ If you find this project useful, consider giving the repository a star.