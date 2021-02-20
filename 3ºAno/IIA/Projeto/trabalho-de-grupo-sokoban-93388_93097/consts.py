"""Constants shared amongst modules."""
from enum import IntFlag

MAX_HIGHSCORES = 10

RANKS = {
    1: "1ST",
    2: "2ND",
    3: "3RD",
    4: "4TH",
    5: "5TH",
    6: "6TH",
    7: "7TH",
    8: "8TH",
    9: "9TH",
    10: "10TH",
}


class GameStatus(IntFlag):
    """Game Status"""

    RUNNING = 0
    NEW_MAP = 1
    NO_OPERATION = 2

class Tiles(IntFlag):
    """Tiles bitfield."""

    FLOOR = 0  # -
    GOAL = 1  # .
    MAN = 2  # @
    MAN_ON_GOAL = 3  # +
    BOX = 4  # $
    BOX_ON_GOAL = 5  # *
    WALL = 8  # #


TILES = {
    "-": Tiles.FLOOR,
    ".": Tiles.GOAL,
    "@": Tiles.MAN,
    "+": Tiles.MAN_ON_GOAL,
    "$": Tiles.BOX,
    "*": Tiles.BOX_ON_GOAL,
    "#": Tiles.WALL,
}
