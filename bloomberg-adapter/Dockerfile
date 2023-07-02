ARG CI_REGISTRY
ARG IMAGE=devops/images/docker/basejava:master

FROM ${CI_REGISTRY}/${IMAGE}

ADD build/libs/bloomberg-adapter.jar .
ENTRYPOINT exec java -Dspring.profiles.active=$PROFILE $JAVA_MEM $JAVA_EXT -jar bloomberg-adapter.jar