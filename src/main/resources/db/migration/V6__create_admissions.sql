CREATE TABLE admissions (
    id BIGINT PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patients (id),
    bed_id BIGINT NOT NULL REFERENCES beds (id),
    status VARCHAR(30) NOT NULL,
    admission_at TIMESTAMP NOT NULL,
    discharge_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);
