CREATE TABLE hospitals (
    iD BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(14) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    CONSTRAINT uk_hospitals_cnpj UNIQUE (cnpj)
);
