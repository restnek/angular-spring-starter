# Angular Spring Boot JWT Starter

> An Angular full stack starter kit featuring
[Angular](https://angular.io),
[Router](https://angular.io/docs/ts/latest/guide/router.html),
[Forms](https://angular.io/docs/ts/latest/guide/forms.html),
[Http](https://angular.io/docs/ts/latest/guide/server-communication.html),
[Services](https://angular.io/guide/architecture-services),
[Spring Boot](https://projects.spring.io/spring-boot/),
[JSON Web Token](https://jwt.io/)

## Quick start

Make sure you have docker and docker-compose. To run the application, use the following command
in a terminal window:

```bash
docker-compose up
```

There are two user accounts present to demonstrate the different levels of access to the endpoints
in the API and the different authorization exceptions:

- User - user:Qwerty1!
- Admin - admin:Qwerty1!

### JSON Web Token

> JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely
between two parties. For more info, check out https://jwt.io/

> Token authentication is a more modern approach and is designed solve problems session IDs stored
server-side can’t. Using tokens in place of session IDs can lower your server load, streamline
permission management, and provide better tools for supporting a distributed or cloud-based
infrastructure.
>
> -- <cite>Stormpath</cite>

___

## License

[MIT](/LICENSE)
