# API REST Pagination 🚀
This project is an example of a REST API that implements pagination in its endpoints. Here are some important guidelines:

**Annotations**✅: Annotations are used in the DTOs (Data Transfer Objects) to ensure data integrity and guarantee that they comply with the established requirements before being processed.

**HTTP status codes**🌐: API endpoints handle HTTP responses according to the situation, returning status codes that accurately reflect the outcome of the operations (such as 200 OK, 400 Bad Request, 404 Not Found, etc.).

**Versioning** 📊: Endpoints are versioned to ensure compatibility with future versions of the API, allowing changes to be made without disrupting users who depend on previous versions.

**Pagination**📄: Pagination is implemented in the endpoints to efficiently handle large datasets, improving performance and user experience.

**Global Exception Handling**⚠️: A `GlobalExceptionHandler` is used to manage exceptions in a centralized manner. This includes:
- **Validations**: Validation exceptions, such as `MethodArgumentNotValidException` and `ConstraintViolationException`, are handled to provide clear and specific error messages.
- **Data Integrity**: Data integrity violations, such as `DataIntegrityViolationException`, are caught to prevent conflicts and deliver useful error messages.
- **General Errors**: Other exceptions, such as `ResourceNotFoundException` and `MethodArgumentTypeMismatchException`, are managed to ensure appropriate and helpful responses are returned.

Thanks for visiting!👋  Don't forget to leave a star⭐ if you liked the project.
> [!WARNING]
> The project uses a PostgreSQL database, so you should change the credentials in the following path: `src/main/resources/application.properties`.

> [!IMPORTANT]
> In the root of the project, you will find the API documentation in the file named `REST-API-PAGINATION.postman_collection.json`. There, you can test the different endpoints.