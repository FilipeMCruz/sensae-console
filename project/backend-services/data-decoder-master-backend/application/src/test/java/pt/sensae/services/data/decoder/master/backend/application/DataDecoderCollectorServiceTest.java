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
import pt.sensae.services.data.decoder.master.backend.domain.LastTimeSeenDecoderRepository;
import pt.sensae.services.data.decoder.master.backend.domainservices.DataDecoderCollector;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DataDecoderCollectorServiceTest {

    @Mock
    DataDecoderCollector collector;

    @Mock
    DataDecoderMapper mapper;

    @Mock
    TokenExtractor tokenExtractor;

    @Mock
    LastTimeSeenDecoderRepository repository;

    @InjectMocks
    DataDecoderCollectorService service;


    @Test
    public void ensureServiceFailsWhenUserHasNoPermissions() {

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.invalidTenantInfo());

        Assertions.assertThrows(UnauthorizedException.class,
                () -> service.collectAll(new FakeAccessTokenDTO()),
                Mockito.eq("No Permissions"));

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verifyNoInteractions(collector);
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    public void ensureServiceWorksWhenUserHasPermissionsAndDecoderWasNeverUsed() {

        var dataDecoder = CommonObjectsFactory.dataDecoder();

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.validTenantInfo());

        Mockito.when(collector.collect()).thenReturn(Stream.of(dataDecoder));

        var dataDecoderDTOStream = service.collectAll(new FakeAccessTokenDTO()).toList();

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verify(collector, Mockito.times(1)).collect();
        Mockito.verify(mapper, Mockito.times(1)).domainToDto(dataDecoder, 0L);

        Assertions.assertEquals(dataDecoderDTOStream.size(), 1);
    }

    @Test
    public void ensureServiceWorksWhenUserHasPermissionsAndDecoderWasUsed() {

        var dataDecoder = CommonObjectsFactory.dataDecoder();

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.validTenantInfo());

        Mockito.when(collector.collect()).thenReturn(Stream.of(dataDecoder));

        Mockito.when(repository.find(dataDecoder.id()))
                .thenReturn(Optional.of(Instant.ofEpochMilli(1_659_810_451_018L)));

        var dataDecoderDTOStream = service.collectAll(new FakeAccessTokenDTO()).toList();

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verify(collector, Mockito.times(1)).collect();
        Mockito.verify(mapper, Mockito.times(1)).domainToDto(dataDecoder, 1_659_810_451_018L);

        Assertions.assertEquals(dataDecoderDTOStream.size(), 1);
    }

    @Test
    public void ensureServiceWorksWhenUserHasPermissionsAndFirstDecoderWasUsedAndSecondWasNot() {

        var dataDecoder = CommonObjectsFactory.dataDecoder();
        var otherDataDecoder = CommonObjectsFactory.otherDataDecoder();

        Mockito.when(tokenExtractor.extract(Mockito.any(AccessTokenDTO.class)))
                .thenReturn(CommonObjectsFactory.validTenantInfo());

        Mockito.when(collector.collect()).thenReturn(Stream.of(dataDecoder, otherDataDecoder));

        Mockito.when(repository.find(dataDecoder.id()))
                .thenReturn(Optional.of(Instant.ofEpochMilli(1_659_810_451_018L)));

        var dataDecoderDTOStream = service.collectAll(new FakeAccessTokenDTO()).toList();

        Mockito.verify(tokenExtractor, Mockito.times(1)).extract(Mockito.any(AccessTokenDTO.class));
        Mockito.verify(collector, Mockito.times(1)).collect();
        Mockito.verify(mapper, Mockito.times(1)).domainToDto(dataDecoder, 1_659_810_451_018L);
        Mockito.verify(mapper, Mockito.times(1)).domainToDto(otherDataDecoder, 0L);

        Assertions.assertEquals(dataDecoderDTOStream.size(), 2);
    }
}
