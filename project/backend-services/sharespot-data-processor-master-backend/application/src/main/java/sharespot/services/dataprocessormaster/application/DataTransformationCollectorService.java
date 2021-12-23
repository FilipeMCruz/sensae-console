package sharespot.services.dataprocessormaster.application;

import org.springframework.stereotype.Service;
import sharespot.services.dataprocessormaster.domainservices.DataTransformationCollector;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataTransformationCollectorService {

    private final DataTransformationCollector collector;

    private final DataTransformationMapper mapper;

    public DataTransformationCollectorService(DataTransformationCollector collector, DataTransformationMapper mapper) {
        this.collector = collector;
        this.mapper = mapper;
    }

    public Set<DataTransformationDTO> transformations() {
        return collector.collect()
                .stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toSet());
    }
}
