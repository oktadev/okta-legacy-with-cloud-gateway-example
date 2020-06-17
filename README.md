
# Secure Legacy Applications with Spring Cloud Gateway
 
This repository contains examples of how to secure a "legacy" servlet application Spring Cloud Gateway.

This repository has two examples in it:
- `cloud-gateway` - A basic Spring Cloud Gateway application that proxies and secures downstream requests
- `legacy-servlet-app` - A simple servlet application.

* See the [Add OAuth 2.0 to Legacy Apps with Spring Cloud Gateway][blog-post] learn more about how these projects were created.

**Prerequisites:** [Java 8](https://sdkman.io/sdks#java).

* [Spring Boot + Spring Cloud Example](#spring-boot-gateway-example)
* [Legacy Servlet Application Example](#legacy-servlet-application-example)
* [Links](#links)
* [Help](#help)
* [License](#license)

## Spring Cloud Gateway Example

To install this example, run the following commands:

```bash
git clone https://github.com/oktadeveloper/okta-legacy-with-cloud-gateway-example.git
cd okta-legacy-with-cloud-gateway-example/cloud-gateway
```

### Create a Web Application in Okta

Log in to your Okta Developer account (or [sign up](https://developer.okta.com/signup/) if you don't have an account).

1. From the **Applications** page, choose **Add Application**.
2. On the Create New Application page, select **Web**.
3. Give your app a memorable name, add `http://localhost:8080/login/oauth2/code/okta` as a Login redirect URI, select **Refresh Token** (in addition to **Authorization Code**), and click **Done**.

Copy the issuer (found under **API** > **Authorization Servers**), client ID, and client secret into the `src/main/resources/application.yml` file of the `cloud-gateway` project.

```yaml
okta:
  oauth2:
    issuer: https://{yourOktaDomain}/oauth2/default
    client-id=$clientId
    client-secret=$clientSecret
```

Then, run the project with: `./mvnw spring-boot:run`


## Legacy Servlet Application Example

In a seperate terminal window (in the same git repostiory as above) run:

```bash
cd legacy-servlet-app
./mvnw jetty:run -Dokta.oauth2.issuer=https://{yourOktaDomain}/oauth2/default
```

**NOTE:** the "issuer" must be the same in both projects.

Browse to `http://localhost:8080` in an incognito/private window to login and view the user profile data.

## Links

These examples uses the following open source libraries:

* [Okta Spring Boot Starter](https://github.com/okta/okta-spring-boot) 
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
* [Spring Security](https://spring.io/projects/spring-security)

## Help

Please post any questions as comments on the example's [blog post][blog-post], or on the [Okta Developer Forums](https://devforum.okta.com/).

## License

Apache 2.0, see [LICENSE](LICENSE).

[blog-post]: https://developer.okta.com/blog/2020/01/08/secure-legacy-spring-cloud-gateway
