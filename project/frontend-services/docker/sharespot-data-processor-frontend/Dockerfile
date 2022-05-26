FROM node:18-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
COPY . .
RUN npm run nx build sharespot-data-processor-frontend --prod

FROM nginx:1.22.0
COPY apps/sharespot-data-processor-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/sharespot-data-processor-frontend /usr/share/nginx/html
