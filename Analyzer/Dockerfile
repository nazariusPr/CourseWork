FROM python:3.12

# Set the working directory
WORKDIR /app

# Copy project files into the container
COPY . /app

# Install dependencies
RUN pip install --upgrade pip && pip install -r requirements.txt

# Expose port 9090
EXPOSE 9090

# Set the entrypoint for your application
ENTRYPOINT ["python"]
CMD ["app.py"]
