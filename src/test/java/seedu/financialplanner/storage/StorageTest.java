package seedu.financialplanner.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.financialplanner.enumerations.ExpenseType;
import seedu.financialplanner.enumerations.IncomeType;
import seedu.financialplanner.exceptions.FinancialPlannerException;
import seedu.financialplanner.list.Expense;
import seedu.financialplanner.list.CashflowList;
import seedu.financialplanner.list.Income;
import seedu.financialplanner.utils.Ui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StorageTest {
    @TempDir
    public static Path testFolder;
    protected CashflowList cashflowList = CashflowList.getInstance();
    protected Ui ui = Ui.getInstance();
    @Test
    public void loadValidData() throws FinancialPlannerException {
        Storage storage = Storage.INSTANCE;
        cashflowList.list.clear();
        storage.load(cashflowList, ui, "src/test/testData/ValidData.txt");
        String actual = cashflowList.getList();
        cashflowList.list.clear();
        getTestData();
        String expected = cashflowList.getList();
        assertEquals(expected, actual);
    }

    @Test
    public void loadInvalidData_userInputNo() {
        Storage storage = Storage.INSTANCE;
        cashflowList.list.clear();
        ByteArrayInputStream in = new ByteArrayInputStream("n".getBytes());
        ui.setScanner(new Scanner(in));
        assertThrows(FinancialPlannerException.class,
                () -> storage.load(cashflowList, ui, "src/test/testData/InvalidData.txt"));
    }

    @Test
    public void saveValidData() throws FinancialPlannerException, IOException {
        cashflowList.list.clear();
        getTestData();
        Storage storage = Storage.INSTANCE;
        storage.save(cashflowList, String.valueOf(testFolder.resolve("temp.txt")));
        assertEquals(Files.readAllLines(Path.of("src/test/testData/ValidData.txt")),
                Files.readAllLines(testFolder.resolve("temp.txt")));
    }

    @Test
    public void saveNonExistentFile() {
        getTestData();
        Storage storage = Storage.INSTANCE;
        assertThrows(FinancialPlannerException.class, () -> storage.save(cashflowList, ""));
    }

    private void getTestData() {
        cashflowList.load(new Income(123.12, IncomeType.ALLOWANCE, 0));
        cashflowList.load(new Expense(100, ExpenseType.SHOPPING, 30));
    }
}
