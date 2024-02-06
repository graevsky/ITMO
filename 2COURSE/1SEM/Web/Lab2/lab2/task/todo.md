# Startup:
1. Для управления порт форвардинг на порт 9990: `ssh -L localhost:9990:localhost:9990 s386871@se.ifmo.ru -p 2222`. В нем настройка деплоя.
2. Для просмотра порт форвардинг на 12106: `ssh -L localhost:12106:localhost:12106 s386871@se.ifmo.ru -p 2222`.
3. Собирается в `.war` под Java 8. В buld.gradle указан путь к javac.exe. Для MacOS удалить и настроить в структуре проекта/скачать для жабу 8 для макоси. 
4. Настройка портов в `standalone.xml` в `configuration`:
```xml
<socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}">
    <socket-binding name="ajp" port="${jboss.ajp.port:8009}"/>
    <socket-binding name="http" port="${jboss.http.port:12106}"/>
    <socket-binding name="https" port="${jboss.https.port:8443}"/>
    <socket-binding name="management-http" interface="management" port="${jboss.management.http.port:9990}"/>
    <socket-binding name="management-https" interface="management" port="${jboss.management.https.port:9993}"/>
    <socket-binding name="txn-recovery-environment" port="4712"/>
    <socket-binding name="txn-status-manager" port="4713"/>
    <outbound-socket-binding name="mail-smtp">
        <remote-destination host="${jboss.mail.server.host:localhost}" port="${jboss.mail.server.port:25}"/>
    </outbound-socket-binding>
</socket-binding-group>
```
