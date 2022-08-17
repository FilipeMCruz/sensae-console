FROM node:18-alpine AS build
WORKDIR /workspace
COPY package.json ./
COPY . .
RUN npm install
RUN npm run nx build device-management-frontend --omit=dev

FROM nginx:1.23.1
COPY apps/device-management-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/device-management-frontend /usr/share/nginx/html
