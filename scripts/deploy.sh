#!/bin/bash
BUILD_JAR=$(ls /home/ubuntu/app/fis_police_server/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/app/deploy.log

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/app/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/app/deploy.log
else
  echo "> kill -15 $CURRENT_PID" >> /home/ubuntu/app/deploy.log
  kill -15 $CURRENT_PID
  sleep 30
fi

echo "> build 파일 복사" >> /home/ubuntu/app/deploy.log
DEPLOY_PATH=/home/ubuntu/app/
cp $BUILD_JAR $DEPLOY_PATH

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포" >> /home/ubuntu/app/deploy.log
nohup java -jar $DEPLOY_JAR > /home/ubuntu/app/nohup.out 2>&1 &
#원보라 - 위에 코드로 작성해야 codedeploy 무한 로딩 안돌고 nohup.out 으로 확인 가능
#nohup java -jar $DEPLOY_JAR >> /home/ubuntu/deploy.log 2>/home/ubuntu/app/deploy_err.log &