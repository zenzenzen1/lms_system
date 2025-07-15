FROM node:22.16-bookworm as build-stage
WORKDIR /app
# ARG VITE_FIREBASE_API_KEY
# ARG VITE_API_GATEWAY_URL
COPY . .
# ENV VITE_API_GATEWAY_URL=${VITE_API_GATEWAY_URL}
# ENV VITE_FIREBASE_API_KEY=${VITE_FIREBASE_API_KEY}

# RUN echo "VITE_FIREBASE_API_KEY=${VITE_FIREBASE_API_KEY}" > .env && \
#     echo "VITE_API_GATEWAY_URL=${VITE_API_GATEWAY_URL}" >> .env
RUN npm install
EXPOSE 5175
ENTRYPOINT ["npm", "run", "dev"]
# RUN npm run build

# # production stage
# FROM nginx:1.27-alpine

# RUN rm -rf /usr/share/nginx/html/*

# COPY --from=build-stage /app/dist /usr/share/nginx/html
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# EXPOSE 80

# CMD ["nginx", "-g", "daemon off;"]