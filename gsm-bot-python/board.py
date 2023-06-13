import pygame
import random
from settings import *
from numpy import random

vec = pygame.math.Vector2

class board(pygame.sprite.Sprite):
    def __init__(self, newBoard):
        pygame.sprite.Sprite.__init__(self)
        #self.image = pygame.Surface((50, 50))
        #self.image.fill((246, 224, 210))  # the color
        self.board = self.createBoard(newBoard)
        self.pink
        self.purple
        self.blue
        self.green
        #self.initialColors

    def getBoard(self):
        return self.board

    def isEmpty(self, xy):
        if self.board[xy[0]][xy[1]] == 0:
            return True
        else:
            return False

    def checkForWin(self):
        if self.pink == 0 and self.purple == 0 and self.blue == 0 and self.green == 0:
            return True
        else:
            return False
    def checkForLose(self):
        if self.pink == 1 or self.purple == 1 or self.blue == 1 or self.green == 1:
            outputString = "[Pink count: {} Purple count: {} Blue count: {} Green count: {}]".format(self.pink, self.purple, self.blue, self.green)
            print(outputString)
            return True
        else:
            return False
    def updateBoard(self, coordinate):
        if self.board[coordinate[0]][coordinate[1]] == 1:
            self.clearNeighbours(1, coordinate, True)
        elif self.board[coordinate[0]][coordinate[1]] == 2:
            self.clearNeighbours(2, coordinate, True)
        elif self.board[coordinate[0]][coordinate[1]] == 3:
            self.clearNeighbours(3, coordinate, True)
        elif self.board[coordinate[0]][coordinate[1]] == 4:
            self.clearNeighbours(4, coordinate, True)
        else:
            print("Please choose correct value")
            return

        self.board = self.moveDown()
        self.board = self.moveRight()
        #outputString = "[UPDATE BOARD Pink count: {} Purple count: {} Blue count: {} Green count: {}]".format(self.pink, self.purple, self.blue, self.green)
        #print(outputString)

    def moveDown(self):
        board_copy = [[None] * len(self.board[0]) for _ in range(len(self.board))]  # Create a copy of the board

        for i in range(len(self.board)):
            board_copy[i] = self.board[i].copy()

        for j in range(len(board_copy[0])):
            empty_row = len(board_copy) - 1  # Track the empty row position

            # Iterate from bottom to top
            for i in range(len(board_copy) - 1, -1, -1):
                if board_copy[i][j] != 0:
                    # Move the non-empty cell to the empty row
                    board_copy[empty_row][j] = board_copy[i][j]
                    empty_row -= 1

            # Fill the remaining empty cells with Color.O
            while empty_row >= 0:
                board_copy[empty_row][j] = 0
                empty_row -= 1

        return board_copy

    def moveRight(self):
        counter = []
        for i in range(len(self.board)):
            if self.board[10][i] == 0 and i > 0:
                counter.append(i)

        for i in range(len(counter)):
            self.board = self.moveRightHelper(counter[i])

        return self.board
    def moveRightHelper(self, column):
        board_copy = [[None] * len(self.board[0]) for _ in range(len(self.board))]  # Create a copy of the board

        for i in range(len(self.board)):
            board_copy[i] = self.board[i].copy()

        # Make the movable part blanc
        for i in range(len(board_copy)):
            for j in range(column):
                board_copy[i][j] = 0

        for i in range(len(board_copy)):
            for j in range(column):
                board_copy[i][j+1] = self.board[i][j]

        return board_copy

    def createBoard(self, newBoard):
        pink = 0
        purple = 0
        blue = 0
        green = 0
        for i in range(len(newBoard)):
            for j in range(len(newBoard)):
                if newBoard[i][j] == 1:
                    pink = pink + 1
                elif newBoard[i][j] == 2:
                    purple = purple + 1
                elif newBoard[i][j] == 3:
                    blue = blue + 1
                elif newBoard[i][j] == 4:
                    green = green + 1
        self.pink = pink
        self.purple = purple
        self.blue = blue
        self.green = green
        #self.initialColors = [pink, purple, blue, green]
        #print(self.initialColors)

        return newBoard

    def validCoord(self, coordinate):
        if coordinate[0] < 0 or coordinate[0] > 10 or coordinate[1] < 0 or coordinate[1] > 10:
            return False
        else:
            return True

    def clearNeighbours(self, value, coordinate, firstCheck):
        if self.board[coordinate[0]][coordinate[1]] == value:
            if not firstCheck:
                self.board[coordinate[0]][coordinate[1]] = 0
                if value == 1:
                    self.pink = self.pink - 1
                elif value == 2:
                    self.purple = self.purple - 1
                elif value == 3:
                    self.blue = self.blue - 1
                elif value == 4:
                    self.green = self.green - 1

            #over
            newCoord = [coordinate[0] - 1, coordinate[1]]
            if self.validCoord(newCoord):
                self.clearNeighbours(value, newCoord, False)

            #under
            newCoord = [coordinate[0] + 1, coordinate[1]]
            if self.validCoord(newCoord):
                self.clearNeighbours(value, newCoord, False)

            #left
            newCoord = [coordinate[0], coordinate[1] - 1]
            if self.validCoord(newCoord):
                self.clearNeighbours(value, newCoord, False)

            #right
            newCoord = [coordinate[0], coordinate[1] + 1]
            if self.validCoord(newCoord):
                self.clearNeighbours(value, newCoord, False)
