# Domain Modelling

- Hopital

- Ward (ala)

- Room

- Bed (leito)

- Patient

- Log

### Log example
|patient_id|bed_id|status|adm_at|dis_at|
|-|-|-|-|-|
|1|1|ACTIVE|19/10|null|
|1|1|INACTIVE|19/10|20/10|
|2|1|ACTIVE|21/10|null|
|2|1|INACTIVE|21/10|22/10|

### Questions
- Can I edit the number of beds, rooms and wards after created? No
- Are room codes generated from their respective names? Yes
- How does the admission log work??
