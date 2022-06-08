FROM node:18-alpine AS build
WORKDIR /workspace
COPY package.json ./
COPY . .
RUN npm install
RUN npm run nx build smart-irrigation-frontend --omit=dev

FROM nginx:1.22.0
COPY apps/smart-irrigation-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/smart-irrigation-frontend /usr/share/nginx/html
