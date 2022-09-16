import sql from "k6/x/sql";

export function moveDeviceToPublicDomain(device) {
  identityDB.exec(`DO $$
      DECLARE id integer;
      BEGIN
      INSERT INTO public.device (oid)
      VALUES ('${device.id}') RETURNING persistence_id INTO id;

      INSERT INTO public.device_permission (domain_oid, device_persistence_id)
      (SELECT public.domain.oid, id from public.domain where name = 'public');
      END $$;`);
}

export function givePermissionsToPublicDomain() {
  identityDB.exec(`DO $$
    DECLARE id integer;
    BEGIN
    SELECT public.domain.persistence_id into id from public.domain where name = 'public';

    INSERT INTO public.domain_permission (type, domain_persistence_id)
    VALUES (25,id), (26,id), (18,id);
    END $$;`);
}

export function resetIdentity() {
  identityDB.exec(`CREATE FUNCTION public.init_domains () 
  RETURNS varchar(255) AS $$
      DECLARE
          root_oid varchar(255) := gen_random_uuid();
          public_oid varchar(255) := gen_random_uuid();
          unallocated_oid varchar(255) := gen_random_uuid();
      BEGIN
          INSERT INTO public.domain (name, oid, path) VALUES ('root', root_oid, ARRAY[root_oid]);
          INSERT INTO public.domain (name, oid, path) VALUES ('public', public_oid, ARRAY[root_oid, public_oid]);
          INSERT INTO public.domain (name, oid, path) VALUES ('unallocated', unallocated_oid, ARRAY[root_oid, unallocated_oid]);
          INSERT INTO public.tenant (name, oid, phone_number, email, domains) VALUES ('Anonymous', gen_random_uuid(), '', '', ARRAY[public_oid]);
          INSERT INTO public.tenant (name, oid, phone_number, email, domains) VALUES ('Admin', gen_random_uuid(), '', 'filipe.cruz@sensae.pt', ARRAY[root_oid]);
          RETURN root_oid;
      END;
  $$ LANGUAGE plpgsql;
  
  select public.init_domains();
  
  DROP FUNCTION public.init_domains;`);
}

export function clearDomainsDevicesTenants() {
  identityDB.exec("TRUNCATE public.device CASCADE;");
  identityDB.exec("TRUNCATE public.tenant CASCADE;");
  identityDB.exec("TRUNCATE public.domain CASCADE;");
}

const identityDB = sql.open(
  "postgres",
  `postgres://user:${__ENV.SENSAE_COMMON_DATABASE_PASSWORD}@localhost:5486/identity?sslmode=disable`
);
