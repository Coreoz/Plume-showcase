plume-demo-admin
=============

Welcome to your [Plume](https://github.com/Coreoz/Plume) project!
Here are some reminders to get your project up and running.

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

Database
--------
To connect to a database, the Plume Querydsl module must be configured:
1. Setup the database connector in the `pom.xml` file (look for the "PUT YOUR DATABASE CONNECTOR HERE" comment
2. Setup the database connection parameters in the `application.conf` file,
see the [Plume Querydsl documentation](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl#configuration) for details
3. Add the Plume Querydsl module in the Guice configuration class `ApplicationModule`
by uncommenting the line `install(new GuiceQuerydslModule());`

To generate classes corresponding to the database tables,
you can run the `com.coreoz.db.QuerydslGenerator.main()` method.
Before the first run, do not forget to configure
the `TABLES_PREFIX` constant in `QuerydslGenerator`, to match your tables prefix.
For example, if your tables are named `abc_film` and `abc_actor`, then your prefix will be `abc_`.

See the detailed documentations:
- [Plume Database](https://github.com/Coreoz/Plume/tree/master/plume-db)
- [Plume Querydsl](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl)
- [Plume Querydsl codegen](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl-codegen)

Removing Qurerydsl
------------------
To completly get rid of the database component:
1. Remove the line `install(new GuiceQuerydslModule());` in the `ApplicationModule` class,
2. Remove the package `db` in the project,
3. Remove the dependencies `plume-db-querydsl` and `plume-db-querydsl-codegen` in the `pom.xml` file.

Swagger
-------
Swagger is pre-configured to provide documentation about the project web-services.
This documentation is protected by credentials that should be configured in the `application.conf` file.

To access this documentation, start the project
and go to <http://localhost:8080/>.
As a reminder, the default Swagger credentials are: `plume//rocks`.

More modules
------------
- [Plume Mail](https://github.com/Coreoz/Plume/tree/master/plume-mail): send mails,
- [Plume Scheduler](https://github.com/Coreoz/Plume/tree/master/plume-scheduler): schedule recurring Java jobs/tasks,
- [Plume Admin](https://github.com/Coreoz/Plume-admin): web-service with a security layer to create an admin area,
- [Plume Admin AngularJs](https://github.com/Coreoz/Plume-admin-ui-angularjs): an administration HTML/JS UI that use Plume Admin,
- [Plume File](https://github.com/Coreoz/Plume-file/tree/master/plume-file-core): manage and serve files,
- [Plume File Gallery](https://github.com/Coreoz/Plume-file/tree/master/plume-file-gallery): manage medias galleries.

Check the [demo project](https://github.com/Coreoz/Plume-demo/tree/master/plume-demo-full-guice-jersey)
to see an example with these modules.

Deploying to production
-----------------------
In the default mode, when `mvn package` is executed, a zip files is generated.
This file contains all the projects jar files and startup BAT/Bash files.
These startup files will not work since they are built only for Play Framework.
This solution is not ideal, eventually we should make our own Maven plugin
so that the startup files works correctly with Plume Framework.

If `appserver` is used, then configure the server with `export SERVER=javazip`,
it will correctly build and launch the project.
If not there are 3 solutions:
- switch back to the WAR file generation: see the [Plume War archetype](https://github.com/Coreoz/Plume-archetypes/tree/master/plume-archetype-querydsl-jersey-guice),
- create a maven plugin like `play2-maven-plugin` that produce Plume compatible startup scripts (if you choose this option, please share your work :),
- replace in the `pom.xml` file the `play2-maven-plugin` and the `maven-jar-plugin` plugins by
```xml
<!-- single jar executable with all dependencies -->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-shade-plugin</artifactId>
	<version>3.1.0</version>
	<configuration>
		<filters>
			<filter>
				<artifact>*:*</artifact>
				<excludes>
					<exclude>META-INF/*.SF</exclude>
					<exclude>META-INF/*.DSA</exclude>
					<exclude>META-INF/*.RSA</exclude>
				</excludes>
			</filter>
		</filters>
	</configuration>
	<executions>
		<execution>
			<phase>package</phase>
			<goals>
				<goal>shade</goal>
			</goals>
			<configuration>
				<transformers>
					<transformer
						implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer" />
					<transformer
						implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
						<mainClass>com.coreoz.WebApplication</mainClass>
					</transformer>
				</transformers>
			</configuration>
		</execution>
	</executions>
</plugin>
```
With this solution, `mvn package` will produce an executable jar file.
Note that this last solution may produce side effects: files that share the same name can be overriden.
However all Plume modules will work as expected with this solution.

