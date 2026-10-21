package sg.hospai.ward;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Speciality {

    CARDIOLOGY("CARD"),
    NEUROLOGY("NEUR"),
    ORTHOPEDICS("ORTO"),
    PEDIATRICS("PEDI");

    private final String prefix;
}
