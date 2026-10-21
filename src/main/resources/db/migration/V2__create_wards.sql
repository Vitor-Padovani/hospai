CREATE TABLE wards (
    id BIGINT PRIMARY KEY,
    hospital_id BIGINT NOT NULL REFERENCES hospitals (id),
    speciality VARCHAR(30) NOT NULL,
    CONSTRAINT uk_wards_hospital_speciality UNIQUE (hospital_id, speciality)
);
