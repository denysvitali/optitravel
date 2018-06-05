package ch.supsi.dti.i2b.shrug.optitravel.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferPipe {
    private InputStream is;
    private OutputStream os;
    private int bufferSize = 1024;

    public BufferPipe(InputStream is, OutputStream os){
        this.is = is;
        this.os = os;
    }

    public BufferPipe(InputStream is, OutputStream os, int bufferSize){
        this.is = is;
        this.os = os;
        this.bufferSize = bufferSize;
    }

    public void copy(){
        // http://www.java2s.com/Tutorials/Java/IO_How_to/Stream/Copy_from_InputStream_to_OutputStream.htm
        byte[] buffer = new byte[bufferSize];
        try {
            while (true) {
                int read = is.read(buffer);
                if(read == -1){
                    break;
                }
                os.write(buffer);
            }
        } catch(IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
