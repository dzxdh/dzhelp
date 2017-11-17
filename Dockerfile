FROM ibmjava:8
RUN mkdir /var/app
#docker 服务器环境下 docker时间跟宿主机时间同步
#RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && ntpdate cn.pool.ntp.org
ENV LANG C.UTF-8
WORKDIR /var/app/
ADD ./build/libs /var/app/libs
EXPOSE 8080
CMD ["java","-jar","libs/app-1.0-SNAPSHOT-shadow.jar"]

