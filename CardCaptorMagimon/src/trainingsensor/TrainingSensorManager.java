package trainingsensor;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class TrainingSensorManager {
	 private static Context aContext=null;
	 private static Sensor sensorAccelerometer;
	 private static Sensor sensorProximity;
	 private static SensorManager sensorManager;
	 private static boolean running = false;
	 private static Boolean supported;
	 private static int SHAKE_THRESHOLD = 600;
	 private static TrainingSensorListener listener;
	   
	 
	public static boolean isListening() {
	        return running;
	}
	public static void stopListening() {
	        running = false;
	        try {
	            if (sensorManager != null && sensorEventListener != null) {
	                sensorManager.unregisterListener(sensorEventListener);
	            }
	        } catch (Exception e) {}
	    }
	
    public static boolean isSupported(Context context) {
        aContext = context;
        if (supported == null) {
            if (aContext != null) {
                 
                 
                sensorManager = (SensorManager) aContext.
                        getSystemService(Context.SENSOR_SERVICE);
                 
                // Get all sensors in device
                List<Sensor> sensorsaccelerometer = sensorManager.getSensorList(
                        Sensor.TYPE_ACCELEROMETER);
                List<Sensor> sensorsproximity = sensorManager.getSensorList(
                        Sensor.TYPE_PROXIMITY);
                 
                supported = new Boolean(sensorsaccelerometer.size() > 0 || sensorsproximity.size() >0);
                 
                 
                 
            } else {
                supported = Boolean.FALSE;
            }
        }
        return supported;
    }
    
    public static void configure(int threshold) {
        TrainingSensorManager.SHAKE_THRESHOLD = threshold;
      
    }
    
    public static void startListening( TrainingSensorListener trainingSensorListener ) 
    {
         
        sensorManager = (SensorManager) aContext.
                getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // Register Accelerometer Listener
        running = ( sensorManager.registerListener(
                sensorEventListener, sensorAccelerometer, 
                SensorManager.SENSOR_DELAY_GAME) ||sensorManager.registerListener(
                            sensorEventListener, sensorProximity, 
                            SensorManager.SENSOR_DELAY_GAME));
        
        listener = trainingSensorListener;
         
    }
    
    public static void startListening(
            TrainingSensorListener sensorListener, 
            int threshold) {
        configure(threshold);
        startListening(sensorListener);
    }
	
	 private static SensorEventListener sensorEventListener = 
		        new SensorEventListener() {
		  
				private long lastUpdate = 0;
				private float last_x;
				private float last_y;
				private float last_z;
				
		  
		        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
		  
		        public void onSensorChanged(SensorEvent sensorEvent) {
		        	Sensor mySensor = sensorEvent.sensor;
		   		 
		    	    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		    	    	   float x = sensorEvent.values[0];
		    	           float y = sensorEvent.values[1];
		    	           float z = sensorEvent.values[2];
		    	    
		    	           long curTime = System.currentTimeMillis();
		    	    
		    	           if ((curTime - lastUpdate) > 100) {
		    	               long diffTime = (curTime - lastUpdate);
		    	               lastUpdate = curTime;
		    	    
		    	               float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;
		    	    
		    	               if (speed > SHAKE_THRESHOLD) {
		    	            	  listener.onShake();
		    	               }
		    	    
		    	               last_x = x;
		    	               last_y = y;
		    	               last_z = z;
		    	        }
		    	    }
		    	    else if (mySensor.getType() == Sensor.TYPE_PROXIMITY) {
		    	    	
		    	    	listener.changeProximity(sensorEvent.values[0]);
		    	    }
		        }
		  
		    };
		    

}
