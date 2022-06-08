FROM node:18-alpine AS build
WORKDIR /workspace
COPY package.json ./
COPY . .
RUN npm install
RUN npm run nx build identity-management-frontend --omit=dev

FROM nginx:1.22.0
COPY apps/identity-management-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/identity-management-frontend /usr/share/nginx/html
