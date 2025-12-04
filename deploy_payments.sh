#!/usr/bin/env bash

# ===== CONFIGURACIÓN =====

# Ruta a tu llave .pem
KEY_PATH="/Volumes/SSD - 1TB/Payments/keys-payments.pem"


# Usuario y host de la EC2
EC2_USER="ec2-user"
EC2_HOST="ec2-18-222-185-228.us-east-2.compute.amazonaws.com"   # ej: ec2-3-120-45-67.compute.amazonaws.com o 3.120.45.67

# Nombre del servicio systemd
SERVICE_NAME="payments"

# Nombre del JAR generado por Maven (ajusta si cambia la versión)
LOCAL_JAR="target/Payments-0.0.1-SNAPSHOT.jar"

# Ruta remota donde vive el JAR en la EC2
REMOTE_DIR="/opt/payments"
REMOTE_JAR="$REMOTE_DIR/Payments.jar"

# ===== NO CAMBIAR DE AQUÍ PARA ABAJO (salvo que sepas lo que haces) =====

set -e  # Detener script si algo falla

echo "👉 1/4 - Construyendo proyecto con Maven..."
mvn clean package -DskipTests

echo "✅ Build completo."

echo "👉 2/4 - Copiando JAR a la EC2..."
scp -i "$KEY_PATH" "$LOCAL_JAR" "$EC2_USER@$EC2_HOST:$REMOTE_JAR.tmp"

echo "✅ Copia realizada."

echo "👉 3/4 - Reemplazando JAR y reiniciando servicio en EC2..."

ssh -i "$KEY_PATH" "$EC2_USER@$EC2_HOST" <<EOF
  set -e

  echo "Deteniendo servicio $SERVICE_NAME..."
  sudo systemctl stop $SERVICE_NAME || true

  echo "Reemplazando archivo JAR..."
  sudo mv "$REMOTE_JAR.tmp" "$REMOTE_JAR"
  sudo chown $EC2_USER:$EC2_USER "$REMOTE_JAR"

  echo "Iniciando servicio $SERVICE_NAME..."
  sudo systemctl start $SERVICE_NAME

  echo "Estado del servicio (primeras líneas):"
  sudo systemctl status $SERVICE_NAME --no-pager -l | head -n 20
EOF

echo "✅ 4/4 - Despliegue completado."
echo "Tu API debería estar arriba en la EC2 🎉"

