# Art Gallery Management Platform

A **web platform that facilitates the management of artists, exhibitions, and artworks**, offering a modern and organized administration system for art galleries.  
Built with **Spring Boot**, **Thymeleaf**, and **PostgreSQL**, fully deployed as web services on **Render**.

---

## 👨‍💻 Overview

This project provides two access modes:  
- **Administrator** - full management of artists, exhibitions, and artworks.  
- **Visitor** - ability to explore artworks, artists, and gallery information.  

It integrates both a **web interface** and **RESTful services**, ensuring a seamless and secure experience for users and administrators.

---

## 🚀 Main Features

- Complete CRUD management for artists, artworks, and exhibitions.  
- User authentication and authorization using **Spring Security**.  
- Responsive web interface with **Thymeleaf** and **Tailwind CSS**.  
- Database persistence using **JPA/Hibernate** with **PostgreSQL**.  
- RESTful API endpoints for modular integration.  
- Integrated **Docker** support for easy deployment.  
- Database and application hosted on **Render**.

---

## 🧰 Tech Stack

**Core Technologies**
- 🧩 **Spring Boot 3.3.5**
- ☕ **Java 17**
- 🧠 **Spring Data JPA**
- 🕸️ **Spring MVC**
- 🔐 **Spring Security 6.1.4**
- 🎨 **Thymeleaf**
- 🐘 **PostgreSQL**

**Development Tools**
- ⚙️ Maven - dependency management and build  
- 🧱 Lombok - reduce boilerplate code  
- 🧪 Spring Boot DevTools & Test - development and testing utilities  
- 🔗 Apache HttpClient, HttpCore, HttpMime - HTTP communication  
- 🧩 Jakarta Validation API - data validation  
- 🔒 Thymeleaf Extras Spring Security 6 - security integration  

**Frontend & Design**
- 🎨 Figma — UI design  
- 💅 Tailwind CSS, HTML, CSS  

---

## 🧱 Architecture

The project follows an **MVC architecture** (Model–View–Controller) with a clear separation of concerns:

Sistema-Galeria/
├── src/
│ ├── main/
│ │ ├── java/com/usta/sistemagaleria/
│ │ │ ├── BD/ # Database configuration
│ │ │ ├── controllers/ # MVC controllers
│ │ │ ├── entities/ # JPA entities
│ │ │ ├── handler/ # Custom handlers
│ │ │ ├── models/ # Data models
│ │ │ ├── security/ # Security configuration
│ │ │ ├── MvcConfig.java # MVC configuration
│ │ │ └── SistemaGaleriaApplication.java # Main application
│ │ └── resources/
│ │ ├── static/
│ │ │ ├── css/ # Stylesheets
│ │ │ ├── images/ # Static images
│ │ │ └── js/ # JavaScript files
│ │ ├── templates/ # Thymeleaf templates
│ │ │ ├── artistas/ # Artists views
│ │ │ ├── comentarios/ # Comments views
│ │ │ ├── exposiciones/ # Exhibitions views
│ │ │ ├── layout/ # Base layouts
│ │ │ ├── obras/ # Artworks views
│ │ │ ├── error404.html # Error 404 page
│ │ │ ├── error500.html # Error 500 page
│ │ │ ├── index.html # Main page
│ │ │ ├── login.html # Login page
│ │ │ └── register.html # Registration page
│ │ └── application.properties # Application configuration
├── target/ # Compiled files
├── .idea/ # IDE configuration
└── .mvn/ # Maven configuration

## 📽️ Project preview

<div align="center"> 
 <a href="https://youtu.be/7Q91I2dsPGo" target="_blank"> 
    <img src="https://i.ibb.co/N2qhk00f/image.png" alt="Sistema Galería Preview" width="700" style="border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.2);"> 
  </a>
</div>

## 🔗 Connect with Me

<p align="center">
  <a href="https://yourdomain.com" target="_blank">
    <img src="https://img.icons8.com/doodle/48/000000/domain.png" alt="Website"/>
  </a>
  <a href="https://linkedin.com/in/andresgogutierrez/" target="_blank" style="margin-left: 15px;">
    <img src="https://img.icons8.com/doodle/48/000000/linkedin--v2.png" alt="LinkedIn"/>
  </a>
  <a href="https://github.com/AndresGoGutierrez" target="_blank" style="margin-left: 15px;">
    <img src="https://img.icons8.com/doodle/48/000000/github--v1.png" alt="GitHub"/>
  </a>
  <a href="mailto:andregogutierrezgmail.com" target="_blank" style="margin-left: 15px;">
    <img src="https://img.icons8.com/doodle/48/000000/new-post.png" alt="Email"/>
  </a>
</p>
