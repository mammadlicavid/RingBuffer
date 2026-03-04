# RingBuffer
This is the repository for Assignment 2 in Object Oriented Analysis & Design.

# Project Overview

This project implements a Ring Buffer with a single writer and multiple readers.
The buffer has a fixed capacity and stores integer values.

A single writer can continuously write values into the buffer. Multiple readers can read the values independently, meaning that reading by one reader does not remove the data for other readers.

If the buffer becomes full, new writes overwrite the oldest data. In this situation, slow readers may miss some values that were overwritten before they were read.

# Design Explanation

The system follows object-oriented design principles and separates responsibilities between classes.

**RingBuffer**

The RingBuffer class is responsible:

- Managing the fixed-size buffer storage

- Storing written values

- Maintaining the writer position (writeSeq)

- Creating new Reader objects

- Handling overwriting when the buffer becomes full

- Only one writer calls the write() method.

**Reader**

The Reader class represents an independent reader and each reader maintains its own read position (nextSeq).

Responsibilities of the Reader class:

- Reading values from the buffer

- Tracking its own position

- Detecting when it has fallen behind and data has been overwritten

- Returning null if no new data is available

- Multiple readers can read from the same buffer without affecting each other.


# UML Diagrams

**UML Class Diagram**

<img width="296" height="414" alt="image" src="https://github.com/user-attachments/assets/3ba5f687-f689-47b0-aabf-71719eb40657" />

**Sequence Diagram – write()**

<img width="477" height="583" alt="image" src="https://github.com/user-attachments/assets/121d75fe-51b7-4cb9-8349-76ee188a4c03" />

**Sequence Diagram – read()**

<img width="314" height="378" alt="image" src="https://github.com/user-attachments/assets/487ad614-ee6f-4b1d-9c29-0635b2469c34" />

