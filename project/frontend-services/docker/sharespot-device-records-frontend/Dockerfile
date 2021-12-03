FROM node:14.15-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
RUN npm run postinstall
COPY . .
RUN npm run nx build sharespot-device-records-frontend --prod

FROM nginx:1.19.8
COPY apps/sharespot-device-records-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/sharespot-device-records-frontend /usr/share/nginx/html
