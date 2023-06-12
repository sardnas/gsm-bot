import pygame
from board import board
from settings import size, FPS, PINK, PURPLE, BLUE, GREEN, CLEAR
import random
# initialize pygame and create window
pygame.init()
screen = pygame.display.set_mode(size)
clock = pygame.time.Clock()

# init board
gameBoard = board()

def game_loop():
    running = True
    while running:
        # keep loop running at the right speed
        clock.tick(FPS)

        # check for closing window and key input
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    running = False

        # Draw / render
        font = pygame.font.SysFont('Arial', 20)
        screen.fill((249, 249, 249))
        x = 0
        y = 0

        for i in range(len(gameBoard.getBoard())):
            for j in range(len(gameBoard.getBoard()[0])):
                coordinate_string = "[{},{}]".format(i, j)
                if gameBoard.getBoard()[i][j] == 0:
                    pygame.draw.rect(screen, CLEAR, pygame.Rect(x, y, 50, 50))
                    screen.blit(font.render(coordinate_string, True, (249, 249, 249)), (x, y))
                elif gameBoard.getBoard()[i][j] == 1:
                    pygame.draw.rect(screen, PINK, pygame.Rect(x, y, 50, 50))
                    screen.blit(font.render(coordinate_string, True, (249, 249, 249)), (x, y))
                elif gameBoard.getBoard()[i][j] == 2:
                    pygame.draw.rect(screen, PURPLE, pygame.Rect(x, y, 50, 50))
                    screen.blit(font.render(coordinate_string, True, (249, 249, 249)), (x, y))
                elif gameBoard.getBoard()[i][j] == 3:
                    pygame.draw.rect(screen, BLUE, pygame.Rect(x, y, 50, 50))
                    screen.blit(font.render(coordinate_string, True, (249, 249, 249)), (x, y))
                elif gameBoard.getBoard()[i][j] == 4:
                    pygame.draw.rect(screen, GREEN, pygame.Rect(x, y, 50, 50))
                    screen.blit(font.render(coordinate_string, True, (249, 249, 249)), (x, y))
                x = x + 50
            x = 0
            y = y + 50

        pygame.display.flip()

        remove_x = int(input('Enter x-coordinate\n'))
        remove_y = int(input('Enter y-coordinate\n'))
        coordinates = [remove_x, remove_y]

        gameBoard.updateBoard(coordinates)

def solutionAlghorithm():
    searching = True
    arrayOfCoordinates = []
    invalidCount = 0

    while searching:
        randomX = random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        randomY = random.choice([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        randomCoordinates = [randomX, randomY]

        arrayOfCoordinates.append(randomCoordinates)

        gameBoard.updateBoard(randomCoordinates)

        if not gameBoard.validInput:
            invalidCount = invalidCount + 1

        if gameBoard.checkForLose() or invalidCount > 100:
            gameBoard.undoBoard()
            print('GAME OVER')
            print(arrayOfCoordinates)
            arrayOfCoordinates = []
            invalidCount = 0

        if gameBoard.checkForWin():
            print('SUCCESS')
            print(arrayOfCoordinates)
            searching = False


solutionAlghorithm()
