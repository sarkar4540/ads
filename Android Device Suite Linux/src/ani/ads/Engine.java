/*
 * Copyright 2016 Aniruddha Sarkar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ani.ads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *The main engine of the Project ( handles most common adb transactions)
 * @author ani
 */
public class Engine {
/**
 * The log Text
 */
public String logtxt="";
/**
 * The executable of adb
 */
public String adb,dev;
    
    /**
     * The constructor of the Engine
     * 
     */
    public Engine(){
        /*System.setErr(new PrintStream(new OutputStream() {
            
            @Override
            public void write(int b) throws IOException {
                logtxt+=(char)b; //To change body of generated methods, choose Tools | Templates.
            }
        }));
        
        System.setOut(new PrintStream(new OutputStream() {
            
            @Override
            public void write(int b) throws IOException {
                logtxt+=(char)b; //To change body of generated methods, choose Tools | Templates.
            }
        }));
        */
        setup();
        File f=new File(ush+"/.ads/enginelog.txt");
        if(f.exists())f.delete();
        //JOptionPane.showMessageDialog(null, "log build started...");
            try (OutputStream fos = new FileOutputStream(f)) {
                fos.write(logtxt.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        //JOptionPane.showMessageDialog(null, "log build doned...");
    
    }
    
    /**
     * Sets up the requirements and installs drivers
     * @param devf
     */
    public void setdev(String devf){
        dev=devf;
    }
    public final void setup(){
        File fdir=new File(ush+"/.ads"),cdir=new File(ush+"/.ads/cache");
        if(!fdir.exists())if(!fdir.mkdirs()){
            JOptionPane.showMessageDialog(null, "Fatal Error 0xBC1", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if(!cdir.exists())if(!cdir.mkdirs()){
            JOptionPane.showMessageDialog(null, "Fatal Error 0xBC2", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if(new File(ush+"/.ads/ads.prop").exists()){
            try {
                Properties p=new Properties();
                p.load(new FileInputStream(ush+"/.ads/ads.prop"));
                adb=p.getProperty("adb.location");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            
            String sel=""+JOptionPane.showInputDialog(null, "Select your Operating System:", "Android Device Suite", -1, null, new String[]{"Debian (or derivatives like Ubuntu)","Other Linux Distro","Windows","Other"}, this);
            
            if(sel.startsWith("Debian")){
                Thread t=new Thread(() -> {
                    FileOutputStream fis;
                    InputStream is;
                    try {
                        String ske=runNoEx("adb");
                        if(!(ske.trim().toLowerCase().contains("android"))){
                            /*JOptionPane.showMessageDialog(null, "<html><body><p>Please open a Package Manager and Install:<ol><li> android-tools-adb</li><li> android-tools-fastboot</li></ol></p><p align=center><b>OR</b></p><p>Run \n" +
                            "sudo apt-get install android-tools-adb android-tools-fastboot in command line</p></body></html>");
                            */
                            if(JOptionPane.showConfirmDialog(null, "Would you like to install the necessary packages?")==0){
                                TextViewer vw=new TextViewer("Installing requirements","Installing the requirements:");
                                new Thread(()->{
                                    vw.setDefaultCloseOperation(TextViewer.DO_NOTHING_ON_CLOSE);
                                    vw.setVisible(true);
                                }).start();
                                    try {
                                        URL urcheck=new URL("http://www.google.com");
                                        urcheck.openStream();
                                        Process p=Runtime.getRuntime().exec(new String[]{"gksudo","apt-get install -y android-tools-adb android-tools-fastboot"});
                                        InputStream reqis=p.getInputStream(),reqes=p.getErrorStream();
                                        int a,b;
                                        do{
                                            a=reqis.read();
                                            if(a>-1)vw.append(a);
                                            b=reqes.read();
                                            if(b>-1)vw.append(b);
                                            
                                        }while(p.isAlive()||a>-1||b>-1);
                                        vw.dispose();
                                    } catch (IOException ex) {
                                        vw.dispose();
                                        JOptionPane.showMessageDialog(null, "No internet Connection!", "Error", JOptionPane.ERROR_MESSAGE);
                                        System.exit(-1);
                                        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            }else{
                                System.exit(-1);
                            }
                        }
                        JOptionPane.showMessageDialog(null,"Starting to configure your system... Please click OK to Continue");
                        
                        is=getClass().getResourceAsStream("/ani/ads/tools/ubuntu.sh");
                        
                        fis = new FileOutputStream(ush+"/.ads/ubuntu.sh");
                        int x;
                        do{
                            x=is.read();
                            if(x>-1)fis.write(x);
                        }while(x>-1);
                        is.close();
                        fis.close();
                        String s=runNoEx("gksudo","sh",ush+"/.ads/ubuntu.sh");
                        is=getClass().getResourceAsStream("/ani/ads/tools/adbd-insecure.apk");
                        
                        fis = new FileOutputStream(ush+"/.ads/adbd-insecure.apk");
                        do{
                            x=is.read();
                            if(x>-1)fis.write(x);
                        }while(x>-1);
                        is.close();
                        fis.close();
                        adb="adb";
                        writeProps();
                        s=s.replaceAll("\n", "<br/>");
                        JOptionPane.showMessageDialog(null,"<html><body>"+s+"</html></body>");
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        
                        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                t.start();
            }
            else if(sel.startsWith("Other Linux")){
                try {
                    String ske=run("adb");
                    if(!ske.toLowerCase().contains("android")){
                        JOptionPane.showMessageDialog(null, "<html><body><p>Please open a Package Manager and Install:<ol><li> android-tools-adb</li><li> android-tools-fastboot</li></ol> and Restart</p></body></html>");
                        System.exit(1);
                    }
                    else {
                        adb="adb";
                        writeProps();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //JOptionPane.showMessageDialog(null, "Engine build");
    }
    /**
    * Writes the required data for each session.
    */
    private void writeProps(){
        try {
            Properties p=new Properties();
            System.out.println("adb:" +adb);
            p.setProperty("adb.location", adb);
            p.store(new FileOutputStream(ush+"/.ads/ads.prop"),null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void clearDev(){
        dev=null;
    }
    /**
     * Runs the commands as processes
    * @param cmd The commands to be run.
    * @return The Output of the process.
     * @throws java.io.IOException If I/O Error Occurs by Process
    */
    public String run(String... cmd) throws IOException{
            String out="";
                String[] cmds;
                if(dev==null)cmds=cmd;
                else{
                    cmds=new String[cmd.length+2];
                
                    for(int i=0;i<cmds.length;i++){
                        if(i==0){
                            cmds[i]=cmd[i];
                        }
                        else if(i==1){
                            cmds[i]="-s";
                        }
                        else if(i==2){
                            cmds[i]=dev;
                        }
                        else{
                           cmds[i]=cmd[i-2];
                        }
                    }
                }
                Process p=new ProcessBuilder(cmds).start();
                InputStream is=p.getInputStream();
                InputStream es=p.getErrorStream();
                int a,b;
                do{
                    a=is.read();
                    b=es.read();
                    if(a>-1)out+=(char)a;
                    if(b>-1)System.err.print((char)b);
                }while(a>-1||b>-1||p.isAlive());
                System.out.println("Run -> ("+Arrays.toString(cmds)+") ->"+out);
                if(p.exitValue()!=0)throw new IOException("Exit value not zero"+out);
        return out;
    }
    public String runNoEx(String... cmd){
       
        String out="";
    try {
        
        Process p=new ProcessBuilder(cmd).start();
        InputStream is=p.getInputStream();
        InputStream es=p.getErrorStream();
        int a,b;
        do{
            a=is.read();
            b=es.read();
            if(a>-1)out+=(char)a;
            if(b>-1){
                out+=(char)b;
                System.err.print((char)b);
            }
        }while(a>-1||b>-1||p.isAlive());
        System.out.println("Run -> ("+Arrays.toString(cmd)+") ->"+out);
        //if(p.exitValue()!=0)throw new IOException("Exit value not zero"+out);
    } catch (IOException ex) {
        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
    }
        return out;
    }
    
    
    
    public boolean rootCheckADB() throws IOException{
            String out="";
            try {
                Process p=new ProcessBuilder(new String[]{adb,"-s",dev,"root"}).start();
                InputStream is=p.getInputStream();
                InputStream es=p.getErrorStream();
                int a,b;
                do{
                    a=is.read();
                    b=es.read();
                    if(a>-1)out+=(char)a;
                    if(b>-1)out+=(char)b;
                }while(a>-1||b>-1);
                p.destroy();
                out=out.trim().toLowerCase();
                return out.endsWith("root");
            } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
    }
    public boolean rootCheck() throws IOException{
            String out="";
            try {
                Process p=new ProcessBuilder(new String[]{adb,"-s",dev,"shell","su -c echo hello"}).start();
                InputStream is=p.getInputStream();
                InputStream es=p.getErrorStream();
                int a,b;
                do{
                    a=is.read();
                    b=es.read();
                    if(a>-1)out+=(char)a;
                    if(b>-1)out+=(char)b;
                }while(a>-1||b>-1);
                p.destroy();
                out=out.trim().toLowerCase();
                return out.endsWith("hello");
            } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
    }
    
    /**
    * Fetches the Properties of the device.
    * @param id the id of the property.
     * 
    * @return the value of property
    * @throws java.io.IOException If I/O Error Occurs by Process
    */
    public String getprop(String id) throws IOException{
        return run(adb,"shell","getprop",id);
    }
    /**
     * Captures the screenshot of the device's screen
     * 
     * @return the path to the file where screenshot is located
     */
    public String getShot(){
        String out=ush+"/.ads/screen.png";
            try {
                run(adb,"shell","mkdir","/sdcard/.ads/");
                run(adb,"shell","screencap","-p","/sdcard/.ads/screencap.png");
                run(adb,"pull","/sdcard/.ads/screencap.png",out);
                //System.out.println(run(adb,"shell","rm","/sdcard/.ads/screencap.png"));
            } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        return out;
    }
    /**
     * Fetches the android version of the device
     * 
     * @return the android version of the device
     * @throws java.io.IOException If I/O Error Occurs by Process
     */
    public String getAndroid() throws IOException{
        String os=getprop("ro.build.version.release"),osn=os;
            if(os.startsWith("2.2"))osn="Froyo";
            else if(os.startsWith("2.3"))osn="Gingerbread";
            else if(os.startsWith("3"))osn="HoneyComb";
            else if(os.startsWith("4.0"))osn="Icecream-Sandwitch";
            else if(os.startsWith("4.4"))osn="KitKat";
            else if(os.startsWith("4"))osn="JellyBean";
            else if(os.startsWith("5"))osn="Lollipop";
            else if(os.startsWith("6"))osn="MarshMallow";
        return osn;
    }
    /**
     * The home directory of user
     */
    public String ush=System.getProperty("user.home");
    /**
     * Fetches all the data of /system/build.prop
     * 
     * @return the data of /system/build.prop in a String
     * @throws java.io.IOException If I/O Error Occurs by Process
     */
    public String getAllProps() throws IOException{
        run(adb,"pull","/system/build.prop",ush+"/.ads/build.prop");
        String txt="";
        Scanner sc=new Scanner(new FileInputStream(ush+"/.ads/build.prop"));
        while(sc.hasNextLine())txt+=sc.nextLine()+"\n";
        return txt;
    }
    /**
     * Fetches the list of all the subdirectories and files of provided path
     * @param dir the path to the directory of the device
     * 
     * @return array containing the list of all the subdirectories and files of provided path
     * @throws java.io.IOException If I/O Error Occurs by Process
     */
   public String[] getchilds(String dir) throws IOException{
       String s=run(adb,"shell","ls","-F",dir).trim();
       return s.split("\n");
    }
   /*public String[] getchilds(String dir) throws IOException{
       String s=run(adb,"shell","ls","-s","-a",dir),d=s.substring(s.indexOf("\n")).trim();
       if(!s.trim().toLowerCase().startsWith("total"))throw new IOException("Fatal Error!!!!");
       return d.split("\n");
    }
   */
}
