# SalesSparrow

This repository contains a SalesSparrow app built using Android that follows MVVM architecture,
Coroutines,
Hilt, Room, Data Binding, View Binding, Retrofit, and Repository pattern. It also includes a Network
service built on top of Retrofit and a push notification setup, along with TDDS (Test-Driven
Development Setup).

## Table of Contents

- [Features](#features)
- [Architecture Overview](#architecture-overview)
- [Product Flavors](#product-flavors)
- [Setup Instructions](#setup-instructions)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)
- [Code of Conduct](#code-of-conduct)
- [Testing and Quality Assurance](#testing-and-quality-assurance)

## Features

Key features of Sales Sparrow project:

- MVVM architecture
- Coroutines for asynchronous operations
- Hilt for dependency injection
- Room for local database handling
- Data Binding and View Binding for UI setup
- Retrofit for network communication
- Repository pattern for data management
- Network service setup for API calls
- Push notification integration
- Test-Driven Development Setup (TDDS)

## Architecture Overview

The MVVM architecture with Jetpack Compose follows a unidirectional flow of data and communication
between different components:

1. **View (Activity / Fragment):**
   The View layer represents the user interface, implemented using Jetpack Compose. It observes the
   ViewModel and displays the data received from it. The View is responsible for capturing user
   interactions and forwarding them to the ViewModel for processing.

2. **ViewModel:**
   The ViewModel acts as a mediator between the View and the domain layer. It exposes LiveData or
   StateFlow objects for the View to observe. When the View requires data or actions, it
   communicates
   with the ViewModel. The ViewModel may call the domain layer to retrieve data or perform business
   logic.

3. **Domain (Use-Cases):**
   The domain layer contains use-cases that define the actions or operations that the application
   can perform. Each use-case represents a specific functionality of the app. Use-cases are
   responsible for orchestrating the flow of data between the ViewModel and the data layer.

4. **Data (Repository):**
   The data layer abstracts the data sources (local and remote) from the domain layer. It contains a
   repository that acts as the single source of truth for data. The repository can retrieve data
   from local data sources (Room database) or remote data sources (API calls with Retrofit).

5. **Local Data Source (Room):**
   The local data source is responsible for handling data stored locally in a Room database. It
   includes data access objects (DAOs) to perform database operations like CRUD (Create, Read,
   Update, Delete) operations.

6. **Remote Data Source (Retrofit):**
   The remote data source handles data communication with the server using Retrofit for making API
   calls. It defines interfaces for the API endpoints and handles network requests and responses.

## Product Flavors

The Sales Sparrow project includes different product flavors for staging, development, and
production environments. The environment variables can be managed using separate property files for
each
flavor.

- **Staging:** staging.properties
- **Development:** dev.properties
- **Production:** prod.properties

You can use these properties for adding different environment-based configurations. For example:

**sample.properties:**

- DEV_API_URL = https://sample-api.com
- DEV_API_LABEL = staging

## Setup Instructions

Provide step-by-step instructions on setting up the Android project in Kotlin:

1. Clone the repository: `git clone https://github.com/TrueSparrowSystems/sales-sparrow-android.git`
2. Open Android Studio and import the project.
3. Create the necessary `properties` files for each product flavor (staging.properties,
   dev.properties, prod.properties) and add the required environment variables.
4. Build the project and ensure there are no errors.
5. Select the desired product flavor in Android Studio to build the corresponding version of the
   app.

## Dependencies

- Retrofit: HTTP client for network communication.
- Coroutines: For managing asynchronous operations.
- Hilt: For dependency injection.
- Room: For local database management.
- Data Binding and View Binding: For setting up the UI.
- Navigation Component: For navigating between different screens.

### License

This project is licensed under the MIT License. See the LICENSE file for details.

### Contributing

Contributions to the project are welcome! If you would like to contribute, please follow the
guidelines in the CONTRIBUTING.md file.

### Code of Conduct

This project has a code of conduct that outlines expected behavior for contributors and users.
Please read the CODE_OF_CONDUCT.md file before getting involved.

### Testing and Quality Assurance

The app includes unit tests and UI tests to ensure the functionality is working as expected.
Follow these steps to run the test cases:

1. Open the project in Android Studio.
2. In the top navigation bar, next to the "Build" option, you will find a dropdown menu.
3. Click on the dropdown menu and select the test case you want to run.
4. Click on the "Run" button to execute the selected test case.
5. To view more detailed test results, check the links provided in the test output.

---

Happy coding! 🎉
