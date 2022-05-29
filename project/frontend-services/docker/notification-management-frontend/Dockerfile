FROM node:18-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
COPY . .
RUN npm run nx build notification-management-frontend --prod

FROM nginx:1.22.0
COPY apps/notification-management-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/notification-management-frontend /usr/share/nginx/html
