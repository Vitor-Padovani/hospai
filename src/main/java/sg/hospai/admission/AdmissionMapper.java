package sg.hospai.admission;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sg.hospai.admission.dtos.ActiveAdmissionResponse;
import sg.hospai.admission.dtos.AdmissionResponse;
import sg.hospai.admission.dtos.BedAdmissionHistoryResponse;
import sg.hospai.admission.dtos.CurrentAdmissionResponse;
import sg.hospai.admission.dtos.PatientAdmissionHistoryResponse;
import sg.hospai.admission.dtos.SpecialityAdmissionsResponse;
import sg.hospai.room.RoomMapper;
import sg.hospai.ward.Speciality;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = RoomMapper.class)
public interface AdmissionMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "bed.id", target = "bedId")
    AdmissionResponse toResponse(Admission admission);

    @Mapping(source = "bed.room.ward.hospital.name", target = "hospitalName")
    @Mapping(source = "bed.room.ward.speciality", target = "speciality")
    @Mapping(source = "bed.room", target = "roomCode", qualifiedByName = "roomCode")
    @Mapping(source = "patient.name", target = "patientName")
    CurrentAdmissionResponse toCurrentResponse(Admission admission);

    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "admissionAt", target = "daysAdmitted", qualifiedByName = "daysSince")
    ActiveAdmissionResponse toActiveResponse(Admission admission);

    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "bed.room.ward.speciality", target = "speciality")
    PatientAdmissionHistoryResponse toPatientHistoryResponse(Admission admission);

    @Mapping(source = "bed.number", target = "bedNumber")
    @Mapping(source = "patient.name", target = "patientName")
    BedAdmissionHistoryResponse toBedHistoryResponse(Admission admission);

    List<BedAdmissionHistoryResponse> toBedHistoryResponse(List<Admission> admissions);

    // Expects admissions already ordered by speciality; keeps that order in the groups.
    default List<SpecialityAdmissionsResponse> toGroupedResponse(List<Admission> admissions) {
        Map<Speciality, List<ActiveAdmissionResponse>> bySpeciality = new LinkedHashMap<>();
        for (Admission admission : admissions) {
            bySpeciality
                    .computeIfAbsent(admission.getBed().getRoom().getWard().getSpeciality(), s -> new ArrayList<>())
                    .add(toActiveResponse(admission));
        }
        return bySpeciality.entrySet().stream()
                .map(entry -> new SpecialityAdmissionsResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Named("daysSince")
    default long daysSince(LocalDateTime admissionAt) {
        return ChronoUnit.DAYS.between(admissionAt.toLocalDate(), LocalDate.now());
    }
}
