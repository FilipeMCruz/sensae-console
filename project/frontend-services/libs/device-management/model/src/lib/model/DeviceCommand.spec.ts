import {DeviceCommand} from "./DeviceCommand";

describe('Device Command Unit Test', () => {
  it('should be clone every single parameter', () => {
    const deviceCommand = new DeviceCommand('openValve', 'openValve', 'ldcnsldnc', 0, 70);
    const clone = deviceCommand.clone();
    expect(clone.id).toBe(deviceCommand.id);
    expect(clone.name).toBe(deviceCommand.name);
    expect(clone.ref).toBe(deviceCommand.ref);
    expect(clone.payload).toBe(deviceCommand.payload);
    expect(clone.port).toBe(deviceCommand.port);
  });
  it('should be invalid when it has no id', () => {
    const deviceCommand = new DeviceCommand('', 'openValve', 'ldcnsldnc', 0, 70);
    expect(deviceCommand.isValid()).toBeFalsy();
  });
  it('should be invalid when it has no name', () => {
    const deviceCommand = new DeviceCommand('openValve', '', 'ldcnsldnc', 0, 70);
    expect(deviceCommand.isValid()).toBeFalsy();
  });
  it('should be invalid when it has no payload', () => {
    const deviceCommand = new DeviceCommand('openValve', 'openValve', '', 0, 70);
    expect(deviceCommand.isValid()).toBeFalsy();
  });
  it('should be invalid when the port is a negative number', () => {
    const deviceCommand = new DeviceCommand('openValve', 'openValve', 'lnsdlns', 0, -70);
    expect(deviceCommand.isValid()).toBeFalsy();
  });
  it('should be invalid when the ref is a negative number', () => {
    const deviceCommand = new DeviceCommand('openValve', 'openValve', 'lnsdlns', -3, 70);
    expect(deviceCommand.isValid()).toBeFalsy();
  });
  it('should be valid', () => {
    const deviceCommand = new DeviceCommand('openValve', 'openValve', 'lnsdlns', 0, 70);
    expect(deviceCommand.isValid()).toBeTruthy();
  });
});
