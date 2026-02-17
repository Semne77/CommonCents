# CommonCents

CommonCents is a full-stack personal finance web application for tracking income, expenses, savings, and financial goals across customizable time ranges.

![Dashboard Screenshot](docs/images/dashboard.png)
## Tech Stack

- **Frontend:** React (Vite)
- **Backend:** Spring Boot (REST API)
- **Database:** JPA / Hibernate
- **Testing:** Vitest + React Testing Library

## Features

- User authentication (sign up / login)
- Dynamic dashboard:
  - Adjustable time ranges
  - Auto-calculated income, expenses, savings
  - Category breakdown chart
- Transaction management:
  - Income/expense toggle
  - Automatic expense negation
  - Bulk `.tsv` upload
- Goals system
- Account settings

## Testing Strategy

- Unit tests for components
- Integration tests for full page flows


