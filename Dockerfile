FROM openjdk:17-alpine

# INFORMAR EL PUERTO DONDE SE EJECUTA EL CONTENEDOR (INFORMATIVO)
EXPOSE 8080

# DEFINIR DIRECTORIO RAIZ DE NUESTRO CONTENEDOR
WORKDIR /app

# COPIAR Y PEGAR ARCHIVOS DENTRO DEL CONTENEDOR
COPY ./pom.xml .
COPY ./.mvn ./.mvn
COPY ./mvnw .

# Dar permisos de ejecución al archivo mvnw
RUN chmod +x mvnw

# DESCARGAR LAS DEPENDENCIAS
RUN ./mvnw dependency:go-offline

# COPIAR EL CODIGO FUENTE DENTRO DEL CONTENEDOR
COPY ./src ./src

# CONSTRUIR NUESTRA APLICACION
RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java", "-jar", "/app/target/SchoolBack-1.0.0-Beta.jar"]