-- ============================================================
-- Script de datos masivos para pruebas
-- ============================================================

CREATE TABLE IF NOT EXISTS VISITAS_PAGINA (
    ID_CONTADOR    BIGINT PRIMARY KEY,
    TOTAL_VISITAS  BIGINT NOT NULL DEFAULT 0
);

-- Limpiar datos existentes (respetando FK)
TRUNCATE TABLE PAGOS_PRESTAMOS, TRANSACCIONES, PRESTAMOS, CUENTAS, CLIENTES, SUCURSALES RESTART IDENTITY CASCADE;
TRUNCATE TABLE VISITAS_PAGINA;

INSERT INTO VISITAS_PAGINA (ID_CONTADOR, TOTAL_VISITAS)
VALUES (1, 0);

-- ─── SUCURSALES (10) ─────────────────────────────────────────
INSERT INTO SUCURSALES (NOMBRE, DIRECCION, CIUDAD)
SELECT
    'Sucursal ' || i,
    'Av. Principal ' || (i * 100) || ' y Calle ' || i,
    (ARRAY['Quito','Guayaquil','Cuenca','Ambato','Loja','Manta','Ibarra','Portoviejo','Riobamba','Esmeraldas'])[i]
FROM generate_series(1, 10) AS i;

-- ─── CLIENTES (200) ──────────────────────────────────────────
INSERT INTO CLIENTES (NOMBRE, APELLIDO, CEDULA, FECHA_NACIMIENTO, DIRECCION, TELEFONO, EMAIL, FECHA_REGISTRO)
SELECT
    (ARRAY['Juan','María','Carlos','Ana','Luis','Rosa','Pedro','Laura','Diego','Sofía',
           'Miguel','Valeria','Andrés','Natalia','Jorge','Paola','Roberto','Gabriela','Fernando','Isabella'])[((i-1) % 20) + 1],
    (ARRAY['García','López','Martínez','González','Rodríguez','Hernández','Pérez','Sánchez','Ramírez','Torres',
           'Flores','Rivera','Gómez','Díaz','Cruz','Morales','Reyes','Ortiz','Núñez','Castillo'])[((i-1) % 20) + 1],
    LPAD(i::TEXT, 10, '0'),
    DATE '1970-01-01' + (RANDOM() * 15000)::INT,
    'Calle ' || i || ' N' || (i * 2) || '-' || (i * 3),
    '09' || LPAD((10000000 + i)::TEXT, 8, '0'),
    'cliente' || i || '@mail.com',
    DATE '2020-01-01' + (RANDOM() * 1500)::INT
FROM generate_series(1, 200) AS i;

-- ─── CUENTAS (300) ───────────────────────────────────────────
-- Algunos clientes pueden tener más de una cuenta
INSERT INTO CUENTAS (ID_CLIENTE, ID_SUCURSAL, NUMERO_CUENTA, TIPO_CUENTA, SALDO, ESTADO, FECHA_APERTURA)
SELECT
    ((i - 1) % 200) + 1,
    ((i - 1) % 10) + 1,
    LPAD(i::TEXT, 10, '0'),
    CASE WHEN i % 3 = 0 THEN 'CORRIENTE' ELSE 'AHORROS' END,
    ROUND((RANDOM() * 9900 + 100)::NUMERIC, 2),
    CASE WHEN i % 10 = 0 THEN 'INACTIVA' ELSE 'ACTIVA' END,
    DATE '2018-01-01' + (RANDOM() * 2000)::INT
FROM generate_series(1, 300) AS i;

-- ─── TRANSACCIONES (2000) ────────────────────────────────────
INSERT INTO TRANSACCIONES (ID_CUENTA, TIPO, MONTO, FECHA, DESCRIPCION)
SELECT
    ((i - 1) % 300) + 1,
    (ARRAY['DEPOSITO','RETIRO','TRANSFERENCIA'])[((i - 1) % 3) + 1],
    ROUND((RANDOM() * 1900 + 100)::NUMERIC, 2),
    DATE '2024-01-01' + (RANDOM() * 450)::INT,
    CASE ((i - 1) % 3)
        WHEN 0 THEN 'Depósito en ventanilla N°' || i
        WHEN 1 THEN 'Retiro cajero automático N°' || i
        ELSE        'Transferencia electrónica N°' || i
    END
FROM generate_series(1, 2000) AS i;

-- ─── PRESTAMOS (150) ─────────────────────────────────────────
INSERT INTO PRESTAMOS (ID_CLIENTE, MONTO, TASA_INTERES, PLAZO_MESES, FECHA_INICIO, ESTADO)
SELECT
    ((i - 1) % 200) + 1,
    ROUND((RANDOM() * 49000 + 1000)::NUMERIC, 2),
    ROUND((RANDOM() * 15 + 5)::NUMERIC, 2),
    (ARRAY[6, 12, 18, 24, 36, 48, 60])[((i - 1) % 7) + 1],
    DATE '2022-01-01' + (RANDOM() * 1000)::INT,
    (ARRAY['ACTIVO','ACTIVO','ACTIVO','PAGADO','MORA'])[((i - 1) % 5) + 1]
FROM generate_series(1, 150) AS i;

-- ─── PAGOS_PRESTAMOS (500) ───────────────────────────────────
INSERT INTO PAGOS_PRESTAMOS (ID_PRESTAMO, MONTO, FECHA_PAGO)
SELECT
    ((i - 1) % 150) + 1,
    ROUND((RANDOM() * 900 + 100)::NUMERIC, 2),
    DATE '2022-06-01' + (RANDOM() * 1200)::INT
FROM generate_series(1, 500) AS i;

-- ─── RESUMEN ─────────────────────────────────────────────────
SELECT 'SUCURSALES'     AS tabla, COUNT(*) AS registros FROM SUCURSALES
UNION ALL
SELECT 'CLIENTES',       COUNT(*) FROM CLIENTES
UNION ALL
SELECT 'CUENTAS',        COUNT(*) FROM CUENTAS
UNION ALL
SELECT 'TRANSACCIONES',  COUNT(*) FROM TRANSACCIONES
UNION ALL
SELECT 'PRESTAMOS',      COUNT(*) FROM PRESTAMOS
UNION ALL
SELECT 'PAGOS_PRESTAMOS',COUNT(*) FROM PAGOS_PRESTAMOS
UNION ALL
SELECT 'VISITAS_PAGINA', COUNT(*) FROM VISITAS_PAGINA;
