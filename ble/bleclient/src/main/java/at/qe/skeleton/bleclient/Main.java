package at.qe.skeleton.bleclient;

import com.google.common.io.ByteStreams;
import org.apache.commons.io.IOUtils;
import tinyb.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


// TODO: use logging instead of System.out/System.err

/**
 * Entry point for program to search for Bluetooth devices and communicate with them
 */
public final class Main {

    static boolean running = true;

    private Main() {    }

    /**
     * https://github.com/intel-iot-devkit/tinyb/blob/master/examples/java/HelloTinyB.java
     *
    */
    static BluetoothGattService getService(BluetoothDevice device, String UUID) throws InterruptedException {
        BluetoothGattService tempService = null;
        List<BluetoothGattService> bluetoothServices = null;

        do {
            bluetoothServices = device.getServices();
            if (bluetoothServices == null)
                return null;

            for (BluetoothGattService service : bluetoothServices) {
                if (service.getUUID().equals(UUID))
                    tempService = service;
            }
            Thread.sleep(4000);
        } while (bluetoothServices.isEmpty() && running);
        return tempService;
    }


    /**
     * https://github.com/intel-iot-devkit/tinyb/blob/master/examples/java/HelloTinyB.java
     *
     */
    static BluetoothGattCharacteristic getCharacteristic(BluetoothGattService service, String UUID) {
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        if (characteristics == null)
            return null;

        for (BluetoothGattCharacteristic characteristic : characteristics) {
            if (characteristic.getUUID().equals(UUID))
                return characteristic;
        }
        return null;
    }

    /**
     * This program should connect to TimeFlip devices and read the facet characteristic exposed by the devices
     * over Bluetooth Low Energy.
     *
     * @param args the program arguments
     * @throws InterruptedException if finding devices gets interrupted
     * @see <a href="https://github.com/DI-GROUP/TimeFlip.Docs/blob/master/Hardware/BLE_device_commutication_protocol_v3.0_en.md" target="_top">BLE device communication protocol v3.0</a>
     */
    public static void main(String[] args) throws InterruptedException {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        final String findDeviceName = "TimeFlip";
        final boolean discoveryStarted = manager.startDiscovery();
        System.out.println("The discovery started: " + (discoveryStarted ? "true" : "false"));

        FindDevicesManager findDevicesManager = new FindDevicesManager(findDeviceName);
        final boolean findDevicesSuccess = findDevicesManager.findDevices(manager);

        try {
            manager.stopDiscovery();
        } catch (BluetoothException e) {
            System.err.println("Discovery could not be stopped.");
        }

        System.out.println("All found devices:");
        manager.getDevices().forEach(d -> System.out.println(d.getAddress() + " - " + d.getName() + " (" + d.getRSSI() + ")"));

        if (!findDevicesSuccess) {
            System.err.println("No " + findDeviceName + " devices found during discovery.");
            System.exit(1);
        }

        Set<BluetoothDevice> foundDevices = findDevicesManager.getFoundDevices();
        System.out.println("Found " + foundDevices.size() + " " + findDeviceName + " device(s).");
        for (BluetoothDevice device : foundDevices) {
            System.out.println("Found " + findDeviceName + " device with address " + device.getAddress() + " and RSSI " +
                    device.getRSSI());

            if (device.connect()) {
                System.out.println("Connection established");
                System.out.println("Save Timeflip MacAddress in file, for later use, if it gets disconnected and we want to reconect");
                createFile(device.getAddress());
                // TODO: read from device
                System.out.println("Try to read out data from: " + device.getName());
                Lock lock = new ReentrantLock();
                lock.lock();
                readDataFromTimeflip(device);
                lock.unlock();
                device.disconnect();
            } else {
                System.out.println("Connection not established - trying next one");
            }
        }
    }

    private static void readDataFromTimeflip(BluetoothDevice device) throws InterruptedException {
        //set all services what we need
        BluetoothGattService timeflipService = getService(device, "f1196f50-71a4-11e6-bdf4-0800200c9a66");
        BluetoothGattService batteryService = getService(device, "0000180f-0000-1000-8000-00805f9b34fb");
        if(timeflipService == null ) {
            System.err.println("timeflip service is null");
            device.disconnect();
            System.exit(1);
        }
        if(batteryService == null ) {
            System.err.println("battery service is null");
            device.disconnect();
            System.exit(1);
        }
        //set password for current session, else we cannot read write
        setTimeflipPasswd(device, timeflipService);

        boolean set = timeflipStatus(device, timeflipService);
        System.out.println("status: " + getTimeflipCommandResultOutput(device, timeflipService));
        set = lockTimeflip(device, timeflipService);
        System.out.println("current facet: " + getCurrentTimeflipFacet(device, timeflipService));
        set = readHistory(device, timeflipService);
        System.out.println("current history: " + getTimeflipCommandResultOutput(device, timeflipService));
        set = timeflipStatus(device, timeflipService);
        System.out.println("status: " + getTimeflipCommandResultOutput(device, timeflipService));
        set = unlockTimeflip(device, timeflipService);
        set = timeflipStatus(device, timeflipService);
        System.out.println("status: " + getTimeflipCommandResultOutput(device, timeflipService));
    }

    private static void createFile(String address) {
        try {
            File myObj = new File("timeflip_mac_address.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                myObj.delete();
                System.out.println("File already exists delete and create new.");
                myObj = new File("timeflip_mac_address.txt");
            }
            FileWriter myWriter = new FileWriter("timeflip_mac_address.txt");
            myWriter.write(address);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void setTimeflipPasswd(BluetoothDevice device, BluetoothGattService timeflipService) {
        BluetoothGattCharacteristic passwdChar = getCharacteristic(timeflipService, "f1196f57-71a4-11e6-bdf4-0800200c9a66");
        if(passwdChar == null) {
            System.err.println("passwdChar is Null!");
            device.disconnect();
            System.exit(1);
        }
        byte[] passwd = "000000".getBytes();
        passwdChar.writeValue(passwd);
    }

    private static int getTimeflipBatteryStatus(BluetoothDevice device, BluetoothGattService batteryService) throws InterruptedException {
        BluetoothGattCharacteristic batteryChar = getCharacteristic(batteryService, "00002a19-0000-1000-8000-00805f9b34fb");
        if(batteryChar == null) {
            System.err.println("batteryChar is Null!");
            device.disconnect();
            System.exit(1);
        }
        byte[] batteryRaw = batteryChar.readValue();
        return batteryRaw[0];
    }

    private static int getCurrentTimeflipFacet(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        BluetoothGattCharacteristic facetChar = getCharacteristic(timeflipService, "f1196f52-71a4-11e6-bdf4-0800200c9a66");
        if(facetChar == null) {
            System.err.println("facetChar is Null!");
            device.disconnect();
            System.exit(1);
        }
        byte[] facetRaw = facetChar.readValue();
        return facetRaw[0];
    }
    /** List of possible commands
     *  0x01 history read out request
     *  0x02 delete history
     *  0x03 calibration reset, resets values for timeflip facets and resets char calibration version
     *  0x04 0x01 – lock function* on 0x04 0x02 – lock function off
     *  0x05 0xXX 0xXX – auto-pause** (off by default). 0xXX 0xXX – timer value in
     *  minutes after which the auto-pause is activated (0 = auto-pause off) Autopause timer is automatically reset when TimeFlip is flipped to another facet
     *  or on successful password write.
     *  0x06 0x01 - pause*** function on 0x06 0x02 – pause function off
     * */


    private static boolean setTimeflipCommand(BluetoothDevice device, BluetoothGattService timeflipService, byte[] command) throws InterruptedException {
        BluetoothGattCharacteristic commandChar = getCharacteristic(timeflipService, "f1196f54-71a4-11e6-bdf4-0800200c9a66");
        if(commandChar == null) {
            System.err.println("commandChar is Null!");
            device.disconnect();
            System.exit(1);
        }
        boolean writeSuccess = commandChar.writeValue(command);
        return writeSuccess;
    }

    private static String getTimeflipCommandResultOutput(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        BluetoothGattCharacteristic commandResultChar = getCharacteristic(timeflipService, "f1196f53-71a4-11e6-bdf4-0800200c9a66");
        if(commandResultChar == null) {
            System.err.println("commandResultChar is Null!");
            device.disconnect();
            System.exit(1);
        }

        byte[] history = commandResultChar.readValue();
        return Arrays.toString(history);
    }

    private static boolean timeflipStatus(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x10 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

    private static boolean resetTimeflipCalibration(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x03 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

    private static boolean lockTimeflip(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x04, 0x01 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

    private static boolean unlockTimeflip(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x04, 0x02 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

    private static boolean readHistory(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x01 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

    private static boolean deleteHistory(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x02 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

}
