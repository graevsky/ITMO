<h1>Deployment</h1>
<h3>Helios login and password</h3>
usrname=`s386871`

pswd=`I0oYeruGPnLgdGfp`

<h3>Local login and password for win</h3>
usrname=`postgres`

pswd=`1234`

<h3>Connection</h3>
WEB - `ssh -L localhost:12106:localhost:12106 s386871@se.ifmo.ru -p 2222`<br>
DB - `ssh -p 2222  s386871@se.ifmo.ru`

<h3>Configs</h3>
<h4>wildfly configuration.xml config</h4>
```xml
 <socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}">
        <socket-binding name="ajp" port="${jboss.ajp.port:8009}"/>
        <socket-binding name="http" port="${jboss.http.port:12106}"/>
        <socket-binding name="https" port="${jboss.https.port:8443}"/>
        <socket-binding name="management-http" interface="management" port="${jboss.management.http.port:1999}"/>
        <socket-binding name="management-https" interface="management" port="${jboss.management.https.port:9993}"/>
        <socket-binding name="txn-recovery-environment" port="4712"/>
        <socket-binding name="txn-status-manager" port="4713"/>
        <outbound-socket-binding name="mail-smtp">
            <remote-destination host="${jboss.mail.server.host:localhost}" port="${jboss.mail.server.port:25}"/>
        </outbound-socket-binding>
    </socket-binding-group>
```
<h4>persistence.xml for local win</h4>
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">

    <persistence-unit name="lab3" transaction-type="RESOURCE_LOCAL">
        <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
        <class>org.example.point.Result</class>
        <properties>
            <property name="eclipselink.jdbc.url" value="jdbc:postgresql://localhost:5432/lab3"/>
            <property name="eclipselink.jdbc.user" value="postgres"/>
            <property name="eclipselink.jdbc.password" value="1234"/>
            <property name="eclipselink.jdbc.driver" value="org.postgresql.Driver"/>
            <property name="eclipselink.ddl-generation" value="none"/>
        </properties>
    </persistence-unit>

</persistence>
```
<h4>persistence.xml for helios</h4>
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
                                 http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">

    <persistence-unit name="lab3" transaction-type="RESOURCE_LOCAL">
        <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
        <class>org.example.point.Result</class>
        <properties>
            <property name="eclipselink.jdbc.url" value="jdbc:postgresql://localhost:5432/studs"/>
            <property name="eclipselink.jdbc.user" value="s386871"/>
            <property name="eclipselink.jdbc.password" value="I0oYeruGPnLgdGfp"/>
            <property name="eclipselink.jdbc.driver" value="org.postgresql.Driver"/>
            <property name="eclipselink.ddl-generation" value="none"/>
        </properties>
    </persistence-unit>
</persistence>
```

<h4>WildFly PostgreSQL and CORS issues</h4>

<h5>Прежде всего необходимо добавить драйвер для PostgreSQL:</h5>
```
[WILDFLY_HOME]/
└── modules/
    └── org/
        └── postgresql/
            └── main/
                ├── module.xml
                └── postgresql-42.2.27.jar
```
См. папку psql_driver.

<h5>Так же необходимо в standalone.xml проинициализировать этот драйвер, указать CORS политики. См. папку standalone</h5>

При ошибке (https://github.com/orika-mapper/orika/issues/377) ```Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make protected native java.lang.Object java.lang.Object.clone() throws java.lang.CloneNotSupportedException accessible: module java.base does not \"opens java.lang\" to unnamed module @6d279cde"},``` нужно добавить в standalone.conf/standalone.conf.bat опцию JVM ```JAVA_OPTS="$JAVA_OPTS --add-opens java.base/java.lang=ALL-UNNAMED"```

<h4>Сборка</h4>

```ng build --configuration production --base-href=/lab4/```