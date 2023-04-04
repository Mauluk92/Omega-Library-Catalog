# Omega-Library-Catalog
![Java](https://img.shields.io/badge/java%20v.17-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

This is the catalog microservice for managing books in Omega Library.
It is intended to be used both as an OPAC and as a managing library system, in communication with a loan microservice(in development)
## Features

Includes the following controllers: books, authors and tags
Each book has potentially many authors and many tags which define its belonging category
### Book endpoints

| Path                                     | Method | Functionality |
|------------------------------------------| --- | --- |
| /book                                    | POST | Add a single book with informations |
| /book/{id}                               | GET | Retrieve a single book by its id |
| /book/{id}                               | DELETE | Remove a book by its id |
| /book/{id}                               | PUT | Update book informations |
| /book/{id}                               | PATCH | Update the availability status of the book |
| /books?pageNum&pageSize&title&tag&author | GET | Filtered search on books |
| /books/orphaned?pageNum&pageSize         | GET | Paginated search on orphaned books (without authors or tags) |
| /book/associate/{id}                     | PATCH | Associate a book with authors and tags |
|/books | PATCH | Patch multiple books status |

### Author endpoints

| Path | Method | Functionality |
| --- | --- | --- |
|/author| POST | Add a single author |
|/author/{id} | GET | Rertrieve a single author |
|/author/{id} | DELETE | Delete a single author |
|/author/{id} | PUT | Update a single author |
|/authors?pageSize&pageNum&name | GET | Paginated search on authors |


### Tags Endpoints

| Path      | Method | Functionality |
|-----------| --- | --- |
| /tag      | POST | Add a single tag for categorizing books |
| /tag/{id} | GET | Get a single tag by its id|
| /tag/{id} | DELETE | Remove a tag by its id |
| /tag/{id} | PUT | Update a single tag by its id |
|/tags?pageNum&pageSize&tag | GET | Filtered paginated search on tags by its name |

### Further Documentation on endpoints

Further informations about the endpoints can be found on SwaggerHub [Here](https://app.swaggerhub.com/apis/NIKOLAJBOGDANOROV/swagger-catalog_omega_library_open_api_3_0/1.1.0).


## Filtering functionality

Filters are implemented through JPA specification by means of a SpecificationBuilder interface, which
provides the abstraction to build the specification through a particular request. Here's the interface:

```java
public interface SpecificationBuilder<T, E> {

    SpecificationBuilder<T, E> setFilter(T filterRequest);

    Specification<E> build();

}
```

An implementation showed as an example is this:

```java
public class BookByAuthorSpecification implements Specification<Book>, SpecificationBuilder<SearchBooksDto, Book> {

    private SearchBooksDto searchBooksDto;
    private final String AUTHOR_FIELD_IN_BOOK = "authorList";
    private final String AUTHOR_FIELD = "id";

    @Override
    public SpecificationBuilder<SearchBooksDto, Book> setFilter(SearchBooksDto searchBooksDto) {
        this.searchBooksDto = searchBooksDto;
        return this;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<Book, Author> booksByAuthor = root.join(AUTHOR_FIELD_IN_BOOK);
        return Objects.nonNull(searchBooksDto.getAuthorId())
                ? criteriaBuilder.equal(booksByAuthor.get(AUTHOR_FIELD), searchBooksDto.getAuthorId())
                : criteriaBuilder.conjunction();
    }

    @Override
    public Specification<Book> build() {
        return this;
    }
}
```

This class provides the tool to filter books by its author (if provided) 

## Grpc

The microservice includes also a grpc server for further future uses in communication with other down-streams services.
The server starts by default at 9090 port of localhost.


## Database

I've used a MySQL relational database, and i have included the E-R diagram for clarity here(together withe script to be used to generate the tables and schema)

![E-R-Diagram](https://github.com/Mauluk92/Omega-Library-Catalog/blob/main/src/main/resources/static/omega-catalog-E-R.png)

I've used two many to many relationship to model the links between authors, tags and books via join tables.


## Other technologies

I've used Mapstruct as a Mapper, Lombok, and provided Junit 5 tests for both controllers and services endpoints.
