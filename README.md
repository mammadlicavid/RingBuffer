# RingBuffer

**Project Overview**

This project implements a Ring Buffer with a single writer and multiple readers.
The buffer has a fixed capacity and stores integer values.

A single writer can continuously write values into the buffer. Multiple readers can read the values independently, meaning that reading by one reader does not remove the data for other readers.

If the buffer becomes full, new writes overwrite the oldest data. In this situation, slow readers may miss some values that were overwritten before they were read.





**UML Class Diagram**

<img width="296" height="414" alt="image" src="https://github.com/user-attachments/assets/3ba5f687-f689-47b0-aabf-71719eb40657" />

**Sequence Diagram – write()**

<img width="477" height="583" alt="image" src="https://github.com/user-attachments/assets/121d75fe-51b7-4cb9-8349-76ee188a4c03" />

**Sequence Diagram – read()**

<img width="314" height="378" alt="image" src="https://github.com/user-attachments/assets/487ad614-ee6f-4b1d-9c29-0635b2469c34" />

