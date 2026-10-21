CREATE TABLE beds (
    id BIGINT PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES rooms (id),
    number INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    CONSTRAINT uk_beds_room_number UNIQUE (room_id, number)
);
