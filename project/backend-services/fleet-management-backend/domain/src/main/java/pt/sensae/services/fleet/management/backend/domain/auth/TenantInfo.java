package pt.sensae.services.fleet.management.backend.domain.auth;

import java.util.List;
import java.util.UUID;

public class TenantInfo {
    public String email;
    public String name;
    public UUID oid;
    public List<String> domains;
    public List<String> permissions;

}
