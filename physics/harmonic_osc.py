#!/usr/bin/env python
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation

# Constants
k = 1.0  # spring constant
m = 1.0  # mass
omega = np.sqrt(k / m)  # angular frequency
dt = 0.01  # time step
t_max = 10.0  # maximum time

# Initial conditions
x0 = 1.0  # initial position
v0 = 0.0  # initial velocity

# Function to calculate position at time t
def position(t):
    return x0 * np.cos(omega * t) + (v0 / omega) * np.sin(omega * t)

# Create figure and axis
fig, ax = plt.subplots()
ax.set_xlim(0, t_max)
ax.set_ylim(-1.5, 1.5)
ax.set_xlabel('Time')
ax.set_ylabel('Position')

# Create empty line object for the position
line, = ax.plot([], [], lw=2)

# Initialization function: plot the background of each frame
def init():
    line.set_data([], [])
    return line,

# Animation function: update the plot for each frame
def animate(i):
    t = i * dt
    x = position(t)
    line.set_data(t, x)
    return line,

# Create animation
ani = animation.FuncAnimation(fig, animate, init_func=init, frames=int(t_max / dt), interval=dt * 1000, blit=True)

# Show the animation
plt.title('Harmonic Oscillator Animation')
plt.show()

