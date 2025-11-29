<div align="center">

# 📚 Online Book Store

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-UI-blue?style=for-the-badge&logo=java)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)

<p align="center">
  <strong>A modern, scalable, and intuitive desktop application for managing book sales and inventory.</strong>
</p>

[View Demo](#-screenshots) • [Report Bug](https://github.com/ihsanlearn/toko-buku/issues) • [Request Feature](https://github.com/ihsanlearn/toko-buku/issues)

</div>

---

## 📖 About The Project

**Online Book Store** is a robust desktop application designed to streamline the operations of a bookshop. Built with **Java** and **JavaFX**, it offers a seamless user experience for both customers and administrators. The application follows the **MVC (Model-View-Controller)** architecture, ensuring code maintainability and scalability.

Whether you are a customer looking to browse and buy books, or an administrator managing inventory and sales, this application provides the tools you need in a clean, responsive interface.

### ✨ Key Features

#### � For Customers

- **User Authentication**: Secure login and registration system.
- **Book Browsing**: Search and filter books by category, title, or author.
- **Shopping Cart**: Add items, view cart, and proceed to checkout.
- **Wallet System**: Top-up balance and manage payments securely.
- **Order History**: Track past purchases and order status.

#### 🛡️ For Administrators

- **Dashboard**: Overview of sales, inventory status, and user statistics.
- **Inventory Management**: Add, update, and delete books with ease.
- **Order Management**: View and process customer orders.
- **User Management**: Manage customer accounts and roles.

---

## 🛠️ Tech Stack

| Component        | Technology     | Description                                        |
| ---------------- | -------------- | -------------------------------------------------- |
| **Core**         | Java SE 17     | The main programming language.                     |
| **UI Framework** | JavaFX         | For building the rich desktop user interface.      |
| **Build Tool**   | Apache Maven   | Dependency management and build automation.        |
| **Database**     | MySQL / SQLite | Data persistence (configurable).                   |
| **Architecture** | MVC            | Separation of concerns for better maintainability. |

---

## 📂 Project Structure

```text
toko-buku/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/bookstore/
│   │   │       ├── controller/   # UI Logic & Event Handling
│   │   │       ├── model/        # Data Objects & Business Logic
│   │   │       ├── view/         # FXML files & UI layouts
│   │   │       └── util/         # Helper classes & Database connection
│   │   └── resources/            # Assets, FXML, CSS, Configs
├── data/                         # Database files or scripts
├── target/                       # Compiled binaries
├── pom.xml                       # Maven dependencies
└── README.md                     # Project documentation
```

---

## 🚀 Getting Started

Follow these steps to set up the project locally.

### Prerequisites

- **Java Development Kit (JDK) 17** or higher
- **Maven 3.9+**
- **Git**

### Installation

1.  **Clone the repository**

    ```bash
    git clone https://github.com/ihsanlearn/toko-buku.git
    cd toko-buku
    ```

2.  **Configure Database**

    - Ensure your database server (MySQL) is running if using MySQL.
    - Check `src/main/resources/application.properties` (or similar config file) to set your database credentials.

3.  **Build the project**

    ```bash
    mvn clean install
    ```

4.  **Run the application**
    ```bash
    mvn javafx:run
    ```

---

## 📸 Screenshots

<div align="center">
  <img src="https://via.placeholder.com/800x450?text=Login+Screen" alt="Login Screen" width="800"/>
  <br/><br/>
  <img src="https://via.placeholder.com/800x450?text=Dashboard+View" alt="Dashboard" width="800"/>
</div>

---

## �️ Roadmap

- [x] Core Authentication & Role Management
- [x] Product Browsing & Search
- [x] Shopping Cart & Checkout
- [ ] Payment Gateway Integration
- [ ] Email Notifications
- [ ] Dark Mode Support

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

## 📬 Contact

**Project Maintainers**

- **Ihsan** - [GitHub](https://github.com/ihsanlearn)
- **IMars-kun** - [GitHub](https://github.com/IMars-kun)
- **Tranquilserpentx** - [GitHub](https://github.com/tranquilserpentx)

Project Link: [https://github.com/ihsanlearn/toko-buku](https://github.com/ihsanlearn/toko-buku)

---

<div align="center">
  <p>If you find this project helpful, please give it a ⭐️!</p>
</div>
