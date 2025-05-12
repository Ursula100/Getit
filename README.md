# GetIt – Marketplace Android App

---

## 📱 Overview

GetIt is a modern Android marketplace app built using **Jetpack Compose**, Firebase, and the MVVM architecture. It allows users to **list items**, **explore products**, and **place bids** using a streamlined UI with Material 3 support.

---

## ✅ Key Features

- **User Authentication** (Email & Password)
  - Registration collects full name, phone number (with country code), email, and password
  - Login system with validation
- **Listing Management**
  - Users can create, update, or delete listings
  - Each listing includes: title, description, price, location, categories, and item condition
- **Bidding System**
  - Users can place bids on other listings
  - Top 5 bids are displayed in descending order
  - Previous top bid is marked as **OUTBID** automatically
  - Users **cannot** place bids on their own listings
- **Explore Page**
  - Home screen shows featured listings (up to 4)
  - A *Show All* button links to a full listings page
- **My Listings & Bids**
  - Logged-in users can see and manage their listings and bids only
- **Confirmation Dialogs**
  - Delete confirmation to avoid accidental actions
- **Dynamic Routing**
  - All listings link to detail pages using `listing-detail/{id}`

---

## 🧾 Firestore Data Structure

We currently use **two collections**:

1. `listings`
2. `bids`

Each document contains typed fields and UUIDs as document IDs.

### 📸 Firestore Screenshots (Replace with your own)

