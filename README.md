# VenueVista – EventPro Ticketing & Venue Management System

VenueVista is a full-stack web application for managing events, venues, tickets, employees, and event bookings through a centralized platform.

The system provides a user-friendly Angular frontend connected to a Java Spring Boot REST backend and a MySQL database.

---

## 📌 Project Overview

Managing events and ticket bookings manually can lead to problems such as:

- Difficulty managing multiple events and venues
- Incorrect ticket availability
- Duplicate seat bookings
- Manual booking errors
- Difficulty tracking bookings and employees
- Lack of centralized event and venue information

VenueVista addresses these problems by providing a single platform for event, venue, ticket, and booking management.

---

## ✨ Key Features

### 🏠 Home
- Provides an overview of the VenueVista system
- Navigation to major modules

### 🎫 Events
- View available events
- View event details
- Display event date, description, and associated venue

### 🏢 Venues
- View registered venues
- Display venue location and capacity
- Associate events with venues

### 🎟️ Tickets
- View tickets for different events
- Display ticket type, price, and available quantity

### 📋 Bookings
- Create event bookings
- Select ticket quantity
- Select seats
- Validate seat availability
- Prevent duplicate seat bookings
- Calculate total booking amount
- View booking details
- Cancel bookings
- Restore ticket availability after cancellation

### 👥 Employees
- View employee information
- Display employee name, email, role, and department

---

## 🛠️ Technology Stack

### Frontend
- Angular
- TypeScript
- HTML
- CSS

### Backend
- Java
- Spring Boot
- Spring Data JPA
- REST APIs
- Maven

### Database
- MySQL

### Development Tools
- Visual Studio Code
- MySQL
- Git
- GitHub
- Thunder Client

---

## 🏗️ System Architecture

`
             ┌─────────────────────┐
             │     Angular UI      │
             │     Frontend        │
             └──────────┬──────────┘
                        │
                        │ REST API
                        ▼
             ┌─────────────────────┐
             │    Spring Boot      │
             │      Backend        │
             └──────────┬──────────┘
                        │
                        │ JPA / Hibernate
                        ▼
             ┌─────────────────────┐
             │       MySQL         │
             │      Database       │
             └─────────────────────┘