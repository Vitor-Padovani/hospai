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

## Relatórios

Todos os paths ficam sob o context-path `/api`.

| Requisição | Endpoint |
|-|-|
| Leitos livres por especialidade | `GET /beds/available?speciality=CARDIOLOGY` (opcional: `hospitalId`) |
| Quarto em que o paciente está internado | `GET /patients/{id}/current-admission` (campo `roomCode`) |
| Todos os leitos | `GET /beds` |
| Quartos livres, ocupados e total por especialidade | `GET /rooms/occupancy` |
| Internação atual do paciente: hospital, especialidade/ala, código do quarto, paciente e data/hora de internação | `GET /patients/{id}/current-admission` |
| Histórico de internações do paciente (paginado): paciente, especialidade/ala, data de internação e de alta | `GET /patients/{id}/admissions?page=0&size=20` |
| Quartos com algum leito disponível: especialidade/ala e código do quarto | `GET /rooms/available` |
| Pacientes internados agora, por especialidade e em ordem alfabética: paciente, data de internação e dias internado | `GET /admissions/active` |
| Histórico de internações de um leito: número do leito, paciente, data de internação e de alta | `GET /beds/{id}/admissions` |

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