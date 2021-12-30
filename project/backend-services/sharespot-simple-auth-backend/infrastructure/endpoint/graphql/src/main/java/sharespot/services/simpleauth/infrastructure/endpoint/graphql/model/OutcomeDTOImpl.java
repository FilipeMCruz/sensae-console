package sharespot.services.simpleauth.infrastructure.endpoint.graphql.model;

public class OutcomeDTOImpl {
    
    public boolean valid;

    public static OutcomeDTOImpl of(boolean value) {
        var outcomeDTO = new OutcomeDTOImpl();
        outcomeDTO.valid = value;
        return outcomeDTO;
    }
}
