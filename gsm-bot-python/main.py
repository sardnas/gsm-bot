import pygame
from settings import*

# initialize pygame and create window
pygame.init()
screen = pygame.display.set_mode(size)
clock = pygame.time.Clock()

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
        screen.fill((246, 224, 210))

game_loop()
