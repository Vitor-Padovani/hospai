# Hospai

## Setup
```bash
docker compose up -d
sdk env # Opcional
./mvnw spring-boot:run
```
### Adminer
- http://localhost:8081/?pgsql=postgres&username=postgres&db=hospapi&ns=public&table=hospitals

### Swagger
- http://localhost:8080/api/swagger-ui/index.html

## Diagrama
```mermaid
erDiagram
    HOSPITAL ||--o{ WARD : "tem (máx. 1 por especialidade)"
    WARD ||--o{ ROOM : possui
    ROOM ||--o{ BED : possui
    PATIENT ||--o{ ADMISSION : "é internado em"
    BED ||--o{ ADMISSION : "ocupado por"

    HOSPITAL {
        Long id PK
        String name
        String phone
        String cnpj UK
    }
    WARD {
        Long id PK
        Long hospital_id FK
        Speciality speciality
    }
    ROOM {
        Long id PK
        Long ward_id FK
        Integer number "código derivado: PREFIXO-número"
    }
    BED {
        Long id PK
        Long room_id FK
        Integer number
        BedStatus status
    }
    PATIENT {
        Long id PK
        String name
        String cpf UK
        String phone
    }
    ADMISSION {
        Long id PK
        Long patient_id FK
        Long bed_id FK
        AdmissionStatus status
        LocalDateTime admissionAt
        LocalDateTime dischargeAt
        LocalDateTime createdAt
    }

```