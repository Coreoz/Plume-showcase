Plume showcase
==============
This project enables to test quickly the [Plume Framework](https://github.com/Coreoz/Plume) ecosystem. It contains:
- An embedded database
- An integration with [Plume Admin](https://github.com/Coreoz/Plume-admin)

A [React](https://github.com/facebook/react/) administration frontend using this showcase project can be setup quickly with [Create Plume React Project](https://github.com/Coreoz/create-plume-react-project).

Configuring your IDE
--------------------
Install [Lombok](https://projectlombok.org/): http://jnb.ociweb.com/jnb/jnbJan2010.html#installation

Launching the server
--------------------
Just run the `com.coreoz.WebApplication` class, you can then open your browser to <http://localhost:8080>.

Configuration
-------------
The configuration file is located in `src/main/resources/application.conf`.
If you have any doubt, check out the [configuration documentation](https://github.com/Coreoz/Plume/tree/master/plume-conf). 

Administration area
-------------------
The [React frontend](#plume-showcase) can be started using `yarn start` and opening <http://localhost:3000/>.

In the login screen, the credentials are:
- login: admin
- password: admin

Swagger
-------
Swagger is pre-configured to provide documentation about the project web-services.
This documentation is protected by credentials that should be configured in the `application.conf` file.

To access this documentation, start the project
and go to <http://localhost:8080/>.
As a reminder, the default Swagger credentials are: `plume//rocks`.
