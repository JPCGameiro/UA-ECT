"""Game Logic."""
import asyncio
import json
import logging

from mapa import Map, Tiles
from consts import GameStatus

logger = logging.getLogger("Game")
logger.setLevel(logging.DEBUG)

INITIAL_SCORE = 0
TIMEOUT = 3000
GAME_SPEED = 10


def reduce_score(puzzles, moves, pushes, steps, box_on_goal):
    """Convert tuple into 1-dimension score."""
    return 50000 * puzzles + 5000 * box_on_goal - 100 * pushes - steps


class Game:
    """Representation of a Game run."""

    def __init__(self, level=1, timeout=TIMEOUT, player=None):
        logger.info("Game(level=%s)", level)
        self.puzzles = 0 #puzzles completed
        self.level = level
        if player:
            self._running = True
            self._player_name = player
        else:
            self._running = False
        self._timeout = timeout
        self._step = 0
        self._total_steps = 0
        self._state = {}
        self._papertrail = ""  # keeps track of all steps made by the player
        self._moves = 0
        self._pushes = 0
        self.map = None
        self._lastkeypress = ""

        self.next_level(self.level)

    def info(self):
        """Initial Static information about the game."""
        return {
            "fps": GAME_SPEED,
            "timeout": self._timeout,
            "map": f"levels/{self.level}.xsb",
        }

    @property
    def papertrail(self):
        """String containing all pressed keys by agent."""
        return self._papertrail

    @property
    def running(self):
        """Status on game."""
        return self._running

    @property
    def score(self):
        """Calculus of the current score."""
        return self.puzzles, self._moves, self._pushes, self._total_steps + self._step, self.map.on_goal

    def stop(self):
        """Stop the game."""
        if self._step:
            logger.info("GAME OVER at %s", self._step)
        self._running = False

    def next_level(self, level):
        """Update all state variables to a new level."""
        self.puzzles += 1
        self._total_steps += self._step
        self._step = 0
        self._lastkeypress = ""
        self._papertrail += "," 
        self.level = level
        try:
            self.map = Map(f"levels/{level}.xsb")
            logger.info("NEXT LEVEL: %s", level)
        except FileNotFoundError:
            logger.info("No more levels... You WIN!")
            self.stop()
            return

    def keypress(self, key):
        """Update locally last key pressed."""
        self._lastkeypress = key

    def move(self, cur, direction):
        """Move an entity in the game."""
        assert direction in "wasd", f"Can't move in {direction} direction"

        cx, cy = cur
        ctile = self.map.get_tile(cur)

        npos = cur
        if direction == "w":
            npos = cx, cy - 1
        if direction == "a":
            npos = cx - 1, cy
        if direction == "s":
            npos = cx, cy + 1
        if direction == "d":
            npos = cx + 1, cy

        # test blocked
        if self.map.is_blocked(npos):
            logger.debug("Blocked ahead")
            return False
        if self.map.get_tile(npos) in [
            Tiles.BOX,
            Tiles.BOX_ON_GOAL,
        ]:  # next position has a box?
            if ctile & Tiles.MAN == Tiles.MAN:  # if you are the keeper you can push
                if not self.move(npos, direction):  # as long as the pushed box can move
                    return False
            else:  # you are not the Keeper, so no pushing
                return False
            self._pushes += 1
        else:
            self._moves += 1

        # actually update map
        self.map.set_tile(npos, ctile)
        self.map.clear_tile(cur)
        return True

    def update_keeper(self):
        """Update the location of the Keeper."""
        if self._lastkeypress == "":
            return GameStatus.NO_OPERATION
        try:
            # Update position
            self.move(self.map.keeper, self._lastkeypress)
            self._papertrail += self._lastkeypress
        except AssertionError:
            logger.error(
                "Invalid key <%s> pressed. Valid keys: w,a,s,d", self._lastkeypress
            )
        finally:
            self._lastkeypress = ""  # remove inertia

        if self.map.completed:
            logger.info("Level %s completed", self.level)
            self.next_level(self.level + 1)
            return GameStatus.NEW_MAP

        return GameStatus.RUNNING

    async def next_frame(self):
        """Calculate next frame."""
        await asyncio.sleep(1.0 / GAME_SPEED)

        if not self._running:
            logger.info("Waiting for player 1")
            return

        self._step += 1
        if self._step >= self._timeout:
            self.stop()

        if self._step % 100 == 0:
            logger.debug("[%s] SCORE %s", self._step, self.score)

        game_status = self.update_keeper()

        self._state = {
            "player": self._player_name,
            "level": self.level,
            "step": self._step,
            "score": self.score,
            "keeper": self.map.keeper,
            "boxes": self.map.boxes,
        }

        return game_status

    @property
    def state(self):
        """Contains the state of the Game."""
        # logger.debug(self._state)
        return json.dumps(self._state)
