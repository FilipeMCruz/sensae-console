package sharespot.services.identitymanagementbackend.domain.identity.device;

/**
 * In the domains a device should appear at least twice,
 * once with READ_WRITE permissions and another time with READ permissions
 * This permissions describe what domains can read or read/write device properties.
 * lvl 1 - A
 * lvl 2 - A/B       A/C
 * lvl 3 - A/B/D A/B/E     A/C/F A/C/G
 * <p>
 * In the following domain tree, if the B domain can read/write device X so can A, but the others cant.
 * If this is the only permission, no one can even see device X, apart from A and B.
 * <p>
 * If the A domain can read/write device X and B and F can read device X
 * it means that only A can read/write, A,B,C and F can read teh device.
 * <p>
 * A read permission can 't be placed higher than a read/write permission.
 * <p>
 * If the B domain can read device X D and E can have read/write permissions.
 */
public enum DevicePermissions {
    READ,
    READ_WRITE,
}
