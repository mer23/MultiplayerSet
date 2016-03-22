/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.set.model;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonReader;
import javax.json.Json;
import javax.json.JsonObject;

@ApplicationScoped
@ServerEndpoint("/actions")
public class WebSocketServer {
    
    @Inject
    private SessionHandler sessionHandler;
    
    @Inject
    private OutgoingHandler msgDispatcher;
    
    @Inject
    private IncomingHandler msgReceiver;

    @OnOpen
        public void open(Session session) {
            System.out.println("a new session was oppened!!");
            sessionHandler.addSession(session); //TODO esto podr[ia no hacer falta
            
            session.getUserProperties().put("player", new Player());
    }

    @OnClose
        public void close(Session session) {
            System.out.println("session was closed!!");
            sessionHandler.removeSession(session);
    }

    @OnError
        public void onError(Throwable error) {
            Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
            try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            
            switch (jsonMessage.getString("action")) {
                case "connect":
                    sessionHandler.connectWithName(jsonMessage.getString("name"), session);
                    break;
                case "play":
                    sessionHandler.displayMatchRoom(session);
                    break;
                case "create":
                    break;
                case "join":
                    break;
                case "set":
                    break;
                case "noset":
                    break;
                case "pause":
                    break;
                case "giveup":
                    break;
                case "msg":
                    break;
                case "":
                    break;
            }

            if ("add".equals(jsonMessage.getString("action"))) {
                Device device = new Device();
                device.setName(jsonMessage.getString("name"));
                device.setDescription(jsonMessage.getString("description"));
                device.setType(jsonMessage.getString("type"));
                device.setStatus("Off");
                sessionHandler.addDevice(device);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeDevice(id);
            }

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.toggleDevice(id);
            }
        }
    }
}
