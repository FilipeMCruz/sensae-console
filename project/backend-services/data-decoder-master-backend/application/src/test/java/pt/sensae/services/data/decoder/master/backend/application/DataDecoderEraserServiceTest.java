package pt.sensae.services.data.decoder.master.backend.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.sensae.services.data.decoder.master.backend.application.auth.AccessTokenDTO;
import pt.sensae.services.data.decoder.master.backend.application.auth.TokenExtractor;
import pt.sensae.services.data.decoder.master.backend.application.auth.UnauthorizedException;
import pt.sensae.services.data.decoder.master.backend.application.helpers.CommonObjectsFactory;
import pt.sensae.services.data.decoder.master.backend.application.helpers.FakeAccessTokenDTO;
import pt.sensae.services.data.decoder.master.backend.application.helpers.FakeSensorTypeIdDTO;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderEraser;

@ExtendWith(MockitoExtension.class)
public class DataDecoderEraserServiceTest {

    @Mock
    DataDecoderEraser eraser;

    @Mock
    TokenExtractor tokenExtractor;

    @Mock
    DataDecoderMapper mapper;

    @Mock
    DataDecoderHandlerService publisher;

    @InjectMocks
    DataDecoderEraserService service;

    @Test
    public void ensureServiceFailsWhenUserHasNoPermissions() {

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.invalidTenantInfo());

        Assertions.assertThrows(UnauthorizedException.class,
                () -> service.erase(new FakeSensorTypeIdDTO(), new FakeAccessTokenDTO()),
                Mockito.eq("No Permissions"));

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verifyNoInteractions(eraser);
        Mockito.verifyNoInteractions(mapper);
        Mockito.verifyNoInteractions(publisher);
    }

    @Test
    public void ensureServiceWorksWhenUserHasPermissionsAndDecoderWasNeverUsed() {

        var sensorTypeId = CommonObjectsFactory.sensorTypeId();

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.validTenantInfo());

        Mockito.when(mapper.dtoToDomain(Mockito.any(SensorTypeIdDTO.class))).thenReturn(sensorTypeId);

        Mockito.when(eraser.erase(sensorTypeId)).thenReturn(sensorTypeId);

        service.erase(new FakeSensorTypeIdDTO(), new FakeAccessTokenDTO());

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verify(mapper, Mockito.times(1)).dtoToDomain(Mockito.any(SensorTypeIdDTO.class));
        Mockito.verify(eraser, Mockito.times(1)).erase(sensorTypeId);
        Mockito.verify(publisher, Mockito.times(1)).publishDelete(sensorTypeId);
        Mockito.verify(mapper, Mockito.times(1)).domainToDto(sensorTypeId);
    }
}
