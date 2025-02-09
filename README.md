# Simple Command Line Interpreter (CLI)

This project implements a basic Command Line Interpreter (CLI) in Java, inspired by Unix/Linux shells.  It supports executing system commands and a few internal commands.

## Features

* **Command Execution:** Executes common system commands:
    * `pwd`: Prints the current working directory.
    * `cd`: Changes the current working directory.
    * `ls`: Lists directory contents. Supports options:
        * `ls -a`: Lists all files, including hidden files.
        * `ls -r`: Lists files in reverse order.
    * `mkdir`: Creates a new directory.
    * `rmdir`: Removes an empty directory.
    * `touch`: Creates a new empty file.
    * `mv`: Moves or renames files or directories.
    * `rm`: Removes a file or directory.
    * `cat`: Displays the contents of a file.
    * `>`: Redirects standard output to a file (overwrites).
    * `>>`: Redirects standard output to a file (appends).
    * `|`: Pipes the output of one command to the input of another.

* **Internal Commands:**
    * `exit`: Terminates the CLI.
    * `help`: Displays available commands and their usage.

## Building and Running

1. **Prerequisites:**
   - Java Development Kit (JDK) 8 or later.
   - Maven (recommended for dependency management and building).  If you don't have Maven, you can download the required JUnit jar files and add them to the project manually.

2. **Building with Maven:**
   - Clone the repository: `git clone <repository_url>`
   - Navigate to the project directory: `cd simple-cli`
   - Compile and package the project: `mvn clean package`

3. **Running the CLI:**
   - After building, the executable JAR file will be located in the `target` directory.
   - Run the CLI: `java -jar target/simple-cli-<version>.jar`  (Replace `<version>` with the actual version number).

## JUnit Testing

The project includes JUnit tests to ensure the correct functionality of the CLI. The tests cover various aspects of command execution. The primary test suite is located in `src/test/java/com/example/CommandsRepositoryTest.java`.

1. **Running Tests:**
   - In your IDE, locate the `CommandsRepositoryTest.java` file.
   - Run the JUnit tests.  Your IDE should provide a way to run all tests in a class.

2. **Viewing Test Results:**
   - The test results will be displayed in your IDE's console or test results window.


## Usage

Once the CLI is running, you can enter commands at the prompt.  For help on available commands and their usage, type `help` and press Enter.
