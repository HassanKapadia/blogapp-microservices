FROM eclipse-temurin:21-jre
WORKDIR /app

# Build-time ARG to select which jar to copy
ARG SERVICE_NAME

# Copy the prebuilt jar from host
COPY backend/$SERVICE_NAME/build/libs/*.jar /app/$SERVICE_NAME.jar

# Expose the SERVICE_NAME as environment variable at runtime
ENV SERVICE_NAME=${SERVICE_NAME}

# Run the jar using ENV variable
CMD java -jar /app/$SERVICE_NAME.jar
