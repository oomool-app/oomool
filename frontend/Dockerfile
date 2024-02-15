FROM node:20.11.0-alpine

ENV TZ Asia/Seoul

RUN mkdir /app

WORKDIR /app

RUN mkdir ./build

ADD ./.output ./build

EXPOSE 3000

CMD ["node", "./build/server/index.mjs"]
