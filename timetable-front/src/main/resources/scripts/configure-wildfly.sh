mkdir -p target/wildfly-8.2.1.Final/modules/system/layers/base/com/mysql/main
cp src/main/resources/standalone.xml target/wildfly-8.2.1.Final/standalone/configuration/
cp src/main/resources/module.xml target/wildfly-8.2.1.Final/modules/system/layers/base/com/mysql/main/
cp src/main/resources/mysql-connector-java-5.1.39.jar target/wildfly-8.2.1.Final/modules/system/layers/base/com/mysql/main/