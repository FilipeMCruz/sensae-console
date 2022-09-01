FROM node:18-alpine AS build
WORKDIR /workspace
COPY package.json ./
COPY . .
RUN npm install
RUN npm run nx build fleet-management-frontend --omit=dev

FROM nginx:1.23.1
COPY apps/fleet-management-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/fleet-management-frontend /usr/share/nginx/html
