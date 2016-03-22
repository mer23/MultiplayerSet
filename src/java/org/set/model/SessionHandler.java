/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.set.model;

/**
 *
 * @author mer23
 */
import java.io.IOException;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

@ApplicationScoped
public class SessionHandler {
    
    private int deviceId = 0;
    private final Set sessions = new HashSet<>();
    private final Set devices = new HashSet<>();
    private final Set games = new HashSet<>();

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Object session : sessions) {
            sendToSession((Session)session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addSession(Session session) {
        sessions.add(session);
    }
    
    public void displayMatchRoom(Session session) {
        ((Player)session.getUserProperties().get("player")).setStatus(Player.Status.MATCHROOM);
        
        String[] pairs = new String[this.games.size()];

        for (int i = 0; i < pairs.length; i++) {
            //iterar sobre arraylist o array??
            
        }
        /*
        creador
        dif
        mode
        users
        */
    }
    
    public void addGame(Game newGame) {
        this.games.add(newGame);
    }
    
    public boolean removeGame(Game game) {
        return games.remove(game);
    }
    
    public void connectWithName(String name, Session session) {
        session.getUserProperties().put("name", name);
        System.out.print("sale el greet");

        sendToSession(session, createMessage(
        "action", "greet",
                "name", (String)session.getUserProperties().get("name")));
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    /**
     * Retrieves JsonObjects to be sent to clients (Sessions).
     * @param args Strings to be added as key,value pairs. Must have an even length.
     * @return The JsonObject.
     */
    private JsonObject createMessage(String... args) throws IllegalArgumentException {
        
        if (args.length%2 != 0) { //Strings come in key,value pairs, so it must be even.
            throw new IllegalArgumentException("Number of argument Strings must be even.");
        }
        
        JsonProvider provider = JsonProvider.provider();
        JsonObjectBuilder builder = provider.createObjectBuilder();
        
        for (int i = 0; i < args.length; i+= 2) {
            builder.add(args[i], args[i+1]);
        }
        
        return builder.build();
    }
    
    
    //=====================================================================//
    
    
    public void addDevice(Device device) {
        device.setId(deviceId);
        devices.add(device);
        deviceId++;
        JsonObject addMessage = createAddMessage(device);
        sendToAllConnectedSessions(addMessage);
    }
    
    private Device getDeviceById(int id) {
        for (Object device : devices) {
            if (((Device)device).getId() == id) {
                return (Device)device;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Device device) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getStatus())
                .add("description", device.getDescription())
                .build();
        return addMessage;
    }
    
    public void removeDevice(int id) {
        Device device = getDeviceById(id);
        if (device != null) {
            devices.remove(device);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    public void toggleDevice(int id) {
        JsonProvider provider = JsonProvider.provider();
        Device device = getDeviceById(id);
        if (device != null) {
            if ("On".equals(device.getStatus())) {
                device.setStatus("Off");
            } else {
                device.setStatus("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", device.getId())
                    .add("status", device.getStatus())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }
    }
}
