**Name**: Muhammad Fazil Tirtana  
**NPM**: 2306274983  
**Class**: Pemrograman Lanjut A  

### Reflection 1  

While developing the **EShop** application, I focused on writing clean, maintainable, and structured code. I organized the project into four key packages—**controller, model, repository, and service**—to ensure a clear separation of concerns, making the codebase more scalable and easier to maintain. Meaningful naming conventions were used for classes and methods to enhance readability. Additionally, I leveraged **Spring Boot’s dependency injection**, which improves modularity and simplifies unit testing by reducing tight coupling between components.  

In terms of functionality, I successfully implemented **CRUD operations** for products, including features to **list, add, edit, and delete products**. The application follows a structured workflow where the repository handles database interactions, the service layer processes business logic, and the controller manages HTTP requests. On the frontend, I added intuitive navigation and action buttons to enhance usability.  

Security and validation remain areas for improvement. Since the application currently uses an **in-memory data structure**, it lacks persistent storage. However, I took initial precautions by **sanitizing user inputs** at the view layer to prevent injection vulnerabilities. Moving forward, I plan to integrate **validation annotations** (e.g., `@Valid`) in the controller to enforce data integrity. Additionally, implementing **custom exception handling** would provide clearer error messages and improve debugging.  

Overall, I’m pleased with how the project aligns with clean code and secure coding principles. With further refinements, including better validation, error handling, and database integration, the application can become more secure, robust, and user-friendly.