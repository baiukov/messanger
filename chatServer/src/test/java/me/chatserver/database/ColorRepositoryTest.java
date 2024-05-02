package me.chatserver.database;

import me.chatserver.DatabaseTestBase;
import me.chatserver.entities.Color;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

public class ColorRepositoryTest extends DatabaseTestBase {

    private final ColorRepository colorRepository = new ColorRepository();

    @Test
    public void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("dataSets/ColorSet.xml"));
        ITable expectedTable = expectedDataSet.getTable("COLORS");
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("COLORS");
        assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void shouldFindAvailableAmountOfColors() throws DataSetException {
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream("dataSets/ColorSet.xml"));
        ITable expectedTable = expectedDataSet.getTable("COLORS");
        assertEquals(expectedTable.getRowCount(), colorRepository.getAvailableAmount());
    }

    @Test
    public void givenColorIDShouldReturnColor() {
        Color expectedColor = new Color();
        expectedColor.setId(1);
        expectedColor.setName("Cotton candy");
        expectedColor.setHexcode("F2C4DE");

        Color actualColor = colorRepository.getColor(1);
        assertTrue(expectedColor.equals(actualColor));
    }
}
