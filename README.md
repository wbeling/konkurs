# Konkurs Application 

For testing, use simple Docker file like this:

```dockerfile
FROM maven:3.6.3-openjdk-17
ADD . .
RUN ".build.sh"
EXPOSE 8080
CMD "./run.sh"
```