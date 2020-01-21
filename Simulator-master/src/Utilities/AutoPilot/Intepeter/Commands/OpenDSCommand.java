package Utilities.AutoPilot.Intepeter.Commands;

import Utilities.AutoPilot.Intepeter.InterpterUtilities.StringToArgumentParser;
import test.Simulator;
import Utilities.AutoPilot.Intepeter.Parser;
import Utilities.AutoPilot.Intepeter.TypeArguments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OpenDSCommand implements Command {
    private volatile boolean stop;
    @Override
    public int getArguments(String[] args, int idx, List<Object> emptyList) throws Exception{
        return StringToArgumentParser.parse(args, idx, 3, emptyList, TypeArguments.String, TypeArguments.Integer, TypeArguments.Integer);
    }
    private void runServer(int port, int timesASec){
        BufferedReader br = null;
        boolean sentOnce = false;
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(1000);
            while (!stop) {
                try (Socket client = serverSocket.accept()) {
                    br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    while (!stop) {
                        String line = br.readLine();
                        if (line == null) continue;
                        String[] variables = line.split(",");
                      ConcurrentHashMap<String, Double> serverValuesTest=Parser.getInstance().getBindVarValueMap();                      
                      serverValuesTest.put("simX", Double.parseDouble(variables[0]));
                      serverValuesTest.put("simY", Double.parseDouble(variables[1]));
                      serverValuesTest.put("simZ", Double.parseDouble(variables[2]));
                        
                        if(!sentOnce) {
                            synchronized (this) {
                            	stopTimer();
                                notifyAll();
                            }
                        }
                        try {
                            Thread.sleep(1000 / timesASec);
                        } catch (InterruptedException e1) {
                        }
                    }
                }
                catch(SocketTimeoutException e){}
            }
        } catch (IOException e) {}
        if(br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    @Override
    public void doCommand(List<Object> args) {
        stop = false;
        synchronized (this) {
            new Thread(() -> 
            runServer((int) args.get(1)  , (int) args.get(2))).start(); //test
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
              
    }
    
    
    public void stopTimer(){
        stop = true;
    }
    
}
