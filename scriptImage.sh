mvn clean package
docker build -t melishorter.jar .

docker tag melishorter.jar mcvetic97/melishorterlocal:v1
docker push mcvetic97/melishorterlocal:v1

#docker tag melishorter.jar mcvetic97/melishorter:v1
#docker push mcvetic97/melishorter:v1

docker tag melishorter.jar mcvetic97/melishorterlocal:v1
docker push mcvetic97/melishorterlocal:v1
