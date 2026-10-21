CREATE TABLE rooms (
    id BIGINT PRIMARY KEY,
    ward_id BIGINT NOT NULL REFERENCES wards (id)
);
