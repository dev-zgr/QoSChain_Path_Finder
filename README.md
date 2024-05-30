# QoSChain Path Finder

QoSChain Path Finder is a Java-based project designed to find optimal paths in a network of nodes, considering various Quality of Service (QoS) parameters. The project includes data models, repositories, service layers, and utility classes to manage and process the data required for path finding.

## Features

- **Data Layer**: Manages access to data sources and repositories.
- **Service Layer**: Implements core logic for path finding and random data generation.
- **Utility Classes**: Includes mappers and other helper classes for processing data.
- **SQL Scripts**: Provides scripts to set up the necessary database tables and datasets.

## Installation

### Prerequisites

- Java JDK (version 11 or higher)
- Gradle (version 6.0 or higher)
- A SQL database (e.g., MySQL, PostgreSQL)

### Steps

1. **Clone the repository:**

    ```sh
    git clone https://github.com/dev-zgr/QoSChain_Path_Finder.git
    cd QoSChain_Path_Finder
    ```

2. **Build the project:**

    ```sh
    ./gradlew build
    ```

3. **Set up the database:**

    - Execute the SQL scripts in `src/main/resources/SQLScripts` to create the necessary tables and insert initial data.

## Usage

1. **Run the application:**

    ```sh
    ./gradlew run
    ```

2. **Generate random transactions and requests:**

    - The `RandomTransactionGenerator` and `RandomRequestGenerator` classes in the service layer can be used to generate random data for testing.

3. **Path Finding:**

    - Use the `PathFinder` class to find optimal paths based on the QoS parameters.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a Pull Request.

## Acknowledgements

- Contributors and maintainers of the project.
- Libraries and frameworks used in the project.
