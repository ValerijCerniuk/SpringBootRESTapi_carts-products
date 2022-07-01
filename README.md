# practicalTask1.
1. Create spring boot rest api application.
2. Create Cart and Products entities with one to many relationship.
3. Create service layer, repository layer and representational (controller) layer for both entities.
4. Add custom exceptions.
5. Wrap controller responses in ResponseEntity
6. Add validation service to validate missing mandatory fields. (Use custom exceptions), also validate negative prices or other measurements.
7. Add logging.
8. Add custom functionality as Mail sending, pdf invoice generation, json file generation, or something else.
9. Add some custom database methods/queries.
10. Use clean architecture and good practices. (@Query annotation)
So at the end customer using rest api would be able to create a cart, add products, update or delete products, delete or update the cart as well,
find products by name and specific price range, or something else (like find most expensive cart, etc).
Viska sudeti i GITA.

For Email need add SMPT server setup(addres/password). NOT INCLIUDED for security reason.

JUnit test dosen't work with in app randomli generated data. Needed separate H2 database configuration for test.

Carts not binded to users yet.

Test data...

Roles to add in H2 roles table:
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

user to add in POSTMAN :
POST http://localhost:8080/api/auth/signup
body>JSON
{
    "username": "user",
    "email": "user@test.com",
    "password": "123456",
    "role": ["user"]
}
next...
{
    "username": "mod",
    "email": "mod@test.com",
    "password": "123456",
    "role": ["user","mod"]
}
next...
{
    "username": "admin",
    "email": "admin@test.com",
    "password": "123456",
    "role": ["user","admin","mod"]
}01
