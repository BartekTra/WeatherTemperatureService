# Task 1.
## Weather Temperature Service (AWS Lambda)
## a. Brief description of the solution
This project is AWS Lambda function written in Java 21. Its fetches the current temperature
for location (currently defaulting to Wrocław) using the external Open-Meteo API. After retrieving the numerical temperature,
the application classifies it into a category (e.g. "Freezing", "Cold", "Mild") based on predefined rules and
returns the data as a JSON response.
## b. Explanation of key design decisions
The solution was designed with Object-Oriented Programming principles and Clean Architecture concepts in mind to ensure maintainability
and scalability:
* Separation of Concerns: The code is strictly divided into three main packages:
    * handler: Contains WeatherHandler, which acts purely as an orchestrator. It extracts input, calls the domain service,
  and formats the output, containing zero business logic.
    * domain: Contains WeatherService and TemperatureClassifier.
  This isolates the core business rules (temperature categorization) from any framework or external API dependencies.
    * infrastructure: Handles external communication (HTTP requests to Open-Meteo).
* Dependency Injection & Interfaces: The domain layer relies on abstractions (WeatherApiClient, LocationProvider) rather than concrete 
implementations (OpenMeteoClient). Dependencies are injected via constructors. This decouples the business logic from specific API providers.
* Immutability: record types (WeatherResult, Coordinates) are used to represent data transfer objects ensuring immutability.
* Error Handling and Logging: exception handling mechanism (WeatherApiException) with professional 
SLF4J/Log4j2 logging ensures that all external API failures are handled and logged for debugging, 
while the user receives 500 JSON error response instead of a raw stack trace.
## c. Short note on how the solution could be unit tested without calling the real API
Because the application heavily utilizes Constructor-based Dependency Injection and Interfaces,
unit testing it without hitting the real Open-Meteo API is straightforward.

To test the core logic, we can use a mocking framework (like Mockito) 
or create a simple manual stub class that implements the WeatherApiClient 
interface. We would inject this mock into the WeatherService constructor during the test setup.

By instructing the mock to return predefined temperature values (e.g., -5.0, 15.0, 35.0),
we can write deterministic assertions to verify that our TemperatureClassifier correctly
assigns the "Freezing", "Mild", and "Hot" categories, all without making a single network call.
This ensures tests are fast, reliable, and isolated from external network instability.