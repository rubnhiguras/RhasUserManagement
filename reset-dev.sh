#!/bin/bash

set -e  # detener el script si falla algo

echo "🔄 Limpiando contenedores, red y volumen de datos..."

# Función para detener y eliminar un contenedor si existe
clean_container() {
  local container=$1
  if docker ps -a --format '{{.Names}}' | grep -q "^${container}$"; then
    echo "🛑 Deteniendo contenedor: $container"
    docker stop "$container" > /dev/null
    echo "🗑️ Eliminando contenedor: $container"
    docker rm "$container" > /dev/null
  else
    echo "✅ Contenedor $container no existe. Saltando..."
  fi
}

# Limpiar contenedores y volumenes
clean_container springboot-app
clean_container postgres
docker volume rm -f postgres_data 2>/dev/null
docker volume prune -f

# Eliminar red si existe
if docker network ls --format '{{.Name}}' | grep -q "^usermanagementnetwork$"; then
  echo "🧹 Eliminando red: usermanagementnetwork"
  docker network rm usermanagementnetwork > /dev/null
else
  echo "✅ Red usermanagementnetwork no existe. Saltando..."
fi


# Crear red personalizada (ignorar si ya existe)
docker network create usermanagementnetwork || true
# Crear volumen para datos (ignorar si ya existe)
docker volume inspect postgres_data >/dev/null 2>&1 || docker volume create postgres_data

echo "🔄 Iniciando contenedor PostgreSQL..."

docker run -d --name postgres --network usermanagementnetwork \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin123 \
  -e POSTGRES_DB=usermanagementdb \
  -v postgres_data:/var/lib/postgresql/data \
  -e SPRING_PROFILES_ACTIVE=dev \
  -p 5432:5432 \
  postgres:16

echo "⏳ Esperando a que PostgreSQL esté listo..."

# Esperar activamente usando contenedor temporal
until docker run --rm --network usermanagementnetwork \
  postgres:16 pg_isready -h postgres -U admin > /dev/null 2>&1; do
  echo "❌ PostgreSQL no está listo aún..."
  sleep 1
done

echo "✅ PostgreSQL está listo."
echo "🔄🚀 Lanzando backend..."

cd ./user-management-backend
# Ejecutar el contenedor de Spring Boot
docker buildx create --use
docker build -t usuariomanagement/user-management-backend:latest .
docker run -d --name springboot-app --network usermanagementnetwork \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/usermanagementdb \
  -e SPRING_DATASOURCE_USERNAME=admin \
  -e SPRING_DATASOURCE_PASSWORD=admin123 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e JWT_SECRET=$(openssl rand -base64 64 | tr -d '\n' | tr -d '+/' | head -c 64) \
  -p 8080:8080 \
  usuariomanagement/user-management-backend:latest

echo "✅🚀 Backend levantado correctamente."
echo "🔄🚀 Lanzando frontend..."
# Ejecutar el contenedor de front-end
cd ../user-management-frontend

echo "✅🚀 Frontend levantado correctamente."

docker build -t user-management-frontend .
docker run -d -p 3000:80 --name user-frontend user-management-frontend


echo "✅🚀 Todo levantado correctamente."
cd ..
echo "ℹ️  Accede a http://localhost:8080/swagger-ui/index.html para ver el Swagger y a http://localhost/management para ver la GUI."
