-- Clientes 
INSERT INTO CLIENTE(nombre, email, estado) 
    VALUES ('JUAN PEREZ', 'JUAN.PEREZ@TEST.COM', 1);
  
INSERT INTO CLIENTE(nombre, email, estado) 
    VALUES ('MARIA LOPEZ', 'MARIA.LOPEZ@TEST.COM', 1);

INSERT INTO CLIENTE(nombre, email, estado) 
    VALUES ('CARLOS SANCHEZ', 'CARLOS.SANCHEZ@TEST.COM', 1);

-- Estados
INSERT INTO estado_compra(nombre_estado) 
    VALUES ('PREPARACION');

INSERT INTO estado_compra(nombre_estado) 
    VALUES ('DESPACHABLE');

INSERT INTO estado_compra(nombre_estado) 
    VALUES ('EN TRANSITO');

INSERT INTO estado_compra(nombre_estado) 
    VALUES ('ENTREGADO');

INSERT INTO estado_compra(nombre_estado) 
    VALUES ('CANCELADO');

-- Marcas
INSERT INTO MARCA(nombre) 
    VALUES ('ROYAL CANIN');

INSERT INTO MARCA(nombre) 
    VALUES ('PEDIGREE');

INSERT INTO MARCA(nombre) 
    VALUES ('WHISKAS');

INSERT INTO MARCA(nombre) 
    VALUES ('PURINA');

INSERT INTO MARCA(nombre) 
    VALUES ('PRO PLAN');

INSERT INTO MARCA(nombre) 
    VALUES ('PETLIFE');

INSERT INTO MARCA(nombre) 
    VALUES ('HAPPY PAWS');


-- Tipos de Productos
INSERT INTO TIPO_PRODUCTO(nombre) 
    VALUES ('ALIMENTO');


INSERT INTO TIPO_PRODUCTO(nombre) 
    VALUES ('HIGIENE');


INSERT INTO TIPO_PRODUCTO(nombre) 
    VALUES ('JUGUETES');


INSERT INTO TIPO_PRODUCTO(nombre) 
    VALUES ('ACCESORIOS');


INSERT INTO TIPO_PRODUCTO(nombre) 
    VALUES ('SALUD');


-- Productos
-- ALIMENTO
INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('CROQUETAS PREMIUM PERRO 10KG', 5000, 45000, 1, 1);

INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('ALIMENTO GATO ADULTO 5KG', 4500, 30000, 1, 2);

-- HIGIENE
INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('SHAMPOO ANTIPULGAS PERRO', 3000, 12000, 2, 3);

INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('ARENA SANITARIA GATO 10KG', 6000, 10000, 2, 4);

-- JUGUETES
INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('PELOTA DE GOMA RESISTENTE', 8000, 5000, 3, 5);

INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('RATÓN DE JUGUETE PARA GATO', 7500, 3500, 3, 6);

-- ACCESORIOS
INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('CORREA AJUSTABLE PERRO', 4000, 8000, 4, 7);

INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('PLATO ACERO INOXIDABLE', 3500, 6000, 4, 1);

-- SALUD
INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('ANTIPARASITARIO PERRO', 2000, 15000, 5, 2);

INSERT INTO PRODUCTO(nombre, stock, precio, id_tipo_producto, id_marca)
    VALUES ('VITAMINAS PARA GATO', 2500, 13000, 5, 3);

COMMIT;
