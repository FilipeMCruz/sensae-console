FROM node:14.15-alpine AS build
WORKDIR /usr/src/app
COPY location-tracking-frontend/package.json ./
RUN npm install
COPY location-tracking-frontend .
RUN npm run prod

FROM nginx:1.19.8
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/location-tracking-frontend /usr/share/nginx/html
