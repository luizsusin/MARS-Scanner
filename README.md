# MARS Scanner Rev 1.0

A [MARS](http://courses.missouristate.edu/KenVollmar/mars/) (MIPS Assembler and Runtime Simulator) tool to capture points on a JPEG image so that it can be transmitted through MMIO to the simulator. This data can be used to trace points using MARS Bot or to process image data using MIPS Assembly.

## Getting Started

### Prerequisites
To use MARS Scanner, you must have:

```
* 7-Zip, WinRAR or any other jar and zip file viewer and editor;
* A MARS 4.5 (or newer) jar file; and
* The binaries of this repository.
```
All the binaries released can be found at the page [releases](https://github.com/luizsusin/MARS-Scanner/releases).

### Updating from previous version

In case you're updating from an older version, follow these instructions:
1. Open the MARS 4.5 (or newer) jar file using your jar file viewer;
2. Access mars/tools and delete the folder marsscanner;
3. Go back to the root folder of the jar file;
4. Open the binaries using any zip file viewer;
5. Copy the contents of the binaries zip file inside the MARS jar file.

### Installing

To install MARS Scanner, you must:
1. Open the binaries using any zip file viewer;
2. Open the MARS 4.5 (or newer) jar file using your jar file viewer;
3. Copy the contents of the binaries zip file inside the MARS jar file.

And you're good to go!

## Using the tool

To use MARS Scanner, you must understand how MMIO (Memory Mapped I/O) works. In case you don't, I suggest you have some [reading](https://en.wikipedia.org/wiki/Memory-mapped_I/O) before proceeding.

Once you understand how it works, take note of these informations:

### Addresses used by MARS Scanner

The addresses that MARS Scanner uses to communicate back and forth with MARS are:

```
* 0xFFFF9000 - Used to pop a point from the trace list;
* 0xFFFF9010 - Used to communicate the next X coordinate of the point;
* 0xFFFF9020 - Used to communicate the next Y coordinate of the point.
```

### Popping a point from the trace list

Once you have already used the last point, you get to move to the next one. To do so, you must pop the first point of the trace list. To do so, you must send a high pulse to the address 0xFFFF9000 (see addresses reference above). E.g.:

```
#MIPS Assembly

.data
.text
    li $s0,0xFFFF9000
    li $t0,1
    sw $t0,0($s0) #Send a high pulse
    sw $zero,0($s0) #Turn back to low
```

### Reading the point from the memory

To read the point from the memory, you need to access the X and Y coordinates. To do so, you must read the word inside the referred memory address. E.g.: 

```
#MIPS Assembly

.data
.text
    li $s0,0xFFFF9010 #Defines the $s0 registrator with the X coordinate address
    li $s1,0xFFFF9020 #Defines the $s1 registrator with the Y coordinate address
    
    lw $t0,0($s0) #Loads the X coordinate inside the $t0 registrator
    lw $t1,0($s1) #Loads the Y coordinate inside the $t1 registrator
```

### How to detect that the image is fully iterated?

Once there'll never be a negative point, to detect if all the points have been read you must look for the point (-1,-1), which is the break clause of the MARS Scanner. E.g.:

```
#MIPS Assembly

.data
.text
    li $s0,0xFFFF9010 #Defines the $s0 registrator with the X coordinate address
    
    loop:
        # Iterate here...
        
        lw $t0,0($s0) #Loads the X coordinate inside the $t0 registrator
    bgez $t0,loop #Branch to the label 'loop' if $t0 is greater than or equals to zero, so it will keep iterating
```

## Authors

* **Luiz H. Susin** - *Initial work* - [GitHub](https://github.com/LuizSusin)

## License

This project is licensed under the GNU General Public License version 3 - see the [LICENSE.md](LICENSE.md) file for details.

## TO DO List

* Add more examples;
* Add color reading (RGB)
