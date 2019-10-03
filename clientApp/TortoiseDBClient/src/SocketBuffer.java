package clientApp.TortoiseDBClient.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketBuffer {
    //TODO: read server messages

    private final int STRSIZE = 40;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public SocketBuffer(Socket socket) throws IOException {
        this.dataInputStream  = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void send_function(String function) throws IOException {
        boolean functionNeedsSpace = !(function.equals("GETT")
                || function.equals("DELT")
                || function.equals("UPDT")
                || function.equals("EXST")
                || function.equals("EXIT"));

        write_header(function);
        if(functionNeedsSpace)
            write_char(' ');
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

    public void write_header(String str) throws IOException {
        int numBytes, lenStr, size = 4;
        byte bStr[] = new byte[size];

        lenStr = str.length();

        if(lenStr > size)
            numBytes = size;
        else
            numBytes = lenStr;

        for(int i = 0; i < numBytes; i++)
            bStr[i] = (byte) str.charAt(i);

        // Enviem l'string writeBytes de DataOutputStrem no envia el byte més alt dels chars.
        dataOutputStream.write(bStr, 0, size);
    }


    public void write_char(char c) throws IOException {
        byte bytes = (byte) c;

        dataOutputStream.write(bytes);
    }
}
