FROM node:14.15-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
RUN npm run postinstall
COPY . .
RUN npm run nx build sharespot-data-processor-frontend --prod

FROM nginx:1.19.8
COPY apps/sharespot-data-processor-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/sharespot-data-processor-frontend /usr/share/nginx/html
