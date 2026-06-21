#!/bin/bash
# Script para crear las bases de datos necesarias en PostgreSQL
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    SELECT 'CREATE DATABASE db_reportes' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'db_reportes')\gexec
    SELECT 'CREATE DATABASE db_usuarios' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'db_usuarios')\gexec
    SELECT 'CREATE DATABASE db_notificaciones' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'db_notificaciones')\gexec
EOSQL

echo "Bases de datos creadas exitosamente."
