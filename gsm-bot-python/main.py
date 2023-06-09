import pygame
from board import board
from settings import size, FPS, PINK, PURPLE, BLUE, GREEN, CLEAR

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
        screen.fill((249, 249, 249))
        x = 0
        y = 0
        for i in range(len(gameBoard.getBoard())):
            for j in range(len(gameBoard.getBoard()[0])):
                if gameBoard.getBoard()[i][j] == 0:
                    pygame.draw.rect(screen, CLEAR, pygame.Rect(x, y, 50, 50))
                elif gameBoard.getBoard()[i][j] == 1:
                    pygame.draw.rect(screen, PINK, pygame.Rect(x, y, 50, 50))
                elif gameBoard.getBoard()[i][j] == 2:
                    pygame.draw.rect(screen, PURPLE, pygame.Rect(x, y, 50, 50))
                elif gameBoard.getBoard()[i][j] == 3:
                    pygame.draw.rect(screen, BLUE, pygame.Rect(x, y, 50, 50))
                elif gameBoard.getBoard()[i][j] == 4:
                    pygame.draw.rect(screen, GREEN, pygame.Rect(x, y, 50, 50))
                x = x + 50
            x = 0
            y = y + 50


        pygame.draw.rect(screen, PINK, pygame.Rect(x, y, 50, 50))

        pygame.display.flip()

game_loop()
