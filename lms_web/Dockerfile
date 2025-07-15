FROM node:22.16-bookworm as build-stage
WORKDIR /app
COPY . .
COPY .production.env .env
RUN npm install
RUN npm run build

# production stage
FROM nginx:1.27-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]