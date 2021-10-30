FROM node:12.7-alpine AS build
WORKDIR /usr/src/app
COPY sharespot-services-app/package.json ./
RUN npm install
COPY sharespot-services-app .
RUN npm run prod

FROM nginx:1.19.8
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/sharespot-services-app /usr/share/nginx/html
