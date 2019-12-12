package com.tortoisedb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketBuffer {
    private final int STRSIZE = 60;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public SocketBuffer(Socket socket) throws IOException {
        this.dataInputStream  = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public String read_string() throws IOException {
        String result;
        byte[] bStr;
        char[] cStr = new char[this.STRSIZE];

        bStr = read_bytes(this.STRSIZE);

        for(int i = 0; i < this.STRSIZE;i++)
            cStr[i]= (char) bStr[i];

        result = String.valueOf(cStr);

        return result.trim();
    }
    public String read_space() throws IOException{
        String str;
        byte bStr[] = new byte[1];
        bStr = read_bytes(1);
        char c = (char) bStr[0];
        str = String.valueOf(c);
        return str;
    }
    public String read_command() throws IOException {
        String result;
        byte[] bStr = new byte[4];
        char[] cStr = new char[4];

        bStr = read_bytes(4);

        for (int i = 0; i < 4; i++)
            cStr[i] = (char) bStr[i];

        result = String.valueOf(cStr);

        return result.trim();
    }
    public void write_space() throws IOException
    {
        String str = " ";
        int numBytes, lenStr;
        byte bStr[] = new byte[1];

        lenStr = str.length();

        if (lenStr > 1)
            numBytes = 1;
        else
            numBytes = lenStr;

        for(int i = 0; i < numBytes; i++)
            bStr[i] = (byte) str.charAt(i);

        for(int i = numBytes; i < 1; i++)
            bStr[i] = (byte) ' ';

        dataOutputStream.write(bStr, 0, 1);
    }
    public void write_command(String str) throws IOException {
        int numBytes, lenStr;
        byte[] bStr = new byte[4];

        lenStr = str.length();

        if (lenStr > 4)
            numBytes = 4;
        else
            numBytes = lenStr;

        for (int i = 0; i < numBytes; i++)
            bStr[i] = (byte) str.charAt(i);

        for (int i = numBytes; i < 4; i++)
            bStr[i] = (byte) ' ';

        dataOutputStream.write(bStr, 0, 4);
    }

    public void write_string(String str) throws IOException {
        int numBytes, lenStr;
        byte bStr[] = new byte[this.STRSIZE];

        lenStr = str.length();

        numBytes = Math.min(lenStr, this.STRSIZE);

        for(int i = 0; i < numBytes; i++)
            bStr[i] = (byte) str.charAt(i);

        for(int i = numBytes; i < this.STRSIZE; i++)
            bStr[i] = (byte) ' ';

        dataOutputStream.write(bStr, 0, this.STRSIZE);
    }

    private byte[] read_bytes(int numBytes) throws IOException {
        int len         = 0;
        byte[] bStr     = new byte[numBytes];
        int bytesRead;

        while (len < numBytes) {
            bytesRead = dataInputStream.read(bStr, len, numBytes - len);

            if (bytesRead == -1)
                throw new IOException("Broken Pipe");
            len += bytesRead;
        }

        return bStr;
    }

}
