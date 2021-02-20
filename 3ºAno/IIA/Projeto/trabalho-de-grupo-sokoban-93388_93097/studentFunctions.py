from treeSearch import *

#Check if a box is going to be moved to a deadlock
def dead_lock(box, direction, is_blocked, boxList, deadlock_list):
    aux = []
    for b in boxList:
        if box != b:
            aux.append(b)
    aux = set(aux)
    test = []
    if direction == 'w':
        test = (box[0], box[1]-1)
    if direction == 's':
        test = (box[0], box[1]+1)
    if direction == 'a':
        test = (box[0]-1, box[1])
    if direction == 'd':
        test = (box[0]+1, box[1])

    if test in set(deadlock_list):
        return True

    testd = (test[0]+1, test[1])
    testa = (test[0]-1, test[1])
    testw = (test[0], test[1]-1)
    tests = (test[0], test[1]+1)

    #If left or right of the box is a wall
    if is_blocked(testd) or is_blocked(testa):
        #If Up and down of the box is a wall
        if is_blocked(tests) or is_blocked(testw):
            return True
        #If down of the box is a box
        elif tests in set(aux):
            ls = can_move(tests, boxList, is_blocked, [])
            if len(ls) < 2:
                return True
            elif 'w' in ls and len(ls) == 2:
                return True
        #If up of the box is a box
        elif testw in set(aux):
            lw = can_move(testw, boxList, is_blocked, [])
            if len(lw) < 2:
                return True
            elif 's' in lw and len(lw) == 2:
                return True
    #If up or down of the box is a wall
    elif is_blocked(testw) or is_blocked(tests):
        #If left or right of the box is a wall
        if is_blocked(testa) or is_blocked(testd):
            return True
        #If left of the box is a box
        elif testa in set(aux): 
            la = can_move(testa, boxList, is_blocked, [])
            if len(la) < 2:
                return True
            elif 'd' in la and len(la) == 2:
                return True
        #If right of the box is a box
        elif testd in set(aux):
            ld = can_move(testd, boxList, is_blocked, [])
            if len(ld) < 2:
                return True
            elif 'a' in ld and len(ld) == 2:  
                return True
    return False
    
#Get map deadlocks at the begining of the game without boxes
def deadlock_finder(mapa):
    res = []
    check = False
    for x in range(0, mapa.size[0]):
        for y in range(0, mapa.size[1]):
            if(mapa.is_blocked((x,y))):
                continue
            
            testd = (x+1, y)
            testa = (x-1, y)
            testw = (x, y-1)
            tests = (x, y+1)
            
            if(mapa.is_blocked(testd) and (mapa.is_blocked(tests) or (mapa.is_blocked(testw)))):  
                 check = True
            elif(mapa.is_blocked(testa) and (mapa.is_blocked(tests) or (mapa.is_blocked(testw)))):
                check = True
            elif(mapa.is_blocked(tests) and (mapa.is_blocked(testa) or (mapa.is_blocked(testd)))):
                check = True
            elif(mapa.is_blocked(testw) and (mapa.is_blocked(testa) or (mapa.is_blocked(testd)))):
                check = True 

            if check == True:
                if(str(mapa.get_tile((x,y))) != "Tiles.GOAL"):
                    res.append((x, y))
                check = False
    value = dead_lock_line_finder(res, mapa)
    res+=value
    return res  

#Finding walls around a position
def WallFinder(pos, mapa):
    res = []

    if mapa.is_blocked((pos[0]+1, pos[1])):
        res.append('d')
    elif mapa.is_blocked((pos[0]-1, pos[1])):
        res.append('a')
    elif mapa.is_blocked((pos[0], pos[1]+1)):
        res.append('s')
    elif mapa.is_blocked((pos[0], pos[1]-1)):
        res.append('w')
    
    return res


#Finding lines of deadlocks
def dead_lock_line_finder(deadlock_list, mapa):
    res = []
    tmp0 = deadlock_list[:]
    tmp1 =[]

    check = 0
    #Finding deadlock lines (y axis)
    for i in range(0, len(tmp0)-1):
        for j in range(1, len(tmp0)):
            check = 0
            if(tmp0[i][0] == tmp0[j][0]):
                maxy = max(tmp0[i][1], tmp0[j][1])
                miny = min(tmp0[i][1], tmp0[j][1])

                for y in range(miny+1, maxy):
                    
                    pos = (tmp0[i][0], y)
                    if str(mapa.get_tile(pos)) == "Tiles.FLOOR" or str(mapa.get_tile(pos)) == "Tiles.MAN":
                        if('a' in WallFinder(pos, mapa) or 'd' in WallFinder(pos, mapa)):
                            tmp1.append(pos)
                        else:
                            check = 1
                    else:
                        check = 1

            if(check != 1 and tmp1 != []):
                for v in tmp1:
                    if v not in res:
                        res.append(v)
            tmp1.clear()
    #Finding deadlock lines (x axis)
    for i in range(0, len(tmp0)-1):
        for j in range(1, len(tmp0)):
            check = 0
            if(tmp0[i][1] == tmp0[j][1]):
                maxx = max(tmp0[i][0], tmp0[j][0])
                minx = min(tmp0[i][0], tmp0[j][0])

                for x in range(minx+1, maxx):
                    
                    pos = (x, tmp0[i][1])
                    if str(mapa.get_tile(pos)) == "Tiles.FLOOR" or str(mapa.get_tile(pos)) == "Tiles.MAN":
                        if('w' in WallFinder(pos, mapa) or 's' in WallFinder(pos, mapa)):
                            tmp1.append(pos)
                        else:
                            check = 1
                    else:
                        check = 1

            if(check != 1 and tmp1 != []):
                for v in tmp1:
                    if v not in res:
                        res.append(v)
            tmp1.clear()

    return res


#Check if there is an obstacle on the direction of the movement
def has_obstacle(box, direction, is_blocked, boxlist):
    test = []
    if direction == 'w':
        test = (box[0], box[1]-1)
    if direction == 's':
        test = (box[0], box[1]+1)
    if direction == 'a':
        test = (box[0]-1, box[1])
    if direction == 'd':
        test = (box[0]+1, box[1])
    
    if is_blocked(test) or test in set(boxlist):
        return True
    return False

#Get directions from a list of coordinates
def get_directions(poslist):
    res = []
    for i in range (0, len(poslist)-1):
        if(poslist[i][0] < poslist[i+1][0]):
            res.append("d")
        elif(poslist[i][0] > poslist[i+1][0]):
            res.append("a")
        elif(poslist[i][1] < poslist[i+1][1]):
            res.append("s")
        elif(poslist[i][1] > poslist[i+1][1]):
            res.append("w")
    return res


#Move a Box
def move_box(boxpos, direction, kpos, is_blocked, boxList):
    path = None
    if(direction == 'w'):
        dst = (boxpos[0], boxpos[1]+1)
    elif(direction == 's'):
        dst = (boxpos[0], boxpos[1]-1)
    elif(direction == 'a'):
        dst = (boxpos[0]+1, boxpos[1])
    elif(direction == 'd'):
        dst = (boxpos[0]-1, boxpos[1])

    st = SearchTree("a*", dst, kpos, is_blocked, boxList)
    st.search()
    if(st.solution != None):
        path = st.get_path(st.solution)
        path = get_directions(path)
        path.append(direction)
    return path    

#Check if there is a box is going to be moved to a goal
def in_goal(box, direction, goallist):
    test = box[:]
    if direction == 'w':
        test = (box[0], box[1]-1)
    elif direction == 's':
        test = (box[0], box[1]+1)
    elif direction == 'a':
        test = (box[0]-1, box[1])
    elif direction == 'd':
        test = (box[0]+1, box[1])

    return test in set(goallist)

#Check in what directions can a box be moved
def can_move(pos, boxlist, is_blocked, lst):
    res = []
    testa = (pos[0]-1, pos[1])
    testd = (pos[0]+1, pos[1])
    tests = (pos[0], pos[1]+1)
    testw = (pos[0], pos[1]-1)

    #Position "pos" goes to the checked list
    lst.append(pos)

    #Left of the box
    #If position is not a box and not a wall
    if(testa not in set(boxlist) and not is_blocked(testa)):
        res.append('a')
    #If positions is box (don't check the ones already repeated (lst))
    elif testa in set(boxlist) and testa not in lst:
        la = can_move(testa, boxlist, is_blocked, lst)
        if len(la) > 1:
            res.append('a')

    #Right of the box
    #If position is not a box and not a wall
    if(testd not in set(boxlist) and not is_blocked(testd)):
        res.append('d')
    #If positions is box (don't check the ones already repeated (lst))
    elif testd in set(boxlist) and testd not in lst:
        ld = can_move(testd, boxlist, is_blocked, lst)
        if len(ld) > 1:
            res.append('d')

    #Down of the box
    #If position is not a box and not a wall
    if(tests not in set(boxlist) and not is_blocked(tests)):
        res.append('s')
    #If positions is box (don't check the ones already repeated (lst))
    elif tests in set(boxlist) and tests not in lst:
        ls = can_move(tests, boxlist, is_blocked, lst)
        if len(ls) > 1:
            res.append('s')

    #Up of the box
    #If position is not a box and not a wall
    if(testw not in set(boxlist) and not is_blocked(testw)):
        res.append('w')
    #If positions is box (don't check the ones already repeated (lst))
    elif testw in set(boxlist) and testw not in lst:
        lw = can_move(testw, boxlist, is_blocked, lst)
        if len(lw) > 1:
            res.append('w')
    
    if 's' in res and 'w' not in res:
        res.remove('s')
    if 'w' in res and 's' not in res:
        res.remove('w')
    if 'a' in res and 'd' not in res:
        res.remove('a')
    if 'd' in res and 'a' not in res:
        res.remove('d')
    
    return res
