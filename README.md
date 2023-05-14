# Konkurs Application 

To read the SAST (Static Application Security Testing) report go here: https://github.com/wbeling/konkurs/actions/workflows/sast.yml

For testing use simple Dockerfile file like this:

```dockerfile
FROM maven:3.6.3-openjdk-17
ADD . .
RUN ".build.sh"
EXPOSE 8080
CMD "./run.sh"
```