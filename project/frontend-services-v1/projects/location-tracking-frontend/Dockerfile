FROM node:14.15-alpine AS build
WORKDIR /usr/src/app
COPY project/package.json ./
RUN npm install
COPY project .
RUN npm run prod

FROM nginx:1.19.8
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/sharespot-location-tracking-frontend /usr/share/nginx/html
