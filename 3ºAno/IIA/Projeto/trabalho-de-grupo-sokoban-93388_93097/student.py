import asyncio
import getpass
import json
import os
import random
import math
import sys
import time

import websockets
from mapa import Map

from treeSolver import *
from treeSearch import *
from studentFunctions import *

async def solver(puzzle, solution):
    l = 0
    tmp = 0
    nodes = 0
    averagetmp = 0
    averagenodes = 0
    totalsteps = 0
    while True:
        l+=1
        game_properties = await puzzle.get()
        mapa = Map(game_properties["map"])
        print(mapa)

        #Movements to send to server
        keys = []
        #List to store goals postions
        goals = []

        #Find deadlocks in the map
        dlist = deadlock_finder(mapa)
        print("DEADLOCKS: "+str(dlist))
        #Get the goals
        goals = mapa.empty_goals
        for value in mapa.boxes:
            if str(mapa.get_tile(value)) == 'Tiles.BOX_ON_GOAL':
                goals.append((value[0], value[1]))

        print("Goals are" + str(goals))
        print("Boxes are"+str(mapa.boxes))

        #Initializing a matrix of random numbers for zombrist hashing
        m = Matrix(mapa.size[0], mapa.size[1], 2)

        #Solve the map
        sts = SearchTreeSolver(goals, State(mapa.boxes, mapa.keeper, [], m), mapa.is_blocked, dlist)
        #Search for the Solution
        start = time.time()
        await (sts.search())
        end = time.time()

        #Statistics of the search
        print("TIME: "+str(end - start))
        print("NODES: "+str(sts.nodes))
        
        tmp+=(end - start)
        nodes+=sts.nodes
        print("------------------------")
        print("TOTAL TIME: "+str(tmp))
        print("TOTAL NODES: "+str(nodes))
        
        averagetmp = tmp/l
        averagenodes = nodes/l
        print("------------------------")
        print("AVERAGE TIME p/level: "+str(averagetmp))
        print("AVERAGE NODES p/level: "+str(averagenodes))
        print("------------------------")
        print("LEVEL" + str(l)+" SOLVED")
        print(sts.solution)
        print(sts.get_path(sts.solution))

        await asyncio.sleep(0)
        keys = sts.solution.state.path
        totalsteps+=len(keys)
        print("TOTAL STEPS: "+str(totalsteps))
        await asyncio.sleep(0)
        print(keys)
        await asyncio.sleep(0)
        await solution.put(keys)




async def agent_loop(puzzle, solution, server_address="localhost:8000", agent_name="student"):
    async with websockets.connect(f"ws://{server_address}/player") as websocket:

        # Receive information about static game properties
        await websocket.send(json.dumps({"cmd": "join", "name": agent_name}))

        while True:
            try:
                update = json.loads(
                    await websocket.recv()
                )  # receive game update, this must be called timely or your game will get out of sync with the server

                if "map" in update:
                    # we got a new level
                    game_properties = update
                    keys = []
                    await puzzle.put(game_properties)

                if not solution.empty():
                    keys = await solution.get()

                key = ""
                if len(keys):  # we got a solution!
                    key = keys[0]
                    keys = keys[1:]
                #print(keys)
                await websocket.send(
                    json.dumps({"cmd": "key", "key": key})
                )

            except websockets.exceptions.ConnectionClosedOK:
                print("Server has cleanly disconnected us")
                return

# DO NOT CHANGE THE LINES BELLOW
# You can change the default values using the command line, example:
# $ NAME='arrumador' python3 client.py
loop = asyncio.get_event_loop()
SERVER = os.environ.get("SERVER", "localhost")
PORT = os.environ.get("PORT", "8000")
NAME = os.environ.get("NAME", getpass.getuser())

puzzle = asyncio.Queue(loop=loop)
solution = asyncio.Queue(loop=loop)

net_task = loop.create_task(agent_loop(puzzle, solution, f"{SERVER}:{PORT}", NAME))
solver_task = loop.create_task(solver(puzzle, solution))

loop.run_until_complete(asyncio.gather(net_task, solver_task))
loop.close()
