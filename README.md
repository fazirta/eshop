**Name**: Muhammad Fazil Tirtana  
**NPM**: 2306274983  
**Class**: Pemrograman Lanjut A  

### Reflection 1  

While developing the **EShop** application, I focused on writing clean, maintainable, and structured code. I organized the project into four key packages—**controller, model, repository, and service**—to ensure a clear separation of concerns, making the codebase more scalable and easier to maintain. Meaningful naming conventions were used for classes and methods to enhance readability. Additionally, I leveraged **Spring Boot’s dependency injection**, which improves modularity and simplifies unit testing by reducing tight coupling between components.  

In terms of functionality, I successfully implemented **CRUD operations** for products, including features to **list, add, edit, and delete products**. The application follows a structured workflow where the repository handles database interactions, the service layer processes business logic, and the controller manages HTTP requests. On the frontend, I added intuitive navigation and action buttons to enhance usability.  

Security and validation remain areas for improvement. Since the application currently uses an **in-memory data structure**, it lacks persistent storage. However, I took initial precautions by **sanitizing user inputs** at the view layer to prevent injection vulnerabilities. Moving forward, I plan to integrate **validation annotations** (e.g., `@Valid`) in the controller to enforce data integrity. Additionally, implementing **custom exception handling** would provide clearer error messages and improve debugging.  

Overall, I’m pleased with how the project aligns with clean code and secure coding principles. With further refinements, including better validation, error handling, and database integration, the application can become more secure, robust, and user-friendly.

### Reflection 2

Working on unit and functional testing for the **EShop** application was a valuable learning experience that gave me greater confidence in the reliability of our codebase. Writing tests not only helped me ensure that each component functioned correctly but also exposed potential edge cases that I hadn't considered before. Through this process, I realized how essential it is to have comprehensive test coverage that includes both positive and negative scenarios. However, I also learned that **100% code coverage does not necessarily mean that the code is bug-free**—while it’s a useful metric, the quality and effectiveness of the test cases matter just as much, if not more.

If I were to create a new functional test suite for verifying the number of items in the product list, simply duplicating the previous test structure would introduce unnecessary redundancy and **reduce overall code quality**. The key issues include **repetitive setup code**, **violating the DRY principle**, and **reduced maintainability**. By **abstracting common setup logic**, **using reusable utility methods**, and **applying design patterns like Page Object Model**, we can significantly improve test cleanliness and maintainability. This approach ensures that functional tests remain **scalable, modular, and easy to update** as the application grows.

This experience reinforced the importance of not just writing tests but writing **clean, maintainable tests**. Good testing practices go hand in hand with good coding practices. Moving forward, I plan to refine the testing structure further, ensuring that our tests remain modular, scalable, and efficient. These improvements will help maintain the **EShop** application as a well-tested, robust, and maintainable system.

### Reflection 3

Throughout this exercise, I worked on improving the maintainability and clarity of our codebase by addressing various code quality issues. One of the first things I fixed was removing unnecessary `public` modifiers in the `ProductService` interface since they were redundant and didn't follow best practices. In the `EshopApplication` class, I initially added a private constructor and marked the class as final to satisfy PMD warnings related to utility classes. However, after realizing that Spring Boot configuration classes need to be instantiable and non-final for proxying purposes, I reversed those changes and instead suppressed the "UseUtilityClass" warning to keep everything functional while still addressing the static analysis concerns.  

Another challenge I tackled was a Gradle permission error in our Dockerfile. I resolved this by making sure the `gradlew` script was executable, which eliminated the issue and ensured that our build process could run smoothly in a containerized environment.  

In terms of CI/CD, the workflow I implemented fully supports Continuous Integration and Continuous Deployment. Every commit automatically triggers our test suite and static analysis checks, helping to catch issues early in the development cycle. Once all tests pass, the code is deployed to our PaaS environment without requiring manual intervention. This automation not only improves the overall quality of the code but also makes the development process much more efficient and reliable.  

Overall, this exercise helped me better understand best practices for maintaining a clean codebase and reinforced the importance of automating testing and deployment to streamline software development.