# Diving into docker

FROM python:3.8-alpine

MAINTAINER Joao Paulo Barraca

LABEL version="1.0"

RUN mkdir /app
RUN mkdir /app/www

WORKDIR /app

COPY requirements.txt requirements.txt

RUN pip install -r requirements.txt

COPY app.py app.py

EXPOSE 8080/tcp

VOLUME /app/www

ENTRYPOINT ["python3", "app.py"]
