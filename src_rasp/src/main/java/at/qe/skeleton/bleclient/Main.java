package at.qe.skeleton.bleclient;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import tinyb.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ConnectedNotification implements BluetoothNotification<Boolean> {
    public void run(Boolean connected) {
        System.out.println("Connected");
    }
}

class ValueNotification implements BluetoothNotification<byte[]> {

    public List<String> getApiAndIp() {
        String ipAddress = "";
        String apiKey = "";
        String backend = "";
        RESTClient client;
        String fileName = "/config.json";
        File tmpDir = new File("./config.json");
        List<String> tmp = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(tmpDir);
            org.json.simple.parser.JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
            ipAddress = jsonObject.get("ipAddress").toString();
            apiKey = jsonObject.get("apiKey").toString();
            backend = jsonObject.get("backend").toString();
            if(backend == null || backend.equals("")) {
                System.out.println("Server address missing in config file");
                System.exit(1);
            }
            tmp.add(ipAddress);
            tmp.add(apiKey);
            tmp.add(backend);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error with configuration");
            System.exit(1);
        }
        return tmp;
    }

    public void run(byte[] tempRaw) {
        int i = tempRaw[0];
        if(i>0 && i<13) {
        List<String> tmp = getApiAndIp();
        String ipAddress = tmp.get(0);
        String apiKey = tmp.get(1);
        String backend = tmp.get(2);
        RESTClient client = new RESTClient("http://"+backend+":8080",apiKey);
        PiRequest piRequest = new PiRequest();
        piRequest.setIpAddress(ipAddress);
        piRequest.setFacetId(i);
        client.updateTimeflip(piRequest);
        System.out.print(tempRaw[0]);
        }
    }
}

public class Main {

    static boolean running = true;
    static boolean notify = false;

    public static void main(String[] args) throws InterruptedException, URISyntaxException {

        String ipAddress = "";
        String apiKey = "";
        RESTClient client;
        String fileName = "/config.json";

        Path path = null;



        File tmpDir = new File("./config.json");
        try {
            InputStream inputStream = new FileInputStream(tmpDir);
            org.json.simple.parser.JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
            ipAddress = jsonObject.get("ipAddress").toString();
            apiKey = jsonObject.get("apiKey").toString();

            if(ipAddress == null || ipAddress.equals("")) {
                System.out.println("Server address missing in config file");
                System.exit(1);
            }
        } catch (Exception ex) {
            System.out.println("Error with configuration");
            System.exit(1);
        }

        if(apiKey.equals("")){
            System.out.println("Requesting Key from Server");
            client = new RESTClient("http://localhost:8080");
            apiKey = client.getApiKey(ipAddress);
            client.setApiKey(apiKey);
        } else {
            client = new RESTClient("http://localhost:8080",apiKey);
        }


        BluetoothDevice device = connectTimeFlip();
        if (device.connect()) {
            System.out.println("Connection established");
            try {
                BluetoothGattService timeflipService = getService(device, "f1196f50-71a4-11e6-bdf4-0800200c9a66");
                BluetoothGattService batteryService = getService(device, "0000180f-0000-1000-8000-00805f9b34fb");
                setTimeflipPasswd(device, timeflipService);
                Thread.sleep(4000);

                System.out.println("DO YOU WANT TO CALIBRATE SET UP THE DICE?\n" +
                        "A DICE SHOULD BE ALWAYS NEW CALIBRATED AFTER YOU REMOVED THE BATTERY!\n" +
                        "DO YOU WANT TO CALIBRATE AND SET UP THE DICE? " +
                        "IF YOU PLAY WITH A NOT CALIBRATED DICE, " +
                        "IT CAN HAPPEN THAT ITS NOT WORKING AS EXPECTED!?\n" +
                        "write YES, if you want to calibrate, NO if you want to skip!");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String shouldCalibrate = reader.readLine();

                if(shouldCalibrate.equals("YES") || shouldCalibrate.equals("yes")) {
                    System.out.println("######TIMEFLIP-FACET-CONFIG######");
                    System.out.println("ITS NECESSARY THAT YOUR DICE IS MARKED UP AS IN OUR GIVEN EXAMPLE JPEG " +
                            "- WHERE DO YOU FIND THIS .../src_rasp/diceNumbering.jpeg, WE JUST OFFER 12 NUMBERS FOR" +
                            " THE DICE!");
                    System.out.println("IN THE FOLLOWING 12 REQUESTS - HIT ENTER TO SAVE AND PROCESS THE NEXT NUMBER!");
                    for (int i = 1; i < 13; i++) {
                        System.out.println("IF YOU ARE READY - ROLL THE DICE ON SIDE " + i + " and enter to proceed!");
                        shouldCalibrate = reader.readLine();
                        System.out.println("current facet = " + getCurrentTimeflipFacet(device,timeflipService));
                        Thread.sleep(3);
                    }
                    System.out.println("YOU SUCCESSFULLY SET UP THE DICE!");
                }
                System.out.println("##################INIT GAME MODE##################\n" +
                        " FROM NOW ON IF YOU SET UP THE .../src_rasp/CONFIG.JSON " +
                        "CORRECTLY, THE TIMEFLIP COMMUNICATE WITH THE RASPBERRY AND THE RASPBERRY" +
                        " SHOULD NOW COMMUNICATE WITH THE WEBAPP IN YOUR BROWSER! ");
                device.enableConnectedNotifications(new ConnectedNotification());
                notifyFacet(device);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                device.disconnect();
            }
        } else {
            System.out.println("Connection not established - try it again");
        }
    }

    private static void notifyFacet(BluetoothDevice device) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition cv = lock.newCondition();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                running = false;
                lock.lock();
                try {
                    cv.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        });
        BluetoothGattService timeflipService = getService(device, "f1196f50-71a4-11e6-bdf4-0800200c9a66");
        if(timeflipService == null ) {
            System.err.println("timeflip service is null");
            device.disconnect();
            System.exit(1);
        }
        setTimeflipPasswd(device, timeflipService);
        notifyFacet(device, timeflipService);
        BluetoothGattCharacteristic facetChar = getCharacteristic(timeflipService, "f1196f52-71a4-11e6-bdf4-0800200c9a66");
        if(facetChar == null) {
            System.err.println("facetChar is Null!");
            device.disconnect();
            System.exit(1);
        }
        facetChar.enableValueNotifications(new ValueNotification());
        lock.lock();
        try {
            while(running)
                cv.await();
        } finally {
            lock.unlock();
        }
    }

    private static boolean notifyFacet(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x10 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

    private static boolean timeflipStatus(BluetoothDevice device, BluetoothGattService timeflipService) throws InterruptedException {
        byte[] command = { 0x10 };
        boolean setCommand = setTimeflipCommand(device, timeflipService, command);
        if(setCommand)
            return true;
        return false;
    }

    public static Map<String,String> readDataFromFile(Path path) throws Exception {
        Map<String,String> data;
        System.out.println(path.toString());
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(path);

            // convert JSON file to map and read value
            data = gson.fromJson(reader, Map.class);

            // close reader
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
        return data;
    }

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

    public static BluetoothDevice connectTimeFlip() throws InterruptedException {
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
        return foundDevices.iterator().next();
    }

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
}
