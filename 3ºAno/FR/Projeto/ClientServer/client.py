import signal
import socket
import sys
import psutil
import time

def signal_handler(sg, fr):
    print("\nDone!")
    sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)
print("Press Ctrl+C to exit ...")



ip = "127.0.0.1"
port = 5005

socketTCP = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
socketTCP.connect((ip, port))

while True:
    try:
        msg = ("CPU: "+str(psutil.cpu_percent())+"%   Memory: "+str(psutil.virtual_memory()[2])+"%").encode()
        if len(msg)>0:
            socketTCP.send(msg)
            response = socketTCP.recv(4096).decode()
            print("Server response: {}".format(response))
        time.sleep(2)
    except (socket.timeout, socket.error):
        print("Server error. Done!")
        sys.exit(0)
