package pt.sensae.services.data.decoder.master.backend.domainservices;

import org.springframework.stereotype.Service;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecoder;
import pt.sensae.services.data.decoder.master.backend.domain.DataDecodersRepository;

@Service
public class DataDecoderHoarder {

    private final DataDecodersRepository repository;

    public DataDecoderHoarder(DataDecodersRepository repository) {
        this.repository = repository;
    }

    public DataDecoder hoard(DataDecoder records) {
        return this.repository.save(records);
    }
}
