# Reduce URL

Reduce URL is a shorter URL project to provide a better marketing for your business.

-Docker

-SpringBoot

-MongoDB

-RabbitMQ


## Installation

You need Docker in your pc :)
- https://www.hostinger.com.br/tutoriais/install-docker-ubuntu


Execute this command in the project`s root folder:
```bash
docker-compose up --build

```


## Functionalities

- Create a new short URL 
```bash
POST: http://localhost:8080/reduce

{
   "url":"https://github.com/gabrielpborba/reduce-url/"
}

Response:
http://localhost:8080/redirect/31fdbf2d
   

*clicking on the url you will be redirected

```
- Delete all URLs


```bash

DELETE: http://localhost:8080/deleteAll

```

- Find all URLs


```bash

GET: http://localhost:8080/findAll

```

---


![Image description](https://github.com/gabrielpborba/reduce-url/blob/develop_fix/reduceurl.jpg)
