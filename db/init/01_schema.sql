-- ============================================================
-- Script de inicialización - Base de datos: reportdb
-- Se ejecuta automáticamente al levantar el contenedor
-- ============================================================

-- ─── CLIENTES ────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS CLIENTES (
    ID_CLIENTE        BIGSERIAL PRIMARY KEY,
    NOMBRE            VARCHAR(100),
    APELLIDO          VARCHAR(100),
    CEDULA            VARCHAR(20)  UNIQUE,
    FECHA_NACIMIENTO  DATE,
    DIRECCION         VARCHAR(200),
    TELEFONO          VARCHAR(20),
    EMAIL             VARCHAR(100),
    FECHA_REGISTRO    DATE DEFAULT CURRENT_DATE
);

-- ─── SUCURSALES ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS SUCURSALES (
    ID_SUCURSAL  BIGSERIAL PRIMARY KEY,
    NOMBRE       VARCHAR(100),
    DIRECCION    VARCHAR(200),
    CIUDAD       VARCHAR(100)
);

-- ─── CUENTAS ─────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS CUENTAS (
    ID_CUENTA      BIGSERIAL PRIMARY KEY,
    ID_CLIENTE     BIGINT NOT NULL,
    ID_SUCURSAL    BIGINT,
    NUMERO_CUENTA  VARCHAR(20) UNIQUE,
    TIPO_CUENTA    VARCHAR(20),   -- AHORROS / CORRIENTE
    SALDO          NUMERIC(15,2),
    ESTADO         VARCHAR(20),   -- ACTIVA / INACTIVA
    FECHA_APERTURA DATE,

    CONSTRAINT FK_CUENTA_CLIENTE
        FOREIGN KEY (ID_CLIENTE)
        REFERENCES CLIENTES(ID_CLIENTE),

    CONSTRAINT FK_CUENTA_SUCURSAL
        FOREIGN KEY (ID_SUCURSAL)
        REFERENCES SUCURSALES(ID_SUCURSAL)
);

-- ─── TRANSACCIONES ────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS TRANSACCIONES (
    ID_TRANSACCION  BIGSERIAL PRIMARY KEY,
    ID_CUENTA       BIGINT NOT NULL,
    TIPO            VARCHAR(20),   -- DEPOSITO / RETIRO / TRANSFERENCIA
    MONTO           NUMERIC(15,2),
    FECHA           DATE,
    DESCRIPCION     VARCHAR(200),

    CONSTRAINT FK_TRANSACCION_CUENTA
        FOREIGN KEY (ID_CUENTA)
        REFERENCES CUENTAS(ID_CUENTA)
);

-- ─── PRESTAMOS ────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS PRESTAMOS (
    ID_PRESTAMO   BIGSERIAL PRIMARY KEY,
    ID_CLIENTE    BIGINT NOT NULL,
    MONTO         NUMERIC(15,2),
    TASA_INTERES  NUMERIC(5,2),
    PLAZO_MESES   INTEGER,
    FECHA_INICIO  DATE,
    ESTADO        VARCHAR(20),   -- ACTIVO / PAGADO / MORA

    CONSTRAINT FK_PRESTAMO_CLIENTE
        FOREIGN KEY (ID_CLIENTE)
        REFERENCES CLIENTES(ID_CLIENTE)
);

-- ─── PAGOS_PRESTAMOS ──────────────────────────────────────────
CREATE TABLE IF NOT EXISTS PAGOS_PRESTAMOS (
    ID_PAGO      BIGSERIAL PRIMARY KEY,
    ID_PRESTAMO  BIGINT NOT NULL,
    MONTO        NUMERIC(15,2),
    FECHA_PAGO   DATE,

    CONSTRAINT FK_PAGO_PRESTAMO
        FOREIGN KEY (ID_PRESTAMO)
        REFERENCES PRESTAMOS(ID_PRESTAMO)
);

-- ─── DATOS DE EJEMPLO ─────────────────────────────────────────
INSERT INTO SUCURSALES (NOMBRE, DIRECCION, CIUDAD) VALUES
    ('Sucursal Central', 'Av. Principal 100', 'Quito'),
    ('Sucursal Norte',   'Calle Norte 200',   'Guayaquil');

INSERT INTO CLIENTES (NOMBRE, APELLIDO, CEDULA, FECHA_NACIMIENTO, DIRECCION, TELEFONO, EMAIL) VALUES
    ('Juan',  'Pérez',  '1711234567', '1985-03-15', 'Calle 1 N12-34', '0991111111', 'juan.perez@mail.com'),
    ('María', 'Gómez',  '1709876543', '1990-07-22', 'Av. 6 de Dic',   '0992222222', 'maria.gomez@mail.com');

INSERT INTO CUENTAS (ID_CLIENTE, ID_SUCURSAL, NUMERO_CUENTA, TIPO_CUENTA, SALDO, ESTADO, FECHA_APERTURA) VALUES
    (1, 1, '0001234567', 'AHORROS',   1500.00, 'ACTIVA', '2022-01-10'),
    (2, 2, '0007654321', 'CORRIENTE', 3200.50, 'ACTIVA', '2021-05-20');

INSERT INTO TRANSACCIONES (ID_CUENTA, TIPO, MONTO, FECHA, DESCRIPCION) VALUES
    (1, 'DEPOSITO',     500.00,  '2026-03-01', 'Depósito en ventanilla'),
    (1, 'RETIRO',       200.00,  '2026-03-10', 'Retiro cajero'),
    (1, 'TRANSFERENCIA',300.00,  '2026-03-15', 'Pago servicios'),
    (2, 'DEPOSITO',    1000.00,  '2026-03-05', 'Abono nómina');
