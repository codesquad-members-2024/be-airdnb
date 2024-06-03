# Stage 1: Build
FROM node:20-alpine AS builder
RUN apk add --no-cache python3 make g++

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy all files
COPY . .

# Build the Next.js app
RUN npm run build

# Stage 2: Production
FROM node:20-alpine AS runner

# Set working directory
WORKDIR /app

# Copy the built files from the builder stage
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/public ./public

# Set environment variable for production
ENV NODE_ENV=production

# Expose the port the app runs on
EXPOSE 3000

# Command to run the Next.js app
CMD ["npm", "run", "start"]
