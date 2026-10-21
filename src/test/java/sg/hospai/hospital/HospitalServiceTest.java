package sg.hospai.hospital;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.hospai.shared.BusinessRuleException;
import sg.hospai.shared.NotFoundException;
import sg.hospai.hospital.dtos.CreateHospitalRequest;
import sg.hospai.ward.WardRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HospitalServiceTest {

    @Mock
    HospitalRepository hospitalRepository;

    @Mock
    HospitalMapper hospitalMapper;

    @Mock
    WardRepository wardRepository;

    @InjectMocks
    HospitalService hospitalService;

    @Test
    void createRejectsDuplicatedCnpj() {
        when(hospitalRepository.existsByCnpj("12345678000199")).thenReturn(true);

        assertThatThrownBy(() -> hospitalService.create(
                new CreateHospitalRequest("Hospital", "4433334444", "12345678000199")))
                .isInstanceOf(BusinessRuleException.class);
        verify(hospitalRepository, never()).save(any());
    }

    @Test
    void getByIdThrowsNotFoundForUnknownId() {
        when(hospitalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hospitalService.getById(1L))
                .isInstanceOf(NotFoundException.class);
    }
}
