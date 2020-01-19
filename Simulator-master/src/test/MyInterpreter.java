package test;

import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

import Exceptions.CodeErrorException;
import Utilities.AutoPilot.Intepeter.Parser;

public class MyInterpreter {

	 private  CompletableFuture<Boolean> f;
	 private  volatile boolean isAutoPilotOn;
	
	public static  int interpret(String[] lines){
		// call your interpreter here
		MyInterpreter interp = new MyInterpreter();
		for(String line: lines)
		{
			try {
 // return (int)interp.autoFly(line);
  return (int)interp.autoFly(String.join(" ", lines));
				//return (int)interp.autoFly(Arrays.toString(lines));
			} 
			catch (CodeErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public  double autoFly(String code) throws CodeErrorException  {
		return  Parser.getInstance().parse(code, this);
        /*f=CompletableFuture.supplyAsync(()-> {
            try {
                Parser.getInstance().parse(code, this);
                isAutoPilotOn = true;
            } catch (CodeErrorException e) {
                return false;
            } catch (CancellationException e){
            }
            return true;
        });*/
        
        
    }


}