package com.ely.secretify;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import reactor.Environment;
import reactor.bus.EventBus;
@SpringBootApplication
@Service
public class SecretifyApplication {

    public static void main(String[] args) {

//        SerialPort comPort = SerialPort.getCommPorts()[0];
//        comPort.openPort();
//
//
//
//        comPort.addDataListener(new SerialPortDataListener() {
//            @Override
//            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
//            @Override
//            public void serialEvent(SerialPortEvent event)
//            {
//                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
//                    return;
//                byte[] newData = new byte[comPort.bytesAvailable()];
//                int numRead = comPort.readBytes(newData, newData.length);
//                String str = new String(newData);
//                System.out.println("Read " + numRead + " bytes. like this " + str );
//            }
//        });

        SpringApplication.run(SecretifyApplication.class, args);
    }

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

}
