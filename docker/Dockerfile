FROM java:8-jre
LABEL maintainer="Rafael Blanco Cordero <rblanco@gfi.es>"

ADD ./target/mcsv-MS001-PruebaNumero29.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/mcsv-MS001-PruebaNumero29.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:${PORT}/health || exit 1

EXPOSE ${PORT}