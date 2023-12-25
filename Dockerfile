# Use the official PostgreSQL image as the base image
FROM postgres:16

# Set environment variables for PostgreSQL
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=diver_db

# Copy the init.sql script into the Docker image
COPY init.sql /docker-entrypoint-initdb.d/

# Additional customization if needed

# Specify the command to run when the container starts
CMD ["postgres", "-c", "log_statement=all"]
