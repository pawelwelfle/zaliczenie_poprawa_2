package edu.iis.mto.testreactor.doser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.testreactor.doser.infuser.Infuser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class MedicineDoserTest {

    @Mock
    private Infuser infuser;
    @Mock
    private DosageLog dosageLog;
    @Mock
    private Clock clock;

    private MedicineDoser medicineDoser;
//    private Receipe receipe;

    private static final CapacityUnit STANDARD_UNIT = CapacityUnit.MILILITER;
    private static final Capacity STANDARD_CAPACITY = Capacity.of(20, STANDARD_UNIT);
    private static final Medicine FIRST_MEDICINE = Medicine.of("First Medicine");
    //    Medicine medicine = Medicine.of("Medicine");
    private static final MedicinePackage TEST_PACKAGE = MedicinePackage.of(FIRST_MEDICINE, STANDARD_CAPACITY);
    private static final Period STANDARD_PERIOD = Period.of(STANDARD_CAPACITY.getAmount(), TimeUnit.HOURS);
    private static final Dose TEST_DOSE = Dose.of(STANDARD_CAPACITY, STANDARD_PERIOD);

    private static final Receipe receipe = Receipe.of(FIRST_MEDICINE, TEST_DOSE, 1);


    @BeforeEach
    void setUp() {
        medicineDoser = new MedicineDoser(infuser, dosageLog, clock);

    }

    @Test
    void shouldIDoseAMedicine() {
        medicineDoser.add(TEST_PACKAGE);
        medicineDoser.dose(receipe);

    }


    @Test
    void itCompiles() {
        assertEquals(2, 1 + 1);
    }
}
