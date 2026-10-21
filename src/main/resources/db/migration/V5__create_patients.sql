CREATE TABLE patients (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    phone VARCHAR(14),
    CONSTRAINT uk_patients_cpf UNIQUE (cpf)
);
