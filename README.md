# GetIt – Marketplace and Bidding Android App


📱 **Overview** 

GetIt is a modern Android marketplace and bidding app built using **Jetpack Compose**, Firebase, and the MVVM architecture. It allows users to **list items**, **explore products**, and **place bids** using a streamlined UI with Material 3 support.

👉 [**Watch Demo Video**](https://youtu.be/MKnF0N4H_xw?si=XR3p9fxxm31ewCEw)  



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
    
- **Explore Featured Listings**
  - Home screen shows featured listings (up to 4)
  - A *Show All* button links to a full listings page
    
- **My Listings & Bids**
  - Logged-in users can see and manage their listings and bids only
  
- **Confirmation Dialogs**
  - Delete confirmation to avoid accidental actions
    
- **Dynamic Routing**
  - All listings link to detail pages using `listing-detail/{id}`



## 🧾 Data Structure

There are urrently 2 data models :

1. `listings`
2. `bids`

Each document contains typed fields and UUIDs as document IDs.

## 📊 APIs and Dependencies Used

- **Firebase Authentication** – for managing user login/register

- **Cloud Firestore** – primary storage of listings and bids

- **Firebase AuthService & FirestoreService** – created to abstract Firebase logic

- **Google Material3** – all UI follows the latest Material Design

- **Timber** – for advanced logging

- **Google Play Services** – linked for proper Firebase use



## 🧭 Development Approach

### Architecture

- The app follows the MVVM (Model-View-ViewModel) pattern. Business logic is isolated in ViewModels, while composable UI functions manage state via Kotlin Flows and remember. The app is structured in packages such as screens, ui.component, data.model, firebase.service, and navigation. 

- Repository pattern for Firebase and Room (Room is phased out)

- ViewModels for listings, bids, register, login, etc
  

### UI Design

- Built with Jetpack Compose from scratch

- Material 3 theme used globally

- Custom components: CountryCodeDropdown, ListingMiniCard, TopAppBar, NavDrawer
  

### UX Approach

- Compose First: Faster UI development, state-driven views

- Reactive UIs using StateFlow and remember
  

### 🔁 Git Flow Strategy

- Main Branch: main

- Feature Branches: auth, bids, firestore, etc.

- Meaningful and contextual issues and commits (e.g., Add bid status update, Setup Firebase auth)

- Tagged releases


### 📐 UML & Class Diagrams

**Coming**



## 🔮 Future Enhancements

- Add image uploads with Firebase Storage

- Allow user profile updates

- Enable search and filter features

- Google Sign-In and Password Reset

- Use Firestore fully for all screens

- Enable dark mode, swipe to delete, and improved validation



## 💡 What I Learned

- Firebase integration with Compose

- How to structure MVVM in Compose

- Clean routing with NavController and NavHost

- Managing state across screens

- Firestore document design and lifecycle

- Handling real-time data and updates

- Best practices for ViewModel and  Hilt



## 🧑 Personal Reflection
Working on GetIt helped me grow confidence in building full-stack Android apps. I learned how to write composable UIs, manage state reactively, and work with Firebase securely. I now understand the importance of separation of concerns and following clean architecture. It was also the first time I built a complete flow from login to CRUD in Firestore, and it was incredibly rewarding.



## 📚 References

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Firebase for Androidn](https://firebase.google.com/docs/android/setup)
- [Firebase Authentication using MVVM in Jetpack Compose](https://www.simplifiedcoding.net/firebase-authentication-using-mvvm/)
- [Firebase Authentication in Jetpack Compose - Part 1](https://medium.com/@marwa.diab/firebase-authentication-in-jetpack-compose-part-1-73cb79d3e744)

---


<p float="left">
  <img src="\home.jpg" width="22%" />
  <img src="\navbar.jpg" width="22%" />
  <img src="/explore.jpg" width="22%" />
  <img src="/mybids.jpg" width="22%" />
</p>

<p float="left">
  <img src="\empty my listings.jpg" width="22%" />
  <img src="\list item.jpg" width="22%" />
  <img src="\my listings.jpg" width="22%" />
  <img src="/view listing.jpg" width="22%" />
</p>

---

## 📌 Author

**Ursula Bonte Tamen Kedi**  
BSc (Hons) Software Systems Development  
SETU, Waterford  



