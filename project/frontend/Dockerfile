FROM node:12.7-alpine AS build
WORKDIR /usr/src/app
COPY location-tracking-app/package.json ./
RUN npm install
COPY location-tracking-app .
RUN npm run prod

FROM nginx:1.19.8
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/location-tracking-app /usr/share/nginx/html
