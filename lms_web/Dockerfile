FROM node:22-alpine AS build-stage
WORKDIR /app
ARG VITE_API_GATEWAY_URL
COPY . .
COPY .production.env .env
ENV VITE_API_GATEWAY_URL=${VITE_API_GATEWAY_URL}
RUN npm install
RUN npm run build

# production stage
FROM nginx:1.27-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 5175

CMD ["nginx", "-g", "daemon off;"]