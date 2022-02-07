package sharespot.services.fleetmanagementbackend.domain.model.livedata;

import java.util.Objects;

public record StatusDataDetails(String motion) {

    public boolean sameSegment(StatusDataDetails other) {
        if (Objects.equals(motion, "ACTIVE") && Objects.equals(other.motion, "UNKNOWN")) {
            return true;
        }
        if (Objects.equals(motion, "UNKNOWN") && Objects.equals(other.motion, "ACTIVE")) {
            return true;
        }
        return Objects.equals(motion, other.motion());
    }
}
