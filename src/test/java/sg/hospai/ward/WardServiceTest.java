package sg.hospai.ward;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.shared.NotFoundException;
import sg.hospai.hospital.Hospital;
import sg.hospai.hospital.HospitalService;
import sg.hospai.ward.dtos.CreateWardRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WardServiceTest {

    @Mock
    WardRepository wardRepository;

    @Mock
    WardMapper wardMapper;

    @Mock
    HospitalService hospitalService;

    @InjectMocks
    WardService wardService;

    @Test
    void createAttachesWardToHospital() {
        Hospital hospital = new Hospital();
        Ward ward = new Ward();
        CreateWardRequest request = new CreateWardRequest(Speciality.CARDIOLOGY);
        when(hospitalService.getById(1L)).thenReturn(hospital);
        when(wardMapper.toEntity(request)).thenReturn(ward);
        when(wardRepository.save(ward)).thenReturn(ward);

        Ward created = wardService.create(1L, request);

        assertThat(created.getHospital()).isSameAs(hospital);
    }

    @Test
    void createRejectsSecondWardOfSameSpeciality() {
        when(hospitalService.getById(1L)).thenReturn(new Hospital());
        when(wardRepository.existsByHospitalIdAndSpeciality(1L, Speciality.CARDIOLOGY)).thenReturn(true);

        assertThatThrownBy(() -> wardService.create(1L, new CreateWardRequest(Speciality.CARDIOLOGY)))
                .isInstanceOf(BusinessRuleException.class);
        verify(wardRepository, never()).save(any());
    }

    @Test
    void createFailsForUnknownHospital() {
        when(hospitalService.getById(1L)).thenThrow(new NotFoundException("Hospital 1 not found"));

        assertThatThrownBy(() -> wardService.create(1L, new CreateWardRequest(Speciality.CARDIOLOGY)))
                .isInstanceOf(NotFoundException.class);
        verify(wardRepository, never()).save(any());
    }
}
