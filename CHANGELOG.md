# Changelog

## [1.3.1] - 2024-10-06

### Added
- Added new unit tests

### Changed
- Updated unit tests
- Updated Swagger annotations

### Removed
- Disabled integration tests since H2 database is not working anymore

## [1.3.0] - 2024-10-05

### Added

- Added `retrieveTransactionsByIds` method in `TransactionController`. Updated other classes
  accordingly.

### Changed

- Updated `maven-surefire-plugin` configuration
- Updated `maven-failsafe-plugin` configuration
- Updated `maven-compiler-plugin` configuration
- Updated `spring-boot-maven-plugin` configuration

## [1.2.0] - 2024-9-15

### Changed

- Upgrade to Java 17
- Update `Dockerfile` to use `openjdk:17-jdk-alpine`
- Update `pom.xml` to set `maven.compiler.source` and `maven.compiler.target` to `17`

## [1.1.1] - 2024-09-15

### Added

- Added CHANGELOG.md file

### Changed

- Created new README file
- Updated LICENSE file

## [1.1.0] - 2022-09-09

### Changed

- Upgrade to Java 11
- Update all dependencies

## [1.0.1] - 2019-07-23

### Added

- Unit tests for various controllers
- Swagger configuration

### Changed

- Moved DTOs to `dto` package
- Optimized annotations

## [1.0.0] - 2019-07-19

### Added

- Initial release
- User account management
- Transaction processing
- Balance inquiries
- Transaction history
- Docker configuration
- REST API for client, account, and transaction management
- Unit tests setup with H2 database
- JavaDoc and code refactoring
- Initial entities and APIs
- Swagger integration

### Changed

- Refactored and cleaned up code
- Updated properties and Swagger configuration

### Fixed

- Data.sql initialization for Spring DB

## [0.1.0] - 2019-02-01

### Added

- Initial project commit
- Settings and Swagger
- Model added
- Controller, repository, service, and Jersey config
- Filter added
- AccountRepository, TransactionRepository, AccountService, TransactionService
- Account entity, TransactionEntity, updated Client entity
- REST API for client, account, and transaction management
- JavaDoc and code refactoring
- Initial Docker configuration
- Unit tests setup with H2 database
- REST-Assured tests for Client, Account, and Transaction
- Dockerfile, docker-compose.yml, start.sh for Docker
- LICENSE file

### Changed

- Reformat code
- Change package
- Update balance
- Small changes to properties and Swagger config

### Fixed

- Data.sql initialization for Spring DB