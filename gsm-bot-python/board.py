import pygame
import random
from settings import *
from numpy import random

vec = pygame.math.Vector2

class board(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        #self.image = pygame.Surface((50, 50))
        #self.image.fill((246, 224, 210))  # the color
        self.board = self.createBoard()

    def getBoard(self):
        return self.board
    def updateBoard(self, coordinate):
        if self.board[coordinate[0]][coordinate[1]] == 1:
            self.clearNeighbours(1, coordinate, True)

        elif board[coordinate[0]][coordinate[1]] == 2:
            self.clearNeighbours(2, coordinate, True)

        elif board[coordinate[0]][coordinate[1]] == 3:
            self.clearNeighbours(3, coordinate, True)

        elif board[coordinate[0]][coordinate[1]] == 4:
            self.clearNeighbours(4, coordinate, True)

        else:
            print("Please choose correct value")

        self.board = self.moveDown()

    def moveDown(self):
        board_copy = [[None] * len(self.board[0]) for _ in range(len(self.board))]  # Create a copy of the board

        for i in range(len(self.board)):
            board_copy[i] = self.board[i].copy()  # Copy each row of the board

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

    def createBoard(self):
        print(random.choice([1, 2, 3, 4], size=(11, 11)))
        return random.choice([1, 2, 3, 4], size=(11, 11))

    def validCoord(self, coordinate):
        if coordinate[0] < 0 or coordinate[0] > 10 or coordinate[1] < 0 or coordinate[1] > 10:
            return False
        else:
            return True

    def clearNeighbours(self, value, coordinate, firstCheck):
        if self.board[coordinate[0]][coordinate[1]] == value:
            if not firstCheck:
                self.board[coordinate[0]][coordinate[1]] = 0

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