FROM node:14.15-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
RUN npm run postinstall
COPY . .
RUN npm run nx build sharespot-identity-management-frontend --prod

FROM nginx:1.19.8
COPY apps/sharespot-identity-management-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/sharespot-identity-management-frontend /usr/share/nginx/html
