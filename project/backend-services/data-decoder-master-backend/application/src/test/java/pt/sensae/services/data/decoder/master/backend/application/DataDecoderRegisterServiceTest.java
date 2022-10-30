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
import pt.sensae.services.data.decoder.master.backend.application.helpers.FakeDataDecoderDTO;
import pt.sensae.services.data.decoder.master.backend.domain.LastTimeSeenDecoderRepository;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderHoarder;

import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DataDecoderRegisterServiceTest {

    @Mock
    DataDecoderHoarder hoarder;

    @Mock
    TokenExtractor tokenExtractor;

    @Mock
    DataDecoderMapper mapper;

    @Mock
    LastTimeSeenDecoderRepository repository;

    @Mock
    DataDecoderHandlerService publisher;

    @InjectMocks
    DataDecoderRegisterService service;

    @Test
    public void ensureServiceFailsWhenUserHasNoPermissions() {

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.invalidTenantInfo());

        Assertions.assertThrows(UnauthorizedException.class,
                () -> service.register(new FakeDataDecoderDTO(), new FakeAccessTokenDTO()),
                Mockito.eq("No Permissions"));

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verifyNoInteractions(hoarder);
        Mockito.verifyNoInteractions(mapper);
        Mockito.verifyNoInteractions(publisher);
    }

    @Test
    public void ensureServiceWorksWhenUserHasPermissionsAndDecoderWasNeverUsed() {

        var dataDecoder = CommonObjectsFactory.dataDecoder();

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.validTenantInfo());

        Mockito.when(mapper.dtoToDomain(Mockito.any(DataDecoderDTO.class))).thenReturn(dataDecoder);

        Mockito.when(hoarder.hoard(dataDecoder)).thenReturn(dataDecoder);

        service.register(new FakeDataDecoderDTO(), new FakeAccessTokenDTO());

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verify(mapper, Mockito.times(1)).dtoToDomain(Mockito.any(DataDecoderDTO.class));
        Mockito.verify(hoarder, Mockito.times(1)).hoard(dataDecoder);
        Mockito.verify(publisher, Mockito.times(1)).publishUpdate(dataDecoder);
        Mockito.verify(mapper, Mockito.times(1)).domainToDto(dataDecoder, 0L);
    }

    @Test
    public void ensureServiceWorksWhenUserHasPermissionsAndDecoderWasUsed() {

        var dataDecoder = CommonObjectsFactory.dataDecoder();

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.validTenantInfo());

        Mockito.when(mapper.dtoToDomain(Mockito.any(DataDecoderDTO.class))).thenReturn(dataDecoder);

        Mockito.when(hoarder.hoard(Mockito.any())).thenReturn(dataDecoder);

        Mockito.when(repository.find(dataDecoder.id()))
                .thenReturn(Optional.of(Instant.ofEpochMilli(1_659_810_451_018L)));

        service.register(new FakeDataDecoderDTO(), new FakeAccessTokenDTO());

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verify(mapper, Mockito.times(1)).dtoToDomain(Mockito.any(DataDecoderDTO.class));
        Mockito.verify(hoarder, Mockito.times(1)).hoard(dataDecoder);
        Mockito.verify(publisher, Mockito.times(1)).publishUpdate(dataDecoder);
        Mockito.verify(mapper, Mockito.times(1)).domainToDto(dataDecoder, 1_659_810_451_018L);
    }
}
