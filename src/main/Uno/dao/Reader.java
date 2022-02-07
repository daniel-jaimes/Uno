package dao;

import exceptions.ExecutionException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private BufferedReader inputReader;
    public Reader(String nameFile) throws ExecutionException {
        System.out.println("LEIENDO DATOS...");
        inputReader = new BufferedReader(Reader.readerPath("files/"+ nameFile + ".txt"));
    }

    protected static FileReader readerPath(String path) throws ExecutionException {
        FileReader fr;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException ex){
            throw new ExecutionException(ExecutionException.CREATING_FILE);
        }
        return fr;
    }

    public String readLine() throws ExecutionException {
        try {
            String str = inputReader.readLine();
            return str == null ? "" : str;
        } catch (IOException e) {
            throw new ExecutionException(ExecutionException.READING_FILE);
        }
    }

    public void close() throws ExecutionException {
        try {
            inputReader.close();
        } catch (IOException e) {
            throw new ExecutionException(ExecutionException.CLOSING_FILE);
        }
    }
}