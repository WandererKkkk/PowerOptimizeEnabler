package android.app.usage;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

public interface IUsageStatsManager extends IInterface {
    /**
     * Set app standby bucket
     * @param packageName target package
     * @param bucket bucket value (e.g., 20 for Working Set)
     * @param userId user id (usually 0)
     */
    void setAppStandbyBucket(String packageName, int bucket, int userId) throws RemoteException;

    abstract class Stub extends Binder implements IUsageStatsManager {
        public static IUsageStatsManager asInterface(IBinder obj) {
            throw new UnsupportedOperationException();
        }
    }
}