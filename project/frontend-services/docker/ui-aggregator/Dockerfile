FROM node:14.15-alpine AS build
WORKDIR /workspace
COPY package.json ./
RUN npm install
RUN npm run postinstall
COPY . .
RUN npm run nx build ui-aggregator --prod

FROM nginx:1.19.8
COPY apps/ui-aggregator/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /workspace/dist/apps/ui-aggregator /usr/share/nginx/html
