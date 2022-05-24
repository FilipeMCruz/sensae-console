package pt.sensae.services.rule.management.domainservices.auth;

import java.util.List;
import java.util.UUID;

public class TenantInfo {
    public String email;
    public String name;
    public UUID oid;
    public List<String> domains;

    public List<String> parentDomains;
    public List<String> permissions;

}
