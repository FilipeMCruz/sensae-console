FROM node:17-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
COPY . .
RUN npm run nx build rule-management-frontend --prod

FROM nginx:1.19.8
COPY apps/rule-management-frontend/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/rule-management-frontend /usr/share/nginx/html
