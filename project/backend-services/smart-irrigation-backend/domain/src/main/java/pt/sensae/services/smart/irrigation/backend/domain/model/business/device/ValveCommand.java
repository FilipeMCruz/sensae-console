package pt.sensae.services.smart.irrigation.backend.domain.model.business.device;

import pt.sensae.services.smart.irrigation.backend.domain.model.data.payload.ValveStatusType;

import java.util.Objects;
import java.util.Optional;

public enum ValveCommand {
    CLOSE_VALVE {
        @Override
        public String commandId() {
            return "closeValve";
        }
    },
    OPEN_VALVE {
        @Override
        public String commandId() {
            return "openValve";
        }
    };

    public String commandId() {
        return "";
    }

    public static Optional<ValveCommand> from(String id) {
        if (Objects.equals(id, OPEN_VALVE.commandId())) {
            return Optional.of(OPEN_VALVE);
        } else if (Objects.equals(id, CLOSE_VALVE.commandId())) {
            return Optional.of(CLOSE_VALVE);
        } else {
            return Optional.empty();
        }
    }

    public static ValveCommand from(ValveStatusType type) {
        if (type == ValveStatusType.OPEN) {
            return OPEN_VALVE;
        } else {
            return CLOSE_VALVE;
        }
    }
}
