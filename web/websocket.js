/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = init;
socket = new WebSocket("ws://localhost:8080/WebSocketHome/actions");
socket.onmessage = onMessage;

var socket;

function onMessage(event) {
    var mje = JSON.parse(event.data);
    if (mje.action === "add") {
        printDeviceElement(mje);
    }
    if (mje.action === "remove") {
        document.getElementById(mje.id).remove();
        //device.parentNode.removeChild(device);
    }
    if (mje.action === "toggle") {
        var node = document.getElementById(mje.id);
        var statusText = node.children[2];
        if (mje.status === "On") {
            statusText.innerHTML = "Status: " + mje.status + " (<a href=\"#\" OnClick=toggleDevice(" + mje.id + ")>Turn off</a>)";
        } else if (mje.status === "Off") {
            statusText.innerHTML = "Status: " + mje.status + " (<a href=\"#\" OnClick=toggleDevice(" + mje.id + ")>Turn on</a>)";
        }
    }
    
    if(mje.action === "greet") {
        console.log("lego l greet");
         var greetings = document.createElement("span");
        greetings.innerHTML = "<b>Hello, " + mje.name + "! </b> ";
        var play  = document.createElement("button");
        play.innerHTML= "Play";
        document.getElementById("wrapper").appendChild(greetings);
        document.getElementById("wrapper").appendChild(play);
    }
    
    if(mje.action === "connectok") {
        
    }
}

function onConnect() {
}

function removeDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function toggleDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "toggle",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceElement(device) {
    var content = document.getElementById("content");
    
    var deviceDiv = document.createElement("div");
    deviceDiv.setAttribute("id", device.id);
    deviceDiv.setAttribute("class", "device " + device.type);
    content.appendChild(deviceDiv);

    var deviceName = document.createElement("span");
    deviceName.setAttribute("class", "deviceName");
    deviceName.innerHTML = device.name;
    deviceDiv.appendChild(deviceName);

    var deviceType = document.createElement("span");
    deviceType.innerHTML = "<b>Type:</b> " + device.type;
    deviceDiv.appendChild(deviceType);

    var deviceStatus = document.createElement("span");
    if (device.status === "On") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
    } else if (device.status === "Off") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        //deviceDiv.setAttribute("class", "device off");
    }
    deviceDiv.appendChild(deviceStatus);

    var deviceDescription = document.createElement("span");
    deviceDescription.innerHTML = "<b>Comments:</b> " + device.description;
    deviceDiv.appendChild(deviceDescription);

    var removeDevice = document.createElement("span");
    removeDevice.setAttribute("class", "removeDevice");
    removeDevice.innerHTML = "<a href=\"#\" OnClick=removeDevice(" + device.id + ")>Remove device</a>";
    deviceDiv.appendChild(removeDevice);
}

function showForm() {
    document.getElementById("addDeviceForm").style.display = '';
}

function hideForm() {
    document.getElementById("nickName").style.display = "none";
}

function submitNickName() {
    console.log("submit (second)");
    var form = document.getElementById("nickName");
    var name = form.elements["name"].value;
    hideForm();
    document.getElementById("nickName").reset();
       
    var DeviceAction = {
        action: "connect",
        name: name
    };
    window.socket.send(JSON.stringify(DeviceAction));
}

function init() {
    //hideForm();
}
