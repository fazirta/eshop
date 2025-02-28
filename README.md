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

### Reflection 4

In my project, I incorporated all five SOLID principles to keep the code organized and easier to maintain. First, I used the Single Responsibility Principle (SRP) by making sure each class and module handles only one main task. For example, the `ProductController` just deals with product-related requests, while the `ProductService` focuses on business logic for products. This way, if I ever need to change how products are stored or displayed, I can go directly to the relevant class instead of digging through unrelated code.

Next, I followed the Open/Closed Principle (OCP) by creating repository interfaces (`IProductRepository` and `ICarRepository`). This means I can add new repository implementations in the future—like a database-backed repository—without modifying the existing code. I also removed the old inheritance between `CarController` and `ProductController` to respect the Liskov Substitution Principle (LSP). Each controller now stands on its own, and I don’t have to worry about subclass behavior breaking the parent’s contract.

For the Interface Segregation Principle (ISP), I made sure that interfaces only contain the methods they really need. For instance, `ProductService` has just the methods for product operations, and `CarService` has just the methods for car operations. This avoids creating one giant interface with tons of unused methods. Finally, I applied the Dependency Inversion Principle (DIP) by having higher-level modules (like controllers) depend on abstractions (interfaces) instead of concrete classes. This makes it much easier to switch out implementations and also makes testing simpler.

By applying these principles, the code became more flexible and much simpler to work with. Because each class has a single responsibility (thanks to SRP), I can quickly find and fix bugs or add new features without worrying that I’ll break something unrelated. The OCP setup with repository interfaces also lets me switch from an in-memory list to a database or even an external API without changing my controller or service logic—just swap out one repository class for another.

Testing is also more straightforward. For example, when I test the `ProductService`, I can provide a mock repository instead of a real database. This is all thanks to DIP, which ensures my service code doesn’t rely on a specific implementation. Plus, because each interface is small and focused (ISP), I don’t have to implement or mock a bunch of methods I don’t actually need.

If I hadn’t applied these principles, my code would probably be much more tangled. For instance, if my controller handled both HTTP requests and direct database operations, a simple change in how I store data could force me to rewrite parts of the controller, violating SRP and OCP. Without DIP, I’d be stuck with a specific repository implementation, making it really hard to test or move to a different storage approach in the future.

Ignoring LSP and using inheritance incorrectly can also cause big problems. If `CarController` inherited from `ProductController` and had to override methods that didn’t apply to cars, that might break functionality. Plus, not following ISP would mean huge, bloated interfaces that do too many things. All of this adds up to more bugs, more confusion, and a harder time introducing new features.
