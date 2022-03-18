package sharespot.services.datadecoder.infrastructure.persistence.filesystem.model;

import javax.persistence.*;

@Entity(name = "decoder")
public class DataDecoderPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String deviceType;

    @Column(columnDefinition="TEXT")
    public String script;
}
