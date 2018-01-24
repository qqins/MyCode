package Scan;

import Util.Shell.ReadCSV;
import Util.Shell.ReadShell;

import java.io.File;
import java.util.*;

public class Scan {
    public static void main(String[] args) {
        ReadShell r_airodump = new ReadShell();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ReadShell r = new ReadShell();
                r.readCMD("rm -rf /root/znjj/cap");
                r.readCMD("rm -rf /root/znjj/result");
                r.readCMD("mkdir /root/znjj/cap");
                r.readCMD("mkdir /root/znjj/result");
//                r.readCMD("airmon-ng start wlan0");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r_airodump.readCMD("airodump-ng -c 3 -w /root/znjj/cap/c --bssid 2C:B2:1A:F2:3C:E2 wlan0mon");

            }
        });
        t1.start();
        t2.start();
//        File f = new File("/root/znjj/cap/c-01.csv");
        List<String> real_mac = Arrays.asList("AC:CF:23", "F0:FE:6B");
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag) {
                    if ((new File("/root/znjj/cap/c-01.csv").exists())) {
                        try {
                            Thread.sleep(15000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        flag = false;
                        r_airodump.destroy();
                        WriteCsvForScan wc = new WriteCsvForScan("/root/znjj/result/re.csv");
                        ReadCsvForScan rc = new ReadCsvForScan("/root/znjj/cap/c-01.csv");
                        List<String> mac = rc.readCsv(0);
                        List<String> packets = rc.readCsv(4);
//                        System.out.println(mac);
//                        System.out.println(packets);
                        ArrayList<Integer> in = new ArrayList<>();
                        for (int i = 0; i < real_mac.size(); i++) {
                            for (int j = 0; j < mac.size(); j++) {
                                if (mac.get(j).startsWith(real_mac.get(i))) {
                                    String[] str = {mac.get(j), "Y"};
                                    wc.write(str);
                                    in.add(j);
                                }
                            }
                        }

                        for (int i = 0; i < mac.size(); i++) {
                            boolean b = true;
                            for (int j = 0; j < in.size(); j++) {
                                if (i == in.get(j)) {
                                    b = false;
                                    break;
                                }
                            }
                            if (b) {
                                if (Integer.parseInt(packets.get(i)) < 1000) {
                                    String[] str = {mac.get(i), "Y"};
                                    wc.write(str);
                                } else {
                                    String[] str = {mac.get(i), "N"};
                                    wc.write(str);
                                }
                            }

                        }
                        wc.close();
                    }
                }
            }
        }).start();


    }
}
