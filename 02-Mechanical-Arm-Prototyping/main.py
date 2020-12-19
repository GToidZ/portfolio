import serial
import pynput

from serial.tools import list_ports
from pynput.keyboard import Key, Listener, KeyCode

VALID_JOINTS = {'LOW', 'UP', 'JAWR', 'JAWW'}
isInAutomation = 0

ports = [p.device for p in list_ports.comports()]

print("Available ports for this session:")
for i in range(len(ports)):
    print("["+str(i+1)+"] "+str(ports[i]))

port_selection = int(input("Enter a number for the port. (Defaults at 1) >> ") or 1)
active_port = ports[port_selection-1]
active_baudrate = int(input("Enter a baudrate for serial communication. (Defaults at 115200) >> ") or "115200")

session = serial.Serial(active_port, baudrate = active_baudrate)
session.write("PING".encode("utf-8"))
#while 1:
#    if session.read_until("PONG"):
#        break

print("Connection established with... [" + str(ports[port_selection-1]) + "] ...with a baudrate of " + str(active_baudrate))

low = 0
up = 0
jawr = 0
jaww = 0

# Resets the arm to default angles, invoke when connection is established, when a new set of actions is applied.
def reset_rotation():
    global low, up, jawr, jaww
    session.write("RST".encode("utf-8"))
    print("RST")
    low = 90
    up = 90
    jawr = 90
    jaww = 90

def single_rotation(joint, operator):
    global low, up, jawr, jaww
    i = 0

    if joint not in VALID_JOINTS:
        raise ValueError("Joint " + str(joint) + " is not a VALID_JOINT")
    if int(operator) not in {0, 1}:
        raise ValueError("Operator can only be 0 (for -) or 1 (for +)")
    if int(operator) == 0:
        i = -1
    elif int(operator) == 1:
        i = 1

    if joint == 'LOW':
        if low + i > 180 or low + i < 0:
            return 0
        else:
            low += i
            print("ROT " + joint + ":" + str(low))
    if joint == 'UP':
        if up + i > 180 or up + i < 0:
            return 0
        else:
            up += i
            print("ROT " + joint + ":" + str(up))
    if joint == 'JAWR':
        if jawr + i > 180 or jawr + i < 0:
            return 0
        else:
            jawr += i
            print("ROT " + joint + ":" + str(jawr))
    if joint == 'JAWW':
        if jaww + i > 180 or jaww + i < 0:
            return 0
        else:
            jaww += i
            print("ROT " + joint + ":" + str(jaww))

    session.write(("ROT " + joint + ":" + str(operator)).encode("utf-8"))
    
def pose_rotation(*argpos):
    global low, up, jawr, jaww
    if len(argpos) != 4:
        raise ValueError("Pose rotation takes only 4 arguments.")
    session.write(("ACT " + str(argpos[0]) + "," + str(argpos[1]) + "," + str(argpos[2]) + "," + str(argpos[3])).encode("utf-8"))
    print("ACT " + str(argpos[0]) + "," + str(argpos[1]) + "," + str(argpos[2]) + "," + str(argpos[3]))

# Stops the immediate arm actions, respond to user manual movement.
def stop_rotation():
    session.write("STP".encode("utf-8"))
    print("STP")

reset_rotation()

def on_press(key):
    try:
        if key.char == 'w':
            single_rotation('UP', 1)
        if key.char == 's':
            single_rotation('UP', 0)
        if key.char == 'a':
            single_rotation('JAWR', 0)
        if key.char == 'd':
            single_rotation('JAWR', 1)
        if key.char == 'q':
            single_rotation('JAWW', 0)
        if key.char == 'e':
            single_rotation('JAWW', 1)
    except AttributeError:    
        if key is Key.up:
            single_rotation('LOW', 1)
        if key is Key.down:
            single_rotation('LOW', 0)
    
def on_release(key):
    if key is Key.esc:
        return False

with Listener(on_press=on_press, on_release=on_release, suppress=True) as Listener:
    Listener.join()