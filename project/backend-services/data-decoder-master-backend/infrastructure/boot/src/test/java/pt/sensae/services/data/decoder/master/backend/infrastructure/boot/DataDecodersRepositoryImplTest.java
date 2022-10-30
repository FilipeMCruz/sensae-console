package pt.sensae.services.data.decoder.master.backend.infrastructure.boot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeId;
import pt.sensae.services.data.decoder.master.backend.domain.SensorTypeScript;
import pt.sensae.services.data.decoder.master.backend.infrastructure.persistence.postgres.DataDecodersRepositoryImpl;

import java.sql.SQLException;

public class DataDecodersRepositoryImplTest extends IntegrationTest {

    @Autowired
    DataDecodersRepositoryImpl repository;

    @AfterEach
    public void cleanUp() throws SQLException {
        performQuery("TRUNCATE decoder");
    }

    @Test
    public void ensureADecoderCanBeSaved() throws SQLException {
        var dataDecoder = new DataDecoder(SensorTypeId.of("lgt92"), SensorTypeScript.of("ascma"));
        var save = repository.save(dataDecoder);

        Assertions.assertEquals("lgt92", save.id().getValue());
        Assertions.assertEquals("ascma", save.script().value());

        var resultSet = performQuery("SELECT * FROM decoder WHERE device_type LIKE 'lgt92' AND script LIKE 'ascma'");
        Assertions.assertEquals("lgt92", resultSet.getString("device_type"));
        Assertions.assertEquals("ascma", resultSet.getString("script"));
        resultSet.close();
    }

    @Test
    public void ensureSavedDecoderCanBeFound() throws SQLException {
        performQuery("INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma')");

        var found = repository.findById(SensorTypeId.of("lgt92")).orElseThrow();

        Assertions.assertEquals("lgt92", found.id().getValue());
        Assertions.assertEquals("ascma", found.script().value());
    }

    @Test
    public void ensureUnknownDecoderCantBeFound() {
        var found = repository.findById(SensorTypeId.of("lgt92"));

        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    public void ensureADecoderCanBeUpdated() throws SQLException {
        var resultSetSt = performQuery("INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma') RETURNING *");
        Assertions.assertEquals("lgt92", resultSetSt.getString("device_type"));
        Assertions.assertEquals("ascma", resultSetSt.getString("script"));
        resultSetSt.close();

        var dataDecoder = new DataDecoder(SensorTypeId.of("lgt92"), SensorTypeScript.of("nonono"));
        var save = repository.save(dataDecoder);

        Assertions.assertEquals("lgt92", save.id().getValue());
        Assertions.assertEquals("nonono", save.script().value());

        var resultSet = performQuery("SELECT * FROM decoder WHERE device_type LIKE 'lgt92' AND script LIKE 'nonono'");
        Assertions.assertEquals("lgt92", resultSet.getString("device_type"));
        Assertions.assertEquals("nonono", resultSet.getString("script"));
        resultSet.close();
    }

    @Test
    public void ensureSavedDecodersCanBeCollected() throws SQLException {
        performQuery("INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma'),('emd300th', 'lololol')");

        var found = repository.findAll().toList();
        Assertions.assertEquals(2, found.size());

        Assertions.assertTrue(found.stream().anyMatch(s -> s.id().getValue().equals("lgt92")));
        Assertions.assertEquals("ascma", found.stream()
                .filter(s -> s.id().getValue().equals("lgt92"))
                .findFirst()
                .orElseThrow()
                .script()
                .value());

        Assertions.assertTrue(found.stream().anyMatch(s -> s.id().getValue().equals("emd300th")));
        Assertions.assertEquals("lololol", found.stream()
                .filter(s -> s.id().getValue().equals("emd300th"))
                .findFirst()
                .orElseThrow()
                .script()
                .value());
    }

    @Test
    public void ensureSavedDecoderCanBeDeleted() throws SQLException {
        performQuery("INSERT INTO decoder(device_type, script) VALUES ('lgt92', 'ascma')");

        var found = repository.delete(SensorTypeId.of("lgt92"));

        Assertions.assertEquals("lgt92", found.getValue());

        var resultSet = performQuery("SELECT * FROM decoder WHERE device_type LIKE 'lgt92'");
        Assertions.assertFalse(resultSet.next());
        resultSet.close();
    }
}
