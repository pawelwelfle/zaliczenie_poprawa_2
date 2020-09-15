package edu.iis.mto.testreactor.doser;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.testreactor.doser.infuser.Infuser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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


    private DosingResult resultWithSuccess = DosingResult.SUCCESS;

    private static final CapacityUnit STANDARD_UNIT = CapacityUnit.MILILITER;
    private static final Capacity STANDARD_CAPACITY = Capacity.of(20, STANDARD_UNIT);
    private static final Medicine FIRST_MEDICINE = Medicine.of("First Medicine");
    private static final Medicine medicine = Medicine.of("First Medicine");
    private static final MedicinePackage TEST_PACKAGE = MedicinePackage.of(FIRST_MEDICINE, STANDARD_CAPACITY);
    private static final MedicinePackage TEST_EXCEPTION_PACKAGE = MedicinePackage.of(null, STANDARD_CAPACITY);
    private static final Period STANDARD_PERIOD = Period.of(STANDARD_CAPACITY.getAmount(), TimeUnit.HOURS);
    private static final Dose TEST_DOSE = Dose.of(STANDARD_CAPACITY, STANDARD_PERIOD);
    private static final Dose dose = Dose.of(STANDARD_CAPACITY, STANDARD_PERIOD);

    private static final Receipe receipe = Receipe.of(FIRST_MEDICINE, TEST_DOSE, 1);
    private static final Receipe receipeFail = Receipe.of(null, TEST_DOSE, 1);


    @BeforeEach
    void setUp() {
        medicineDoser = new MedicineDoser(infuser, dosageLog, clock);

    }

    @Test
    void shouldIDoseAMedicine() {
        medicineDoser.add(TEST_PACKAGE);
        medicineDoser.dose(receipe);
        DosingResult result = medicineDoser.dose(receipe);
        assertEquals(resultWithSuccess, result);
    }


    @Test
    void checkIfMedicinePackageIfProperCreated() {

        Assertions.assertEquals(TEST_PACKAGE.getMedicine().getName(), FIRST_MEDICINE.getName(), "test medicine");
        Assertions.assertEquals(20, STANDARD_CAPACITY.getAmount(), "test capacity");
        Assertions.assertEquals(TEST_PACKAGE.getCapacity().getAmount(), STANDARD_CAPACITY.getAmount(), "test capacity2");
    }



    @Test
    void shouldThrowUnavailableMedicineException() {
        Map<Medicine, MedicinePackage> medicinesTray = new HashMap<>();
        medicinesTray.put(receipeFail.getMedicine(), TEST_EXCEPTION_PACKAGE);
        MedicinePackage medicinePackage = medicinesTray.get(0);

        Assertions.assertThrows(UnavailableMedicineException.class,
                () -> medicineDoser.add(TEST_EXCEPTION_PACKAGE));
    }

    @Test
    void itCompiles() {
        assertEquals(2, 1 + 1);
    }
}
