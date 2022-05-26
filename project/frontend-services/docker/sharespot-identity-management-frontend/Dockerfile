FROM node:18-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
COPY . .
RUN npm run nx build sharespot-identity-management-frontend --prod

FROM nginx:1.22.0
COPY apps/sharespot-identity-management-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/sharespot-identity-management-frontend /usr/share/nginx/html
