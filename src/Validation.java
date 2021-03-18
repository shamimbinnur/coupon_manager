import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Validation {
    public boolean isLicenseVerified() throws IOException {
        File file = new File("config");
        BufferedReader br = new BufferedReader(new FileReader(file));
        char[] inputArray = br.readLine().toCharArray();
        char licenseVerifiedSign = 'K';
        if (inputArray[200] == licenseVerifiedSign){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean makeLicenseApproved() throws IOException {
        char[] configArray = getConfig().toCharArray();
        configArray[200] ='K';

        try{
            FileWriter fw=new FileWriter("config");
            fw.write(configArray);
            fw.close();
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean isSubmitted() throws IOException {
        File file = new File("config");
        BufferedReader br = new BufferedReader(new FileReader(file));
        char[] inputArray = br.readLine().toCharArray();
        char submissionSign = 'r';

        if (inputArray[300]== submissionSign){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean makeItSubmitted() throws IOException {
        char[] configArray = getConfig().toCharArray();
        int submissionSignPosition = 300;
        configArray[submissionSignPosition] ='r';
        try{
            FileWriter fw=new FileWriter("config");
            fw.write(configArray);
            fw.close();
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean storeTheRand(String rand) throws IOException {
        char[] configArray = getConfig().toCharArray();
        char[] randArray = rand.toCharArray();
        int k=0;
        System.out.println(randArray);
        int i;
        int startingPosition = 600;
        for( i=startingPosition; i<(startingPosition+randArray.length); i++){
            configArray[i] = randArray[k];
            k++;
        }
        try{
            FileWriter fw=new FileWriter("config");
            String str = String.valueOf(configArray);
            System.out.println("Mod array: "+str);
            fw.write(str);
            fw.close();
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public String getTTheStoredRand() throws IOException {
        char[] configArray = getConfig().toCharArray();
        int i;
        StringBuffer stb = new StringBuffer();
        int lengthOfRand = 15;
        for( i=600; i<(600+lengthOfRand); i++){
            stb.append(configArray[i]);
        }
        return stb.toString();
    }



    public String getConfig() throws IOException {
        File file = new File("config");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = br.readLine();
        return st;
    }

    public String getLicense() throws IOException {
        File file = new File("license");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = br.readLine();
        return st;
    }

    public boolean setLicense(String license) throws IOException {
        try{
            FileWriter fw=new FileWriter("license");
            fw.write(license);
            fw.close();
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean isLicenseNull() throws IOException {
        File file = new File("license");
        BufferedReader br = new BufferedReader(new FileReader(file));
        if (br.readLine() == null){
            return true;
        }
        else
        return false;
    }

    public String getMac() throws UnknownHostException, SocketException {
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        byte[] hardwareAddress = ni.getHardwareAddress();
        String[] hexadecimal = new String[hardwareAddress.length];
        for (int i = 0; i < hardwareAddress.length; i++) {
            hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
        }
        String macAddress = String.join("-", hexadecimal);
        return macAddress;
    }

    public boolean storeMac() throws IOException {
        char[] configArray = getConfig().toCharArray();
        char[] macArray = getMac().toCharArray();
        int k=0;
        int i;
        int startingPosition = 150;
        for( i=startingPosition; i<(startingPosition+macArray.length); i++){
            configArray[i] = macArray[k];
            k++;
        }
        try{
            FileWriter fw=new FileWriter("config");
            String str = String.valueOf(encrypt(configArray));
            System.out.println("Mod array: "+str);
            fw.write(str);
            fw.close();
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public String getStoredMac() throws IOException {
        char[] configArray = getConfig().toCharArray();
        char[] tempArray = new char[50];
        int startingPosition = 150;
        int k=0;
        for (int i = startingPosition; i<(startingPosition+30); i++){
            tempArray[k] = configArray[i];
            k++;
        }
        return String.valueOf(decrypt(tempArray));
    }

    public boolean isMacVerified() throws IOException {
        char[] storedMac = getStoredMac().toCharArray();
        char[] currentMac = getMac().toCharArray();
        char[] tempArr = new char[30];
        int startingPosition = 0;
        int k=0;
        for (int i = startingPosition; i<currentMac.length; i++){
            if(currentMac[i] != storedMac[i]){
                return false;
            }
        }
        return true;
    }

    public char[] encrypt(char[] array){
        char[] temp = new char[array.length];
        for (int i=0; i<array.length; i++){

            if(array[i] == '0'){
                temp[i] = 'n';
            }
            else if(array[i] == '1'){
                temp[i] = 'O';
            }
            else if(array[i] == '2'){
                temp[i] = 'P';
            }
            else if(array[i] == '3'){
                temp[i] = 'q';
            }
            else if(array[i] == '4'){
                temp[i] = 'R';
            }
            else if(array[i] == '5'){
                temp[i] = 'S';
            }
            else if(array[i] == '6'){
                temp[i] = 't';
            }
            else if(array[i] == '7'){
                temp[i] = 'U';
            }
            else if(array[i] == '8'){
                temp[i] = 'v';
            }
            else if(array[i] == '9'){
                temp[i] = 'w';
            }
            else if(array[i] == 'A'){
                temp[i] = 'g';
            }
            else if(array[i] == 'B'){
                temp[i] = 'H';
            }
            else if(array[i] == 'C'){
                temp[i] = 'I';
            }
            else if(array[i] == 'D'){
                temp[i] = 'j';
            }
            else if(array[i] == 'E'){
                temp[i] = 'k';
            }
            else if(array[i] == 'F'){
                temp[i] = 'L';
            }
            else if(array[i] == '-'){
                temp[i] = 'X';
            }
            else {
                temp[i] = array[i];
            }
        }
        return temp;
    }

    public char[] decrypt(char[] array){
        char[] temp = new char[array.length];
        for (int i=0; i<array.length; i++){
            if(array[i] == 'n'){
                temp[i] = '0';
            }
            else if(array[i] == 'O'){
                temp[i] = '1';
            }
            else if(array[i] == 'P'){
                temp[i] = '2';
            }
            else if(array[i] == 'q'){
                temp[i] = '3';
            }
            else if(array[i] == 'R'){
                temp[i] = '4';
            }
            else if(array[i] == 'S'){
                temp[i] = '5';
            }
            else if(array[i] == 't'){
                temp[i] = '6';
            }
            else if(array[i] == 'U'){
                temp[i] = '7';
            }
            else if(array[i] == 'v'){
                temp[i] = '8';
            }
            else if(array[i] == 'w'){
                temp[i] = '9';
            }
            else if(array[i] == 'g'){
                temp[i] = 'A';
            }
            else if(array[i] == 'H'){
                temp[i] = 'B';
            }
            else if(array[i] == 'I'){
                temp[i] = 'C';
            }
            else if(array[i] == 'j'){
                temp[i] = 'D';
            }
            else if(array[i] == 'k'){
                temp[i] = 'W';
            }
            else if(array[i] == 'L'){
                temp[i] = 'F';
            }
            else if(array[i] == 'X'){
                temp[i] = '-';
            }
            else {
                temp[i] = array[i];
            }
        }
        return temp;
    }

}
