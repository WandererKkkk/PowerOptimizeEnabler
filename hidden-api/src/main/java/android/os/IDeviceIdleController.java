package android.os;

public interface IDeviceIdleController extends IInterface {
    /**
     * Remove package from power save whitelist
     */
    void removePowerSaveWhitelistApp(String packageName) throws RemoteException;

    abstract class Stub extends Binder implements IDeviceIdleController {
        public static IDeviceIdleController asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}