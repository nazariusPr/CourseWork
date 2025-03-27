# Pull the base image with Node.js 20 (as you already have)
FROM node:20-buster-slim

EXPOSE 8081

# Install global packages
ENV NPM_CONFIG_PREFIX=/home/node/.npm-global
ENV PATH /home/node/.npm-global/bin:$PATH

# Install specific version of npm and expo-cli to avoid issues with Node.js >= 17
RUN npm install -g npm@10.8.1 expo-cli@6.3.10

# Install dependencies first (this way it uses cache unless package.json/package-lock.json changes)
RUN mkdir /opt/Client
WORKDIR /opt/Client
ENV PATH /opt/Client/.bin:$PATH
COPY ./package.json ./package-lock.json ./
RUN npm install

# Copy in the source code last to optimize the build
WORKDIR /opt/Client/app
COPY . .

# Use expo-cli to start the Expo project
ENTRYPOINT ["npx", "expo", "start"]
